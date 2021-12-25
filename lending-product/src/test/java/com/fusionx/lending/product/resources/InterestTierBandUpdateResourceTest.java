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
 * InterestTierBandUpdateResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   26-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class InterestTierBandUpdateResourceTest {
/*	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTierBandUpdateResource setInterestTierBandUpdateResource() {
		InterestTierBandUpdateResource interestTierBandUpdateResource = new InterestTierBandUpdateResource();
		interestTierBandUpdateResource.setId("1");
		interestTierBandUpdateResource.setCode("LOLC");
		interestTierBandUpdateResource.setInterestRateTypeId("1");
		interestTierBandUpdateResource.setInterestRateTypeName("TEST");
		interestTierBandUpdateResource.setLoanPrInterestRate("10");
		interestTierBandUpdateResource.setLoanPrInterestRateTypeId("1");
		interestTierBandUpdateResource.setLoanPrInterestRateTypeName("TEST");
		interestTierBandUpdateResource.setMaxTermPeriodId("1");
		interestTierBandUpdateResource.setMaxTermPeriodName("TEST");
		interestTierBandUpdateResource.setMinTermPeriodId("1");
		interestTierBandUpdateResource.setMinTermPeriodName("TEST");
		interestTierBandUpdateResource.setRepArp("1.00");
		interestTierBandUpdateResource.setTierValueMaximum("1.00");
		interestTierBandUpdateResource.setTierValueMinimum("1.00");
		interestTierBandUpdateResource.setTierValueMaxTermId("1");
		interestTierBandUpdateResource.setTierValueMaxTermName("TEST");
		interestTierBandUpdateResource.setTierValueMinTermId("1");
		interestTierBandUpdateResource.setTierValueMinTermName("TEST");
		interestTierBandUpdateResource.setNote("TEST");
		interestTierBandUpdateResource.setStatus("ACTIVE");
		interestTierBandUpdateResource.setInterestTempPendingId("1");
		interestTierBandUpdateResource.setTierBandSetPendingId("1");
		interestTierBandUpdateResource.setVersion("1");
		return interestTierBandUpdateResource;
	}
	
	 /**
     * Id should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void idPattern() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
    /**
     * InterestTempPending should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void interestTempPendingIdPattern() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setInterestTempPendingId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
    /**
     * code is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void codeNotNull() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
 /*   @Test
	public void codeSize() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
 /*   @Test
	public void codePattern() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource =setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void statusNotNull() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
	public void statusPattern() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
   
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	InterestTierBandUpdateResource interestTierBandUpdateResource = setInterestTierBandUpdateResource();
    	interestTierBandUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandUpdateResource, localValidatorFactory));
	}
*/

}
