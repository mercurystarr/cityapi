package com.leonardo.web;

import com.leonardo.data.CityDAO;
import com.leonardo.model.City;
import com.leonardo.util.HaversineAlgorithmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityDAO cityDao;

    @GetMapping("/list")
    public List<City> listAllCities() {
        return cityDao.listAll();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Integer id) {
        return cityDao.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCityById(@PathVariable Integer id) {
        cityDao.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@RequestBody City city) {
        return cityDao.create(city);
    }

    @PutMapping
    public void update(@RequestBody City city) {
        cityDao.update(city);
    }

    @GetMapping("/distance")
    public ResponseEntity<?> getDistance(@RequestParam("from") String fromId, @RequestParam("to") String toId) {
        City fromCity, toCity;

        try {
            fromCity = cityDao.findById(Integer.parseInt(fromId));
            toCity = cityDao.findById(Integer.parseInt(toId));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(fromCity !=null && toCity!=null) {
            Double distance =  HaversineAlgorithmUtil.distance(fromCity.getLatitude(), fromCity.getLongitude(), toCity.getLatitude(), toCity.getLongitude());
            return new ResponseEntity<>(distance, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
