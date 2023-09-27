package com.hw.bicyclepolicyrestservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.bicyclepolicyrestservice.model.Bicycle;
import com.hw.bicyclepolicyrestservice.model.CoverageType;
import com.hw.bicyclepolicyrestservice.model.InsuredObject;
import com.hw.bicyclepolicyrestservice.model.PremiumRequest;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.model.Risk;
import com.hw.bicyclepolicyrestservice.service.BicyclePolicyService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BicyclePolicyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BicyclePolicyService service;
	
	@Test
	public void givenValidRequest_whenPostOnCalculate_thenReturnValidResponse() throws Exception {
		
		Bicycle bike = new Bicycle("Test make", "Test model", CoverageType.EXTRA, 2016, 1000, List.of("DAMAGE"));
		List<Bicycle> bicycles = List.of(bike);
		
		PremiumRequest request = new PremiumRequest();
		request.setBicycles(bicycles);
		
		PremiumResponse serviceResponse = new PremiumResponse();
		serviceResponse.setPremium(2234);
		InsuredObject obj1 = new InsuredObject(bike);
		Risk risk1 = new Risk("DAMAGE", 144, 1144);
		obj1.setRisks(List.of(risk1));
		List<InsuredObject> objects = List.of(obj1);
		serviceResponse.setObjects(objects);
		
		when(service.calculatePremium(any())).thenReturn(serviceResponse);
		
		this.mockMvc.perform(
				post("/calculate")
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
//		.andDo(print())
		.andExpect(jsonPath("$.premium").value(serviceResponse.getPremium()))
		.andExpect(jsonPath("$.objects").exists()).andExpect(jsonPath("$.objects", hasSize(1)))
		.andExpect(jsonPath("$.objects[0]").exists()).andExpect(jsonPath("$.objects[0].coverageType").exists())
		.andExpect(jsonPath("$.objects[0].coverageType").value(bike.getCoverage().toString()))
		.andExpect(jsonPath("$.objects[0].risks").exists())
		.andExpect(jsonPath("$.objects[0].risks", hasSize(1)))
		.andExpect(jsonPath("$.objects[0].risks[0].riskType").value(risk1.getRiskType()))
		.andExpect(jsonPath("$.objects[0].risks[0].sumInsured").value(risk1.getSumInsured()))
		.andExpect(jsonPath("$.objects[0].risks[0].premium").value(risk1.getPremium()))
		.andExpect(jsonPath("$.objects[0].attributes").exists())
		.andExpect(jsonPath("$.objects[0].attributes.MANUFACTURE_YEAR").value(bike.getManufactureYear()))
		.andExpect(jsonPath("$.objects[0].attributes.MAKE").value(bike.getMake()))
		.andExpect(jsonPath("$.objects[0].attributes.MODEL").value(bike.getModel()));
	}
	
	@Test
	public void givenInvalidManufactureDate_whenPostOnCalculate_thenReturn400() throws Exception {
		PremiumRequest request = new PremiumRequest();
		request.setBicycles(List.of(new Bicycle("Test make", "Test model", CoverageType.EXTRA, 2000, 1000, List.of("DAMAGE"))));
		this.mockMvc.perform(
				post("/calculate")
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenInvalidSumInsured_whenPostOnCalculate_thenReturn400() throws Exception {
		PremiumRequest request = new PremiumRequest();
		request.setBicycles(List.of(new Bicycle("Test make", "Test model", CoverageType.EXTRA, 2020, 50000, List.of("DAMAGE"))));
		this.mockMvc.perform(
				post("/calculate")
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyMake_whenPostOnCalculate_thenReturn400() throws Exception {
		PremiumRequest request = new PremiumRequest();
		request.setBicycles(List.of(new Bicycle("", "Test model", CoverageType.EXTRA, 2020, 1000, List.of("DAMAGE"))));
		this.mockMvc.perform(
				post("/calculate")
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyModel_whenPostOnCalculate_thenReturn400() throws Exception {
		PremiumRequest request = new PremiumRequest();
		request.setBicycles(List.of(new Bicycle("Test make", "", CoverageType.EXTRA, 2020, 1000, List.of("DAMAGE"))));
		this.mockMvc.perform(
				post("/calculate")
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
