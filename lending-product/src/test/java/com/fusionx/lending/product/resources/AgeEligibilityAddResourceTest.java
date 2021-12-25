package com.fusionx.lending.product.resources;

/**
 * Age Eligibility service
 * @author 	MenukaJ
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021  						    MenukaJ      Created
 *    
 ********************************************************************************************************
 */

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




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AgeEligibilityAddResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private AgeEligibilityAddResource setAgeEligibilityResource() {
		AgeEligibilityAddResource ageEligibilityResource = new AgeEligibilityAddResource();
		ageEligibilityResource.setMinimumAge("10");
		ageEligibilityResource.setMaximumAge("20");
		ageEligibilityResource.setStatus("ACTIVE");
		return ageEligibilityResource;
	}
	
	/**
     * Minimum age is required.
     * Expected: {minimumAge.not-null}
     */
    @Test
	public void minimumAgeNotNull() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMinimumAge(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Age cannot be greater than 3 characters.
     * Expected: {age.size}
     */
    @Test
	public void minimumAgeSize() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMinimumAge("1234");
		assertEquals("{age.size}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Invalid value.
     * Expected: {common.invalid-value}
     */
    @Test
	public void minimumAgeInvalidValue() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMinimumAge("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Maximum age is required.
     * Expected: {maximumAge.not-null}
     */
    @Test
	public void maximumAgeNotNull() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMaximumAge(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Age cannot be greater than 3 characters.
     * Expected: {age.size}
     */
    @Test
	public void maximumAgeSize() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMaximumAge("1234");
		assertEquals("{age.size}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Invalid value.
     * Expected: {common.invalid-value}
     */
    @Test
	public void maximumAgeInvalidValue() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setMaximumAge("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
    /**
     * Status is required.
     * Expected: {status.not-null}
     */
    @Test
	public void statusNotNull() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {status.pattern}
     */
    @Test
	public void statusPattern() {
    	AgeEligibilityAddResource ageEligibilityResource =setAgeEligibilityResource();
    	ageEligibilityResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(ageEligibilityResource, localValidatorFactory));
	}
	

}
