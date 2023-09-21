package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PremiumResponse {
	
	@Getter
	@Setter
	private List<InsuredObjects> objects;
	
	@Getter
	@Setter
	private double premium;

}
