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
 * EligibilityResidencyEligibilityAddResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityResidencyEligibilityAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityResidencyEligibilityAddResource setEligibilityResidencyEligibilityAddResource() {
		EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = new EligibilityResidencyEligibilityAddResource();
		eligibilityResidencyEligibilityAddResource.setEligibilityId("1");
		eligibilityResidencyEligibilityAddResource.setEligibilityName("TEST");
		eligibilityResidencyEligibilityAddResource.setResidencyEligibilityId("1");
		eligibilityResidencyEligibilityAddResource.setResidencyEligibilityName("TEST");
		eligibilityResidencyEligibilityAddResource.setStatus("ACTIVE");
		return eligibilityResidencyEligibilityAddResource;
	}
	
	 /**
     * EligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNotNull() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
    
    /**
     * EligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityIdPattern() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNameNotNull() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
    
    /**
     * EligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void eligibilityIdNameSize() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
		
    }
    
    /**
     * ResidencyEligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void residencyEligibilityIdNotNull() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setResidencyEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
    
    /**
     * ResidencyEligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void residencyEligibilityIdPattern() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setResidencyEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
	
	 /**
     * residencyEligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void residencyEligibilityNameNotNull() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setResidencyEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
    
    /**
     * residencyEligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void residencyEligibilityNameSize() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setResidencyEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource = setEligibilityResidencyEligibilityAddResource();
    	eligibilityResidencyEligibilityAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityAddResource, localValidatorFactory));
	}


}
