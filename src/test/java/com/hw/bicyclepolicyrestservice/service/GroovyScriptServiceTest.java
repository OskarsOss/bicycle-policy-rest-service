package com.hw.bicyclepolicyrestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hw.bicyclepolicyrestservice.model.Risk;

@SpringBootTest(properties = {"scripts_location=src/test/resources/scripts"})
public class GroovyScriptServiceTest {

	@Autowired
	private GroovyScriptService service;
	
	@Test
	public void givenValidArgs_whenRunRiskCalculationScript_thenReturnCalculatedRisk() throws Exception {
	
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("riskType", "MOCK");
		args.put("sumInsured", 222);
		args.put("age", 3);
		args.put("make", "test make");
		args.put("model", "test model");
		args.put("riskCount", 1);
		
		Risk testRisk = service.runRiskCalculationScript(args);
		
		assertNotNull(testRisk);
		assertEquals(testRisk.getRiskType(), "MOCK");
		assertEquals(testRisk.getPremium(), 42);
		assertEquals(testRisk.getSumInsured(), 222 + 1);
	}
	
	@Test
	public void givenInvalidRiskName_whenRunRiskCalculationScript_thenReturnNull() throws Exception {
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("riskType", "FAIL");
		
		Risk testRisk = service.runRiskCalculationScript(args);
	
		assertNull(testRisk);
	}
	
}
