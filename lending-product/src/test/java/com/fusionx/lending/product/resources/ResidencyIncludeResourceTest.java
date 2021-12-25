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
public class ResidencyIncludeResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ResidencyIncludeResource setResidencyIncludeResource() {
		ResidencyIncludeResource residencyIncludeResource = new ResidencyIncludeResource();
		residencyIncludeResource.setCountryId("10");
		residencyIncludeResource.setCountryName("Sri Lanka");
		residencyIncludeResource.setStatus("ACTIVE");
		return residencyIncludeResource;
	}
	
	/**
     * Country id  is required.
     * Expected: {residencyIncludeCountryId.not-null}
     */
    @Test
	public void residencyIncludeCountryIdNotNull() {
    	ResidencyIncludeResource residencyIncludeResource =setResidencyIncludeResource();
    	residencyIncludeResource.setCountryId(null);
		assertEquals("{residencyIncludeCountryId.not-null}", TestUtils.getFieldErrorMessageKey(residencyIncludeResource, localValidatorFactory));
	}
    
	/**
     * Invalid value.
     * Expected: {common.invalid-value}
     */
    @Test
	public void setResidencyIncludeCountryIdInvalidValue() {
    	ResidencyIncludeResource residencyIncludeResource =setResidencyIncludeResource();
    	residencyIncludeResource.setCountryId("ABCDEF");
		assertEquals("{common.invalid-value}", TestUtils.getFieldErrorMessageKey(residencyIncludeResource, localValidatorFactory));
	}
    
    /**
     * Country name  is required.
     * Expected: {countryName.not-null}
     */
    @Test
	public void countryNameNotNull() {
    	ResidencyIncludeResource residencyIncludeResource =setResidencyIncludeResource();
    	residencyIncludeResource.setCountryName(null);
		assertEquals("{countryName.not-null}", TestUtils.getFieldErrorMessageKey(residencyIncludeResource, localValidatorFactory));
	}
	
	/**
     * Type should be MATURITY or HOUR or DATE or WEEK or MONTH or YEAR.
     * Expected: {status.not-null}
     */
    @Test
	public void statusNotNull() {
    	ResidencyIncludeResource residencyIncludeResource =setResidencyIncludeResource();
    	residencyIncludeResource.setStatus(null);
		assertEquals("{status.not-null}", TestUtils.getFieldErrorMessageKey(residencyIncludeResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {status.pattern}
     */
    @Test
	public void statusPattern() {
    	ResidencyIncludeResource residencyIncludeResource =setResidencyIncludeResource();
    	residencyIncludeResource.setStatus("ABCDEF");
		assertEquals("{status.pattern}", TestUtils.getFieldErrorMessageKey(residencyIncludeResource, localValidatorFactory));
	}

}
