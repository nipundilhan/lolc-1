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
 * InterestTierBandSetAddResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   21-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterestTierBandSetAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTierBandSetAddResource setInterestTierBandSetAddResource() {
		InterestTierBandSetAddResource interestTierBandSetAddResource = new InterestTierBandSetAddResource();
		interestTierBandSetAddResource.setTierBandMethodId("1");
		interestTierBandSetAddResource.setTierBandMethodName("Tier");
		interestTierBandSetAddResource.setCode("LOLC");
		interestTierBandSetAddResource.setCalculateMethodId("1");
		interestTierBandSetAddResource.setCalculateMethodName("Calc");
		interestTierBandSetAddResource.setNote("note");
		interestTierBandSetAddResource.setStatus("ACTIVE");
		return interestTierBandSetAddResource;
	}
	
	 /**
     * TierBandMethodId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void tierBandMethodIdNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setTierBandMethodId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * TierBandMethodId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void tierBandMethodIdPattern() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setTierBandMethodId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
	
	 /**
     * TierBandMethodName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void tierBandMethodNameNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setTierBandMethodName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * TierBandMethodName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void tierBandMethodNameSize() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setTierBandMethodName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
		
    }
    
    /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource =setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void calculateMethodIdNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCalculateMethodId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void calculateMethodIdPattern() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCalculateMethodId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
	
	 /**
     *  CalculateMethodName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void calculateMethodNameNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCalculateMethodName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
    /**
     * CalculateMethodName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void calculateMethodNameSize() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setCalculateMethodName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	InterestTierBandSetAddResource interestTierBandSetAddResource = setInterestTierBandSetAddResource();
    	interestTierBandSetAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTierBandSetAddResource, localValidatorFactory));
	}

}
