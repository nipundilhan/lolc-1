package com.fusionx.lending.product.resources;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
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

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class ResidencyEligibilityResourceTest {
/*	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ResidencyEligibilityResource setResidencyEligibilityResource() {
		ResidencyEligibilityResource residencyEligibilityResource = new ResidencyEligibilityResource();
		residencyEligibilityResource.setCode("test");
		residencyEligibilityResource.setResidencyTypeId("282");
		residencyEligibilityResource.setResidencyTypeName("Test");
		residencyEligibilityResource.setStatus("ACTIVE");
		return residencyEligibilityResource;
	}
	
	/**
     * Residency type  is required.
     * Expected: {residencyTypeId.not-null}
     */
 /*   @Test
	public void residencyTypeIdNotNull() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setResidencyTypeId(null);
		assertEquals("{residencyTypeId.not-null}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Invalid value.
     * Expected: {common.invalid-value}
     */
/*    @Test
	public void residencyTypeIdInvalidValue() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setResidencyTypeId("ABCDEF");
		assertEquals("{common.invalid-value}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
	}
    
    /**
     * Residency Type Name is required.
     * Expected: {residencyTypeName.not-null}
     */
 /*   @Test
	public void residencyTypeNameNotNull() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setResidencyTypeName(null);
		assertEquals("{residencyTypeName.not-null}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
	}
    
    /**
     * Residency Type name cannot be greater than 70 characters.
     * Expected: {residencyTypeName.size}
     */
 /*   @Test
    public void residencyTypeNameSize() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setResidencyTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{residencyTypeName.size}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
		
    }
    
	/**
     * Type should be MATURITY or HOUR or DATE or WEEK or MONTH or YEAR.
     * Expected: {status.not-null}
     */
/*    @Test
	public void statusNotNull() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setStatus(null);
		assertEquals("{status.not-null}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {status.pattern}
     */
 /*   @Test
	public void statusPattern() {
    	ResidencyEligibilityResource residencyEligibilityResource =setResidencyEligibilityResource();
    	residencyEligibilityResource.setStatus("ABCDEF");
		assertEquals("{status.pattern}", TestUtils.getFieldErrorMessageKey(residencyEligibilityResource, localValidatorFactory));
	}
*/
}
