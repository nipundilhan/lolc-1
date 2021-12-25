package com.fusionx.casa.transaction.utill;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class TestUtils {
    public static String getFieldErrorMessageKey(Object payload, LocalValidatorFactoryBean localValidatorFactory) {
        Set<ConstraintViolation<Object>> constraintViolations = localValidatorFactory.validate(payload);
        return constraintViolations.size() > 0 ? constraintViolations.iterator().next().getMessageTemplate() : null;
    }
}
