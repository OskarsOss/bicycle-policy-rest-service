package com.hw.bicyclepolicyrestservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilsTest {
	
	@Test
	public void givenDoubleValueWithManyDecimals_whenRoundDoubleResult_thenRoundToTwoDecimals() throws Exception {
		double result = Utils.roundDoubleResult(13.49999995);
		assertEquals(13.50, result);
		
		double result2 = Utils.roundDoubleResult(13.411113);
		assertEquals(13.42, result2);
		
		double result3 = Utils.roundDoubleResult(13.4651);
		assertEquals(13.47, result3);
	}

}
