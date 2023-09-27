package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import com.hw.bicyclepolicyrestservice.validation.NotOlderThan;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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
	private List<String> risks = List.of( "THEFT" );

}
