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
 * Application Frequency Add Resource
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
public class ApplicationFrequencyAddResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ApplicationFrequencyAddResource setApplicationFrequencyAddResource() {
		
		ApplicationFrequencyAddResource applicationFrequencyAddResource = new ApplicationFrequencyAddResource();
		applicationFrequencyAddResource.setCode("TEST");
		applicationFrequencyAddResource.setName("Test");
		applicationFrequencyAddResource.setDescription("TEST");
		applicationFrequencyAddResource.setStatus("ACTIVE");
		applicationFrequencyAddResource.setFrequencyTypeId("1");
		applicationFrequencyAddResource.setUnit("2");
		return applicationFrequencyAddResource;
		
	}

	/**
     * Code Cannot be blank.
     * Expected: {common.not-null}
     */
/*	@Test
    public void codeNotNull() {
		ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
		applicationFrequencyAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
    }
	
	/**
     * Code Invalid value.
     * Expected: {common.invalid-value}
     */
/*    @Test
    public void codeSize() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setCode("WWWWW");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
    }
    
    /**
     * Code should consists of alphanumeric characters only.
     * Expected: {common.code-pattern}
     */
 /*   @Test
    public void codePattern() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setCode("!@#$");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
/*    @Test
    public void statusNotNull() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
    public void statusPattern() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
    }
    
    /**
     * Must be an numeric value.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
    public void frequencyTypeIdPattern() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setFrequencyTypeId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
		
    }
    
    /**
     * Must be an numeric value.
     * Expected: {common-numeric.pattern}
     */
/*    @Test
    public void unitPattern() {
    	ApplicationFrequencyAddResource applicationFrequencyAddResource = setApplicationFrequencyAddResource();
    	applicationFrequencyAddResource.setUnit("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(applicationFrequencyAddResource, localValidatorFactory));
		
    }*/
    
}
