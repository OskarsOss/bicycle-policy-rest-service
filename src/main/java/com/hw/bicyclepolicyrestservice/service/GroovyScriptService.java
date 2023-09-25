package com.hw.bicyclepolicyrestservice.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

@Service
public class GroovyScriptService {
	// TODO pass object
    public String runGroovyScript() {
        try {
            GroovyClassLoader classLoader = new GroovyClassLoader();
            String risk = "damage";
            Class<?> groovyClass = classLoader.parseClass(new File("G:\\DEV\\homework\\bicycle-policy-rest-service\\src\\main\\resources\\risk_"+risk+".groovy"));

            // Instantiate the Groovy class
            GroovyObject groovyObject = (GroovyObject) groovyClass.getDeclaredConstructor().newInstance();

            // Call the function defined in the script
            Object[] arguments = {2200, 13, "Whyte", "T-160 RS"}; // TODO remove (from args)
            Object result = groovyObject.invokeMethod("calculatePremium", arguments);
            
            
            System.out.println(risk + " premium: "+ (double)result);

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error running Groovy script: " + e.getMessage();
        }
    }
}
