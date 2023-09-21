package com.hw.bicyclepolicyrestservice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
{
    "make" : "Pearl",
    "model" : "Gravel SL EVO",
    "coverage" : "EXTRA",
    "manufactureYear" : 2015,
    "sumInsured" : 1000,
    "risks" : [
      "THEFT",
      "DAMAGE",
      "THIRD_PARTY_DAMAGE"
    ]
  }
*/

//"required": [
//	 "make",
//	 "model",
//	 "manufactureYear",
//	 "sumInsured"
//]

public class Bicycle {
	
	public enum CoverageType {
		STANDARD,
	    EXTRA
	}

	@Getter
	@Setter
	private String make;
	
	@Getter
	@Setter
	private String model;
	
	@Getter
	@Setter
	private CoverageType coverage = CoverageType.STANDARD;
	
	@Getter
	@Setter
	private int manufactureYear;
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private List<Risk.Type> risks = List.of( Risk.Type.THEFT );
}
