package com.fusionx.lending.product.utill;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class TestUtils {
	public static String getFieldErrorMessageKey(Object payload, LocalValidatorFactoryBean localValidatorFactory) {
        Set<ConstraintViolation<Object>> constraintViolations = localValidatorFactory.validate(payload);
        return constraintViolations.size() > 0 ? constraintViolations.iterator().next().getMessageTemplate() : null;
    }
}
