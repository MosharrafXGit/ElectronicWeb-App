package com.lcwd.electronicstore.validate;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String>{


	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		//logic
		
		if(value.isBlank())
		{
			return false;
		}
		else
		{
			return true;
		}
		
		
	}

}
