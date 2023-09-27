package com.hw.bicyclepolicyrestservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PremiumResponse {
	
	@Getter
	@Setter
	private List<InsuredObject> objects = new ArrayList<InsuredObject>();
	
	@Getter
	@Setter
	private double premium;
	
}
