package com.hw.bicyclepolicyrestservice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class InsuredObject {
	
	@Getter
	@Setter
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	@Getter
	@Setter
	private CoverageType coverageType;
	
	@Getter
	@Setter
	private List<Risk> risks;
	
	@Getter
	@Setter
	private double sumInsured;
	
	@Getter
	@Setter
	private double premium;
	
	public InsuredObject(Bicycle bicycle) {
		setCoverageType(bicycle.getCoverage());
		setSumInsured(bicycle.getSumInsured());
		addAttribute("MANUFACTURE_YEAR", bicycle.getManufactureYear());
		addAttribute("MODEL", bicycle.getModel());
		addAttribute("MAKE", bicycle.getMake());
	}

	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}
}
