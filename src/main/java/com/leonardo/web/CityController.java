package com.leonardo.web;

import com.leonardo.data.CityDAO;
import com.leonardo.model.City;
import com.leonardo.util.HaversineAlgorithmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private static Logger LOG = LogManager.getLogger(CityController.class);

    @Autowired
    private CityDAO cityDao;

    @GetMapping("/list")
    public List<City> listAllCities() {
        LOG.debug("List all cities");
        return cityDao.listAll();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Integer id) {
        LOG.debug("Getting city with id: " + id);
        return cityDao.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCityById(@PathVariable Integer id) {
        LOG.debug("Deleting city with id: " + id);
        cityDao.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@RequestBody City city) {
        LOG.debug("Adding new City: " + city);
        return cityDao.create(city);
    }

    @PutMapping
    public void update(@RequestBody City city) {
        LOG.debug("Updating City: " + city);
        cityDao.update(city);
    }

    @GetMapping("/distance")
    public ResponseEntity<?> getDistance(@RequestParam("from") String fromId, @RequestParam("to") String toId) {
        City fromCity, toCity;

        try {
            fromCity = cityDao.findById(Integer.parseInt(fromId));
            toCity = cityDao.findById(Integer.parseInt(toId));
        } catch (NumberFormatException e) {
            LOG.error(String.format("Cannot parse from/to id: from=%s, to=%s", fromId, toId), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (fromCity !=null && toCity!=null) {
            Double distance =  HaversineAlgorithmUtil.distance(fromCity.getLatitude(), fromCity.getLongitude(), toCity.getLatitude(), toCity.getLongitude());
            return new ResponseEntity<>(distance, HttpStatus.OK);

        } else {

            if (fromCity == null) {
                LOG.error(String.format("From City with id: %s cannot be found", fromId));
            }
            if (toCity == null) {
                LOG.error(String.format("To City with id: %s cannot be found", toId));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
