package com.leonardo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonardo.data.CityDAO;
import com.leonardo.model.City;
import com.leonardo.web.CityController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

	@Autowired
	private MockMvc mvc;

	@SpyBean
	private CityDAO cityDAO;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getCityList_thenReturnJsonArray() throws Exception {
		mvc.perform(get("/city/list")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(15)))
				.andExpect(jsonPath("$[0].name", is("Tokyo")));
	}

	@Test
	public void getCityById_thenReturnJsonObject() throws Exception {
		mvc.perform(get("/city/3")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Toronto")));
	}

	@Test
	public void getCityByInvalidId_thenReturnEmptyJsonObject() throws Exception {
		mvc.perform(get("/city/3000")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void deleteCityById_thenConfirmDeletion() throws Exception {
		mvc.perform(delete("/city/8")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get("/city/8")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	public void createCity_thenConfirmCreated() throws Exception {
		City city = new City();
		city.setName("Mexico City");
		city.setLatitude(19.42847);
		city.setLongitude(-99.12766);

		MvcResult requestResult = mvc.perform(post("/city")
				.content(objectMapper.writeValueAsString(city))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated()).andReturn();

		String id = requestResult.getResponse().getContentAsString();

		mvc.perform(get("/city/"+ id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(city.getName())))
				.andExpect(jsonPath("$.latitude", is(city.getLatitude())))
				.andExpect(jsonPath("$.longitude", is(city.getLongitude())));
	}

	@Test
	public void updateCity_thenConfirmUpdated() throws Exception {
		City city = new City();
		city.setId(15);
		city.setName("Orlando");
		city.setLatitude(28.5383);
		city.setLongitude(81.3792);

		mvc.perform(put("/city")
				.content(objectMapper.writeValueAsString(city))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		mvc.perform(get("/city/15")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(city.getName())))
				.andExpect(jsonPath("$.latitude", is(city.getLatitude())))
				.andExpect(jsonPath("$.longitude", is(city.getLongitude())));
	}

	@Test
	public void distanceBetweenCities() throws Exception {
		mvc.perform(get("/city/distance")
				.param("from", "1") //Tokyo
				.param("to", "3") //Toronto
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(is("10343.813377282666")));
	}

	@Test
	public void distanceBetweenCitiesWithMissingRequestParam() throws Exception {
		mvc.perform(get("/city/distance")
				.param("from", "1") //Tokyo
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void distanceBetweenCitiesWithInvalidIds() throws Exception {
		mvc.perform(get("/city/distance")
				.param("from", "100")
				.param("to", "9")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


}
