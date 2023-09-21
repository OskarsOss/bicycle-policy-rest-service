package com.hw.bicyclepolicyrestservice.model;

import lombok.Getter;
import lombok.Setter;

/*
{
    "riskType": "THEFT",
    "sumInsured": 1000.00,
    "premium": 30.00
}
 */

public class Risk {
	
	public enum Type {
		THEFT,
		DAMAGE,
		THIRD_PARTY_DAMAGE
	}
	
	@Getter
	@Setter
	private Type riskType = Type.THEFT;
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private double premium;
	
	public Risk(String riskTypeString) {
		try {
			this.riskType = Type.valueOf(riskTypeString);
		} catch (IllegalArgumentException e) {
			// should not normally occur because of validation
		    System.out.println("Invalid enum string for Risk: " + riskTypeString + "! Set default: \"THEFT\"");
		    this.riskType = Type.THEFT;
		}
	}
	
	@Override
    public String toString() {
        return riskType.name();
    }

}
