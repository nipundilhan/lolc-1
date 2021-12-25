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
public class AgeEligibilityUpdateResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private AgeEligibilityUpdateResource setAgeEligibilityUpdateResource() {
		AgeEligibilityUpdateResource ageEligibilityUpdateResource = new AgeEligibilityUpdateResource();
		ageEligibilityUpdateResource.setMinimumAge("10");
		ageEligibilityUpdateResource.setMaximumAge("20");
		ageEligibilityUpdateResource.setStatus("ACTIVE");
		ageEligibilityUpdateResource.setVersion("1");
		return ageEligibilityUpdateResource;
	}
	
 
    
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
    @Test
	public void versionNotNull() {
    	AgeEligibilityUpdateResource ageEligibilityUpdateResource = setAgeEligibilityUpdateResource();
    	ageEligibilityUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(ageEligibilityUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
    @Test
	public void versionPattern() {
    	AgeEligibilityUpdateResource ageEligibilityUpdateResource = setAgeEligibilityUpdateResource();
    	ageEligibilityUpdateResource.setVersion("ABCDE");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(ageEligibilityUpdateResource, localValidatorFactory));
	}

}
