package com.hw.bicyclepolicyrestservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.bicyclepolicyrestservice.model.Bicycle;
import com.hw.bicyclepolicyrestservice.model.InsuredObject;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.model.Risk;

@Service
public class BicyclePolicyService {
	
	private final GroovyScriptService groovyService;

	@Autowired
	public BicyclePolicyService(GroovyScriptService groovyService) {
		this.groovyService = groovyService;
	}
	
	public PremiumResponse calculatePremium(List<Bicycle> bicycles) {
		double totalPremium = 0;
		List<InsuredObject> insuredObjects = new ArrayList<InsuredObject>();
		
		for (Bicycle bicycle : bicycles) {			
			List<Risk> risks = new ArrayList<Risk>();
			double calculatedPremium = 0;
			
			for (String riskName : bicycle.getRisks()) {
				Map<String, Object> args = getRiskCalculationArgs(riskName, bicycle);
				Risk risk = groovyService.runRiskCalculationScript(args);
				if (risk == null ) {
					throw new RuntimeException("failed calculations for risk: " + args.get("riskType"));
				}
				
				risks.add(risk);
				calculatedPremium += risk.getPremium();
			}
			
			InsuredObject insuredObject = new InsuredObject(bicycle);
			insuredObject.setPremium(calculatedPremium);
			insuredObject.setRisks(risks);

			insuredObjects.add(insuredObject);
			totalPremium += calculatedPremium;
		}
		
		return new PremiumResponse(insuredObjects, totalPremium);
	}
	
	private Map<String, Object> getRiskCalculationArgs(String riskName, Bicycle bicycle) {
		Map<String, Object> args = new HashMap<String, Object>();
        args.put("riskType", riskName);
        args.put("sumInsured", bicycle.getSumInsured());
        args.put("age", getAge(bicycle.getManufactureYear()));
        args.put("make", bicycle.getMake());
        args.put("model", bicycle.getModel());
        args.put("riskCount", bicycle.getRisks().size());
        return args;
	}
	
	private int getAge(int manufactureYear) {
		return LocalDate.now().getYear() - manufactureYear;
	}

}
