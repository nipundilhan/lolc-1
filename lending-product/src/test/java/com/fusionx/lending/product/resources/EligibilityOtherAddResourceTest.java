package com.fusionx.lending.product.resources;

import static org.junit.Assert.assertEquals;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fusionx.lending.product.utill.TestUtils;

/**
 * EligibilityOtherAddResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class EligibilityOtherAddResourceTest {
/*	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityOtherAddResource setEligibilityOtherAddResource() {
		EligibilityOtherAddResource eligibilityOtherAddResource = new EligibilityOtherAddResource();
		eligibilityOtherAddResource.setOtherEligibilityTypeId("1");
		eligibilityOtherAddResource.setOtherEligibilityTypeName("TEST");
		eligibilityOtherAddResource.setStatus("ACTIVE");
		return eligibilityOtherAddResource;
	}
	
	 /**
     * OtherEligibilityTypeId is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void otherEligibilityTypeIdNotNull() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setOtherEligibilityTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
	}
    
    /**
     * OtherEligibilityTypeId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void otherEligibilityTypeIdPattern() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setOtherEligibilityTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
	}
	
	 /**
     * OtherEligibilityTypeName is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void tierBandMethodNameNotNull() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setOtherEligibilityTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
	}
    
    /**
     * OtherEligibilityTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
 /*   @Test
    public void tierBandMethodNameSize() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setOtherEligibilityTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
  /*  @Test
	public void statusPattern() {
    	EligibilityOtherAddResource eligibilityOtherAddResource = setEligibilityOtherAddResource();
    	eligibilityOtherAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherAddResource, localValidatorFactory));
	}

*/
}
