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
 * EligibilityOtherUpdateResourceTest 
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING) */
public class EligibilityOtherUpdateResourceTest {

/*
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityOtherUpdateResource setEligibilityOtherUpdateResource() {
		EligibilityOtherUpdateResource eligibilityOtherUpdateResource = new EligibilityOtherUpdateResource();
		eligibilityOtherUpdateResource.setOtherEligibilityTypeId("1");
		eligibilityOtherUpdateResource.setOtherEligibilityTypeName("TEST");
		eligibilityOtherUpdateResource.setEligibilityPendingId("1");
		eligibilityOtherUpdateResource.setStatus("ACTIVE");
		eligibilityOtherUpdateResource.setVersion("1");
		return eligibilityOtherUpdateResource;
	}
	
	 /**
     * OtherEligibilityTypeId is required.
     * Expected: {common.not-null}
     */

/*	
	@Test
	public void otherEligibilityTypeIdNotNull() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setOtherEligibilityTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
    
    /**
     * OtherEligibilityTypeId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */

	/*@Test
	public void otherEligibilityTypeIdPattern() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setOtherEligibilityTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
	
	 /**
     * OtherEligibilityTypeName is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void otherEligibilityTypeNameNotNull() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setOtherEligibilityTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
    
    /**
     * OtherEligibilityTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
/*    @Test
    public void otherEligibilityTypeNameSize() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setOtherEligibilityTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
	public void statusPattern() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
    
    /**
     * eligibilityPendingId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void eligibilityPendingIdPattern() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
   
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void versionPattern() {
    	EligibilityOtherUpdateResource eligibilityOtherUpdateResource = setEligibilityOtherUpdateResource();
    	eligibilityOtherUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityOtherUpdateResource, localValidatorFactory));
	}
*/
}
