package com.hw.bicyclepolicyrestservice.validation;

import com.hw.bicyclepolicyrestservice.model.RiskType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RiskTypeValidator implements ConstraintValidator<ValidRisk, String> {

	@Override
	public void initialize(ValidRisk constraintAnnotation) {
	}

	@Override
	public boolean isValid(String riskTypeString, ConstraintValidatorContext cxt) {
		boolean valid = true;
		try {
			RiskType.valueOf(riskTypeString);
		} catch (IllegalArgumentException e) {
			valid = false;
		}
		return valid;
	}

}