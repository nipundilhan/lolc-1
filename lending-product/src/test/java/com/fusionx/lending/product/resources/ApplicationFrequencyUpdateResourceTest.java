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
 * Application Frequency Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-69   		 FX-6511	Senitha      Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class ApplicationFrequencyUpdateResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ApplicationFrequencyUpdateResource setApplicationFrequencyUpdateResource() {
		
		ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource = new ApplicationFrequencyUpdateResource();
		applicationFrequencyUpdateResource.setCode("TEST");
		applicationFrequencyUpdateResource.setName("Test");
		applicationFrequencyUpdateResource.setDescription("TEST");
		applicationFrequencyUpdateResource.setStatus("ACTIVE");
		applicationFrequencyUpdateResource.setFrequencyTypeId("1");
		applicationFrequencyUpdateResource.setUnit("2");
		applicationFrequencyUpdateResource.setVersion("0");
		return applicationFrequencyUpdateResource;
		
	}
	
	/**
     * Version is required.
     * Expected: {version.not-null}
     */
  /*  @Test
	public void versionNotNull() {
    	ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource = setApplicationFrequencyUpdateResource();
    	applicationFrequencyUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(applicationFrequencyUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
  /*  @Test
	public void versionPattern() {
    	ApplicationFrequencyUpdateResource applicationFrequencyUpdateResource = setApplicationFrequencyUpdateResource();
    	applicationFrequencyUpdateResource.setVersion("ABCDE");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(applicationFrequencyUpdateResource, localValidatorFactory));
	}*/
}
