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
 * EligibilityCustomerTypeAddResourceTest 
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
public class EligibilityCustomerTypeAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityCustomerTypeAddResource setEligibilityCustomerTypeAddResource() {
		EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = new EligibilityCustomerTypeAddResource();
		eligibilityCustomerTypeAddResource.setEligibilityId("1");
		eligibilityCustomerTypeAddResource.setEligibilityName("TEST");
		eligibilityCustomerTypeAddResource.setCustomerTypeId("1");
		eligibilityCustomerTypeAddResource.setCustomerTypeName("TEST");
		eligibilityCustomerTypeAddResource.setStatus("ACTIVE");
		return eligibilityCustomerTypeAddResource;
	}
	
	 /**
     * EligibilityId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNotNull() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
    
    /**
     * EligibilityId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void eligibilityIdPattern() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setEligibilityId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
	
	 /**
     * EligibilityName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eligibilityIdNameNotNull() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setEligibilityName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
    
    /**
     * EligibilityName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void eligibilityIdNameSize() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setEligibilityName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
		
    }
    
    /**
     * CustomerTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void customerTypeIdNotNull() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setCustomerTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
    
    /**
     * CustomerTypeId should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void customerTypeIdPattern() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setCustomerTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
	
	 /**
     * customerTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void customerTypeNameNotNull() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setCustomerTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
    
    /**
     * customerTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void customerTypeNameSize() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setCustomerTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource = setEligibilityCustomerTypeAddResource();
    	eligibilityCustomerTypeAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCustomerTypeAddResource, localValidatorFactory));
	}


}
