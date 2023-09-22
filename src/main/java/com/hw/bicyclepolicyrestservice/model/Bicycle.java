package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.hw.bicyclepolicyrestservice.validation.NotOlderThan;
import com.hw.bicyclepolicyrestservice.validation.ValidRisk;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class Bicycle {

	@Getter
	@Setter
	@NotBlank
	private String make;
	
	@Getter
	@Setter
	@NotBlank
	private String model;
	
	@Getter
	@Setter
	private CoverageType coverage = CoverageType.STANDARD;
	
	@Getter
	@Setter
	@Positive
	@NotOlderThan(value = 10)
	private int manufactureYear;
	
	@Getter
	@Setter
	@Positive
	@Max(value = 10000)
	private double sumInsured;
	
	@Getter
	@Setter
	private List<@ValidRisk String> risks = List.of( RiskType.THEFT.name() );

}
