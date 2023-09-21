package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PremiumRequest {
	
	@Getter
	@Setter
	private List<Bicycle> bicycles;

}
