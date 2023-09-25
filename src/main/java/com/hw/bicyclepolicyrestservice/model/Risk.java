package com.hw.bicyclepolicyrestservice.model;

import lombok.Getter;
import lombok.Setter;

public class Risk {
	
	@Getter
	@Setter
	private String riskType = "THEFT";
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private double premium;
	
	public Risk(String riskType) {
		this.riskType = riskType;
	}
	
	@Override
    public String toString() {
        return riskType;
    }

}
