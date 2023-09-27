package com.hw.bicyclepolicyrestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hw.bicyclepolicyrestservice.model.Bicycle;
import com.hw.bicyclepolicyrestservice.model.CoverageType;
import com.hw.bicyclepolicyrestservice.model.InsuredObject;
import com.hw.bicyclepolicyrestservice.model.PremiumResponse;
import com.hw.bicyclepolicyrestservice.model.Risk;

@SpringBootTest
public class BicyclePolicyServiceTest {
	
	@Autowired
	private BicyclePolicyService service;
	
	@MockBean
	private GroovyScriptService mockService;
	
	@Test
	public void givenBicycleWithTwoRisks_whenCalculatePremium_thenValidPremiumResponseObject() throws Exception {
		Risk risk1 = new Risk("DAMAGE", 144, 1144);
		Risk risk2 = new Risk("THEFT", 200, 2333);
		Bicycle bike = new Bicycle("Test make", "Test model", CoverageType.EXTRA, 2016, 1000, List.of(risk1.getRiskType(), risk2.getRiskType()));
		List<Bicycle> bicycles = List.of(bike);
			
		HashMap<String, Object> args1 = new HashMap<String, Object>();
		args1.put("riskType", risk1.getRiskType());
		args1.put("sumInsured", bike.getSumInsured());
		args1.put("age", LocalDate.now().getYear() - bike.getManufactureYear());
		args1.put("make", bike.getMake());
		args1.put("model", bike.getModel());
		args1.put("riskCount", bike.getRisks().size());
		
		when(mockService.runRiskCalculationScript(args1)).thenReturn(risk1);
		
		HashMap<String, Object> args2 = (HashMap<String, Object>) args1.clone();
		args2.put("riskType", risk2.getRiskType());
		
		when(mockService.runRiskCalculationScript(args2)).thenReturn(risk2);
		
		
		// tests
		PremiumResponse result = service.calculatePremium(bicycles);
		assertNotNull(result);
		assertEquals(result.getObjects().size(), 1);
		assertEquals(result.getPremium(), risk1.getPremium() + risk2.getPremium());

		InsuredObject testObj = result.getObjects().get(0);
		assertEquals(testObj.getCoverageType(), bike.getCoverage());
		assertEquals(testObj.getSumInsured(), bike.getSumInsured());
		assertEquals(testObj.getPremium(), risk1.getPremium() + risk2.getPremium());
		assertEquals(testObj.getRisks().size(), 2);

		Risk testRisk1 = testObj.getRisks().get(0);
		assertEquals(testRisk1.getRiskType(), risk1.getRiskType());
		assertEquals(testRisk1.getPremium(), risk1.getPremium());
		assertEquals(testRisk1.getSumInsured(), risk1.getSumInsured());
		
		Risk testRisk2 = testObj.getRisks().get(1);
		assertEquals(testRisk2.getRiskType(), risk2.getRiskType());
		assertEquals(testRisk2.getPremium(), risk2.getPremium());
		assertEquals(testRisk2.getSumInsured(), risk2.getSumInsured());
	}
	
	@Test
	public void givenNullCalculatedRisk_whenCalculatePremium_thenThrowsException() throws Exception {
		when(mockService.runRiskCalculationScript(any())).thenReturn(null);
		
		Risk risk1 = new Risk("DAMAGE", 144, 1144);
		List<Bicycle> bicycles = new ArrayList<Bicycle>();
		Bicycle bike = new Bicycle("Test make", "Test model", CoverageType.EXTRA, 2016, 1000, List.of(risk1.getRiskType()));
		bicycles.add(bike);
		
		// tests
		RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
			service.calculatePremium(bicycles);
		});

		Assertions.assertEquals("failed calculations for risk: DAMAGE", thrown.getMessage());
	}

}
