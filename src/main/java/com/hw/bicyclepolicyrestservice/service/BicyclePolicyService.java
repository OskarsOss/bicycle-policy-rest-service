package com.hw.bicyclepolicyrestservice.service;

import static com.hw.bicyclepolicyrestservice.utils.Utils.roundDoubleResult;
import static com.hw.bicyclepolicyrestservice.utils.Utils.getAge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.bicyclepolicyrestservice.model.Bicycle;
import com.hw.bicyclepolicyrestservice.model.InsuredObject;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.model.Risk;

@Service
public class BicyclePolicyService {
	
	private final static Logger logger = LoggerFactory.getLogger(BicyclePolicyService.class);
	
	private final GroovyScriptService groovyService;	

	@Autowired
	public BicyclePolicyService(GroovyScriptService groovyService) {
		this.groovyService = groovyService;
	}
	
	/**
    * Calculates insurance details for the provided List of Bicycles
    * @param bicycles List of Bicycle objects
    * @return PremiumResponse with calculated details - InsuredObjects and total premium
    */
	public PremiumResponse calculatePremium(List<Bicycle> bicycles) {
		double totalPremium = 0;
		List<InsuredObject> insuredObjects = new ArrayList<InsuredObject>();
		
		for (Bicycle bicycle : bicycles) {			
			InsuredObject insuredObject = calculateInsuredObjectForBicycle(bicycle);
			insuredObjects.add(insuredObject);
			totalPremium += insuredObject.getPremium();
		}
		
		return new PremiumResponse(insuredObjects, roundDoubleResult(totalPremium));
	}
	
	/**
    * Creates InsuredObject for the specific bicycle with each Risk details all and total premium for the bicycle. 
    * @param bicycle Bicycle object
    * @return InsuredObject with calculated details for the specific bicycle
    */
	private InsuredObject calculateInsuredObjectForBicycle(Bicycle bicycle) {
		List<Risk> risks = new ArrayList<Risk>();
		double calculatedPremium = 0;
		
		for (String riskName : bicycle.getRisks()) {
			Risk risk = calculateRiskForBicycle(riskName, bicycle);
			risks.add(risk);
			calculatedPremium += risk.getPremium();
		}
		
		InsuredObject insuredObject = new InsuredObject(bicycle);
		insuredObject.setPremium(roundDoubleResult(calculatedPremium));
		insuredObject.setRisks(risks);
		return insuredObject;
	}
	
	/**
    * Creates Risk object for the provided risk name and bicycle. Calls groovy service for calculations.
    * @param riskName name of the risk
    * @param bicycle Bicycle object
    * @return Risk object with calculated values.
    */
	private Risk calculateRiskForBicycle(String riskName, Bicycle bicycle) {
		Map<String, Object> args = getRiskCalculationArgs(riskName, bicycle);
		Risk risk = groovyService.runRiskCalculationScript(args);
		if (risk == null ) {
			logger.error("failed calculations (null result) for risk: " + riskName);
			throw new RuntimeException("failed calculations for risk: " + riskName);
		}
		return risk;
	}
	
	/**
    * Creates argument map from risk name and bicycle details for groovy script service call.
    * @param riskName name of the risk
    * @param bicycle details object of the bicycle
    * @return Map of arguments
    */
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

}
