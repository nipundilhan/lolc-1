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
public class ResidencyEligibilityUpdateResourceTest {
	
/*	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ResidencyEligibilityUpdateResource setResidencyEligibilityUpdateResource() {
		ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource = new ResidencyEligibilityUpdateResource();
		residencyEligibilityUpdateResource.setCode("test");
		residencyEligibilityUpdateResource.setResidencyTypeId("282");
		residencyEligibilityUpdateResource.setResidencyTypeName("Test");
		residencyEligibilityUpdateResource.setStatus("ACTIVE");
		residencyEligibilityUpdateResource.setVersion("1");
		return residencyEligibilityUpdateResource;
	}

    
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
 /*   @Test
	public void versionNotNull() {
    	ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource =setResidencyEligibilityUpdateResource();
    	residencyEligibilityUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(residencyEligibilityUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource =setResidencyEligibilityUpdateResource();
    	residencyEligibilityUpdateResource.setVersion("ABCDE");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(residencyEligibilityUpdateResource, localValidatorFactory));
	}
  */  
}
