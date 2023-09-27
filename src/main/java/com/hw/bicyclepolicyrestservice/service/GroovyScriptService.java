package com.hw.bicyclepolicyrestservice.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hw.bicyclepolicyrestservice.model.Risk;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;

@Service
public class GroovyScriptService {
	
	@Value("${scripts_location:src/main/resources/scripts}")
	private String scriptsLocation; 
	
    public Risk runRiskCalculationScript(Map<String, Object> args) {
        try {
        	         
            String riskType = (String) args.get("riskType");
        	
        	URL scriptsUrl = new File(scriptsLocation).toURI().toURL();
        	
        	GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {scriptsUrl}, this.getClass().getClassLoader());
        	
        	Class<?> calcClass = engine.loadScriptByName("risk_"+ riskType.toLowerCase() +".groovy");
            GroovyObject scriptInstance = (GroovyObject) calcClass.getDeclaredConstructor().newInstance();
        	
        	double premiumResult = (double) scriptInstance.invokeMethod("calculatePremium", new Object[] {args});
        	double sumInsuredResult = (double) scriptInstance.invokeMethod("calculateSumInsured", new Object[] {args.get("sumInsured")});
            return new Risk(riskType, premiumResult, sumInsuredResult);
        } catch (Exception e) {
            System.out.println("risk calculation failed - " + e.getMessage());
            return null;
        }
    }
       
}
