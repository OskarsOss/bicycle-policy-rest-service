package com.hw.bicyclepolicyrestservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hw.bicyclepolicyrestservice.service.BicyclePolicyService;

@SpringBootTest
class BicyclePolicyRestServiceApplicationTests {
	
	@Autowired
	private BicyclePolicyService service;
	
	@Test
	void contextLoads() {
		assertNotNull(service);
	}

}
