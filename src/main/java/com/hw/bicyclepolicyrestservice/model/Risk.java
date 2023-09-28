package com.hw.bicyclepolicyrestservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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
	
	public Risk(String riskType, double premium, double sumInsured) {
		this.riskType = riskType;
		this.premium = premium;
		this.sumInsured = sumInsured;
	}
	
	@Override
    public String toString() {
        return riskType;
    }

}
