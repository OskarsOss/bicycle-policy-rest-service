package com.hw.bicyclepolicyrestservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hw.bicyclepolicyrestservice.model.PremiumRequest;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.service.BicyclePolicyService;

import jakarta.validation.Valid;

@RestController
public class BicyclePolicyController {
	
	private final BicyclePolicyService service;

	@Autowired
	public BicyclePolicyController(BicyclePolicyService service) {
		this.service = service;
	}
	
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public ResponseEntity<Object> calculate(@Valid @RequestBody PremiumRequest request) {
		PremiumResponse response = service.calculatePremium(request.getBicycles());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
