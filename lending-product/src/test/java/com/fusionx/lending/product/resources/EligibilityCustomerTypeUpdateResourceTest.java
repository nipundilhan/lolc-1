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
 * EligibilityCustomerTypeUpdateResourceTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   29-07-2021    FXL_365			 	FXL-56		Piyumi      Created
 *    
 *******************************************************************************************************
 */ 

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityCustomerTypeUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityCustomerTypeUpdateResource setEligibilityCustomerTypeUpdateResource() {
		EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = new EligibilityCustomerTypeUpdateResource();
		eligibilityCustomerTypeUpdateResource.setId("1");
		eligibilityCustomerTypeUpdateResource.setEligibilityPendingId("1");
		eligibilityCustomerTypeUpdateResource.setEligibilityId("1");
		eligibilityCustomerTypeUpdateResource.setEligibilityName("TEST");
		eligibilityCustomerTypeUpdateResource.setCustomerTypeId("1");
		eligibilityCustomerTypeUpdateResource.setCustomerTypeName("TEST");
		eligibilityCustomerTypeUpdateResource.setStatus("ACTIVE");
		eligibilityCustomerTypeUpdateResource.setVersion("1");
		return eligibilityCustomerTypeUpdateResource;
	}
	
	 
    /**
     * Id should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void idPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityPendingId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityPendingIdPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setEligibilityPendingId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNotNull() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityIdPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNameNotNull() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * EligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void eligibilityIdNameSize() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * CustomerTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void customerTypeIdNotNull() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setCustomerTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * CustomerTypeId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void customerTypeIdPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setCustomerTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
	
	 /**
     * customerTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void customerTypeNameNotNull() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setCustomerTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * customerTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void customerTypeNameSize() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setCustomerTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource = setEligibilityCustomerTypeUpdateResource();
    	eligibilityCustomerTypeUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeUpdateResource, localValidatorFactory));
	}



}
