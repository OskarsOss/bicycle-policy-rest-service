package com.hw.bicyclepolicyrestservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PremiumResponse {
	
	@Getter
	@Setter
	private List<InsuredObject> objects = new ArrayList<InsuredObject>();
	
	@Getter
	@Setter
	private double premium;

}
