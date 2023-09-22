package com.hw.bicyclepolicyrestservice.validation;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<NotOlderThan, Integer> {

	private int ageLimit;

	@Override
	public void initialize(NotOlderThan constraintAnnotation) {
		this.ageLimit = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Integer year, ConstraintValidatorContext cxt) {
		int yearNow = LocalDate.now().getYear();
		return year >= yearNow - ageLimit;
	}

}
