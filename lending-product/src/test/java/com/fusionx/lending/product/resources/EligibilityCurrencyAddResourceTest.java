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
 * Eligibility Currency Add Resource Test
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class EligibilityCurrencyAddResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private EligibilityCurrencyAddResource setEligibilityCurrencyAddResource() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource = new EligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setEligibilityId("70");
		eligibilityCurrencyAddResource.setCurrencyId("181");
		eligibilityCurrencyAddResource.setCurrencyName("Sri Lankan");
		eligibilityCurrencyAddResource.setStatus("ACTIVE");
		return eligibilityCurrencyAddResource;
	}
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
/*	@Test
	public void eligibilityIdNotNull() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
/*	@Test
	public void eligibilityIdPattern() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setEligibilityId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
/*	@Test
	public void currencyIdNotNull() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * Must be a numeric value.
     * Expected: {common-numeric.pattern}
     */
/*	@Test
	public void currencyIdPattern() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setCurrencyId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * Can not be null.
     * Expected: {common.not-null}
     */
/*	@Test
	public void currencyNameNotNull() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setCurrencyName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
/*	@Test
	public void statusNotNull() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	}
	
	
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*	@Test
	public void statusPattern() {
		EligibilityCurrencyAddResource eligibilityCurrencyAddResource =setEligibilityCurrencyAddResource();
		eligibilityCurrencyAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(eligibilityCurrencyAddResource, localValidatorFactory));
	} */
}
