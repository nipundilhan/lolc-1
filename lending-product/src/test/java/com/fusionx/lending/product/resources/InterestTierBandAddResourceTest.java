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
 * InterestTierBandAddResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   26-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterestTierBandAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTierBandAddResource setInterestTierBandAddResource() {
		InterestTierBandAddResource interestTierBandAddResource = new InterestTierBandAddResource();
		interestTierBandAddResource.setCode("LOLC");
		interestTierBandAddResource.setInterestRateTypeId("1");
		interestTierBandAddResource.setInterestRateTypeName("TEST");
		interestTierBandAddResource.setLoanPrInterestRate("10");
		interestTierBandAddResource.setLoanPrInterestRateTypeId("1");
		interestTierBandAddResource.setLoanPrInterestRateTypeName("TEST");
		interestTierBandAddResource.setMaxTermPeriodId("1");
		interestTierBandAddResource.setMaxTermPeriodName("TEST");
		interestTierBandAddResource.setMinTermPeriodId("1");
		interestTierBandAddResource.setMinTermPeriodName("TEST");
		interestTierBandAddResource.setRepArp("1.00");
		interestTierBandAddResource.setTierValueMaximum("1.00");
		interestTierBandAddResource.setTierValueMinimum("1.00");
		interestTierBandAddResource.setTierValueMaxTermId("1");
		interestTierBandAddResource.setTierValueMaxTermName("TEST");
		interestTierBandAddResource.setTierValueMinTermId("1");
		interestTierBandAddResource.setTierValueMinTermName("TEST");
		interestTierBandAddResource.setNote("TEST");
		interestTierBandAddResource.setStatus("ACTIVE");
		return interestTierBandAddResource;
	}
	
	
    /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	InterestTierBandAddResource interestTierBandAddResource = setInterestTierBandAddResource();
    	interestTierBandAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	InterestTierBandAddResource interestTierBandAddResource = setInterestTierBandAddResource();
    	interestTierBandAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTierBandAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	InterestTierBandAddResource interestTierBandAddResource =setInterestTierBandAddResource();
    	interestTierBandAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandAddResource, localValidatorFactory));
	}
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	InterestTierBandAddResource interestTierBandAddResource = setInterestTierBandAddResource();
    	interestTierBandAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	InterestTierBandAddResource interestTierBandAddResource = setInterestTierBandAddResource();
    	interestTierBandAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandAddResource, localValidatorFactory));
	}


}
