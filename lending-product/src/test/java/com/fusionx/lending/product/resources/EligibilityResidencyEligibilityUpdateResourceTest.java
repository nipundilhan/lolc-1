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
 * EligibilityResidencyEligibilityUpdateResourceTest 
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
public class EligibilityResidencyEligibilityUpdateResourceTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityResidencyEligibilityUpdateResource setEligibilityResidencyEligibilityUpdateResource() {
		EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = new EligibilityResidencyEligibilityUpdateResource();
		eligibilityResidencyEligibilityUpdateResource.setId("1");
		eligibilityResidencyEligibilityUpdateResource.setEligibilityPendingId("1");
		eligibilityResidencyEligibilityUpdateResource.setEligibilityId("1");
		eligibilityResidencyEligibilityUpdateResource.setEligibilityName("TEST");
		eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityId("1");
		eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityName("TEST");
		eligibilityResidencyEligibilityUpdateResource.setStatus("ACTIVE");
		eligibilityResidencyEligibilityUpdateResource.setVersion("1");
		return eligibilityResidencyEligibilityUpdateResource;
	}
	
	 
    /**
     * Id should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void idPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityPendingId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityPendingIdPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setEligibilityPendingId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNotNull() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityIdPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNameNotNull() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void eligibilityIdNameSize() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * ResidencyEligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void residencyEligibilityIdNotNull() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * ResidencyEligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void residencyEligibilityIdPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
	
	 /**
     * residencyEligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void residencyEligibilityNameNotNull() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * residencyEligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void residencyEligibilityNameSize() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setResidencyEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource = setEligibilityResidencyEligibilityUpdateResource();
    	eligibilityResidencyEligibilityUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityResidencyEligibilityUpdateResource, localValidatorFactory));
	}



}
