package com.hw.bicyclepolicyrestservice.model;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

public class Risk {
	
	@Getter
	@Setter
	private RiskType riskType = RiskType.THEFT;
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private double premium;
	
	public Risk(RiskType riskType) {
		this.riskType = riskType;
	}
	
	public Risk(String riskTypeString) {
		try {
			this.riskType = RiskType.valueOf(riskTypeString);
		} catch (IllegalArgumentException e) {
			// should not normally occur because of validation
		    System.out.println("Invalid risk type string for Risk: " + riskTypeString + "! Set default: \"THEFT\"");
		    this.riskType = RiskType.THEFT;
		}
	}
	
	public static Risk ofType(RiskType riskType) {
		return new Risk(riskType);
	}
	
	@Override
    public String toString() {
        return riskType.name();
    }

}
