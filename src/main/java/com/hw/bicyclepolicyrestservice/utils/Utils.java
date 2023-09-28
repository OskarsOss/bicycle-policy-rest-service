package com.hw.bicyclepolicyrestservice.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public final class Utils {
	
	/**
	 * Helper function to round double values to two decimal places.
	 * @param result double value to be rounded
	 * @return rounded value with two decimal places
	 */
	public static double roundDoubleResult(double result) {
    	BigDecimal bd = new BigDecimal(result).setScale(2, RoundingMode.UP);
    	return bd.doubleValue();
    }
	
	public static int getAge(int manufactureYear) {
		return LocalDate.now().getYear() - manufactureYear;
	}

}
