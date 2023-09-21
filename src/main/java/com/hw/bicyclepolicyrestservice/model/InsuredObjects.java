package com.hw.bicyclepolicyrestservice.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
"attributes": {
    "MANUFACTURE_YEAR": 2015,
    "MODEL": "Gravel SL EVO",
    "MAKE": "Pearl"
},
"coverageType": "EXTRA",
"risks": [
    {
        "riskType": "THIRD_PARTY_DAMAGE",
        "sumInsured": 100.00,
        "premium": 12.00
    },
    {
        "riskType": "THEFT",
        "sumInsured": 1000.00,
        "premium": 30.00
    },
    {
        "riskType": "DAMAGE",
        "sumInsured": 500.00,
        "premium": 6.95
    }
],
"sumInsured": 1000,
"premium": 48.95
 */

public class InsuredObjects {
	
	@Getter
	@Setter
	private Map<String, Object> atributes;
	
	@Getter
	@Setter
	private String coverageType;
	
	@Getter
	@Setter
	private List<Risk> risks;
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private double premium;
	

}
