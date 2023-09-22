package com.hw.bicyclepolicyrestservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hw.bicyclepolicyrestservice.model.Bicycle;
import com.hw.bicyclepolicyrestservice.model.InsuredObject;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.model.Risk;

@Service
public class BicyclePolicyService {
	
	public PremiumResponse calculatePremium(List<Bicycle> bicycles) {
		
		PremiumResponse result = new PremiumResponse();
		
		// TODO
		double totalPremium = 99.19;
		List<InsuredObject> insuredObjects = new ArrayList<InsuredObject>();
		
		for (Bicycle bicycle : bicycles) {			
			InsuredObject insuredObject = new InsuredObject();
			insuredObject.setCoverageType(bicycle.getCoverage());
			insuredObject.setSumInsured(bicycle.getSumInsured());
			insuredObject.addAttribute("MANUFACTURE_YEAR", bicycle.getManufactureYear());
			insuredObject.addAttribute("MODEL", bicycle.getModel());
			insuredObject.addAttribute("MAKE", bicycle.getMake());
			
			List<Risk> risks = new ArrayList<Risk>();
			double calculatedPremium = 0;
			for (String riskName : bicycle.getRisks()) {
				Risk risk = new Risk(riskName);
				
				double riskPremium = 0;
				double riskSumInsured = 0;
				// TODO call groovy calculations
				
				risk.setSumInsured(riskSumInsured);
				risk.setPremium(riskPremium);
				risks.add(risk);
				
				calculatedPremium += riskPremium;
			}
			
			
			insuredObject.setPremium(calculatedPremium);
			insuredObject.setRisks(risks);
			
			
			
			insuredObjects.add(insuredObject);
			
			totalPremium += calculatedPremium;
		}
		
		result.setObjects(insuredObjects);
		result.setPremium(totalPremium);
		
		return result;
	}

}
