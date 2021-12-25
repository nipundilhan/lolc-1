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
 * InterestTierBandSetUpdateResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   21-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class InterestTierBandSetUpdateResourceTest {
/*	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTierBandSetUpdateResource setInterestTierBandSetUpdateResource() {
		InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = new InterestTierBandSetUpdateResource();
		interestTierBandSetUpdateResource.setId("1");
		interestTierBandSetUpdateResource.setInterestTempPendingId("1");
		interestTierBandSetUpdateResource.setTierBandMethodId("1");
		interestTierBandSetUpdateResource.setTierBandMethodName("Tier");
		interestTierBandSetUpdateResource.setCode("LOLC");
		interestTierBandSetUpdateResource.setCalculateMethodId("1");
		interestTierBandSetUpdateResource.setCalculateMethodName("Calc");
		interestTierBandSetUpdateResource.setNote("note");
		interestTierBandSetUpdateResource.setStatus("ACTIVE");
		interestTierBandSetUpdateResource.setVersion("1");
		return interestTierBandSetUpdateResource;
	}
	
	 /**
     * Id should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void idPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * InterestTempPending should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void interestTempPendingIdPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setInterestTempPendingId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
	
	 /**
     * TierBandMethodId is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void tierBandMethodIdNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setTierBandMethodId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * TierBandMethodId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void tierBandMethodIdPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setTierBandMethodId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
	
	 /**
     * TierBandMethodName is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void tierBandMethodNameNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setTierBandMethodName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * TierBandMethodName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
/*    @Test
    public void tierBandMethodNameSize() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setTierBandMethodName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * code is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void codeNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
/*    @Test
	public void codeSize() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
 /*   @Test
	public void codePattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource =setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodId is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void calculateMethodIdNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCalculateMethodId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void calculateMethodIdPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCalculateMethodId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
	
	 /**
     *  CalculateMethodName is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void calculateMethodNameNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCalculateMethodName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
/*    @Test
    public void calculateMethodNameSize() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setCalculateMethodName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
/*    @Test
	public void statusNotNull() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
	public void statusPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}
   
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	InterestTierBandSetUpdateResource interestTierBandSetUpdateResource = setInterestTierBandSetUpdateResource();
    	interestTierBandSetUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetUpdateResource, localValidatorFactory));
	}*/

}
