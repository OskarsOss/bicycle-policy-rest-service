package com.hw.bicyclepolicyrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hw.bicyclepolicyrestservice.service.BicyclePolicyService;

@RestController
public class BicyclePolicyController {
	
	private final BicyclePolicyService service;

	@Autowired
	public BicyclePolicyController(BicyclePolicyService service) {
		this.service = service;
	}


}
