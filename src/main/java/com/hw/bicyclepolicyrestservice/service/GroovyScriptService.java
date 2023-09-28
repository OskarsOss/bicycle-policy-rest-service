package com.hw.bicyclepolicyrestservice.service;

import static com.hw.bicyclepolicyrestservice.utils.Utils.roundDoubleResult;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hw.bicyclepolicyrestservice.model.Risk;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import jakarta.annotation.PostConstruct;

@Service
public class GroovyScriptService {
	
	@Value("${scripts_location:src/main/resources/scripts}")
	private String scriptsLocation;
	
	private GroovyScriptEngine engine;
	
	private final static Logger logger = LoggerFactory.getLogger(GroovyScriptService.class);
	
	@PostConstruct
    private void postConstruct() {
		try {
			URL scriptsUrl = new File(scriptsLocation).toURI().toURL();
			this.engine = new GroovyScriptEngine(new URL[] {scriptsUrl}, this.getClass().getClassLoader());
		} catch (MalformedURLException e) {
			logger.error("Failed to create GroovyScriptEngine - " + e.getMessage());
		}
	}
	
	/**
    * Creates a Risk from calculated results by calling calculation groovy script based on risk name.
    * @param args Map<String, Object> of values required by the invoked script.
    * @return Risk object with calculated values. null if calculations fail.
    */
	public Risk runRiskCalculationScript(Map<String, Object> args) {
		String riskType = "";
        try {
        	if (engine == null) {
        		logger.error("can't call calculations on null engine. Return null.");
        		return null;
        	}
        	
        	riskType = (String) args.get("riskType");
        	
        	if (riskType == null || riskType.isEmpty()) {
        		logger.error("can't call calculations with no risk name");
        		return null;
        	}
        	      	
        	Class<?> calcClass = engine.loadScriptByName("risk_"+ riskType.toLowerCase() +".groovy");
            GroovyObject scriptInstance = (GroovyObject) calcClass.getDeclaredConstructor().newInstance();
        	
        	double premiumResult = (double) scriptInstance.invokeMethod("calculatePremium", new Object[] {args});
        	double sumInsuredResult = (double) scriptInstance.invokeMethod("calculateSumInsured", new Object[] {args.get("sumInsured")});
            return new Risk(riskType, roundDoubleResult(premiumResult), roundDoubleResult(sumInsuredResult));
        } catch (Exception e) {
            logger.error("risk calculation failed for risk: " + riskType + ". Return null. - " + e.getMessage());
            return null;
        }
    }

}
