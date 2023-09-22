package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class PremiumRequest {
	
	@Getter
	@Setter
	@NotEmpty
	@Valid
	private List<Bicycle> bicycles;

}
