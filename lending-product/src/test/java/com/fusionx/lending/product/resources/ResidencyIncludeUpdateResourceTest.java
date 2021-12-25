package com.fusionx.lending.product.resources;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
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
public class ResidencyIncludeUpdateResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ResidencyIncludeUpdateResource setResidencyIncludeUpdateResource() {
		ResidencyIncludeUpdateResource residencyIncludeUpdateResource = new ResidencyIncludeUpdateResource();
		residencyIncludeUpdateResource.setCountryId("10");
		residencyIncludeUpdateResource.setCountryName("Sri Lanka");
		residencyIncludeUpdateResource.setStatus("ACTIVE");
		residencyIncludeUpdateResource.setVersion("1");
		return residencyIncludeUpdateResource;
	}
	
    
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
    @Test
	public void versionNotNull() {
    	ResidencyIncludeUpdateResource residencyIncludeUpdateResource =setResidencyIncludeUpdateResource();
    	residencyIncludeUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(residencyIncludeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
    @Test
	public void versionPattern() {
    	ResidencyIncludeUpdateResource residencyIncludeUpdateResource =setResidencyIncludeUpdateResource();
    	residencyIncludeUpdateResource.setVersion("ABCDE");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(residencyIncludeUpdateResource, localValidatorFactory));
	}

}
