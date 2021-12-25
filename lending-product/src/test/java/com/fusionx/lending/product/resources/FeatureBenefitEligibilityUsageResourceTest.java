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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeatureBenefitEligibilityUsageResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private FeatureBenefitEligibilityUsageResource setFeatureBenefitEligibilityUsageResource() {
		FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = new FeatureBenefitEligibilityUsageResource();
		featureBenefitEligibilityUsageResource.setFeatureBenefitEligibilityId("1");
		featureBenefitEligibilityUsageResource.setStatus("ACTIVE");		
		return featureBenefitEligibilityUsageResource;
	}
	
	/**
     * feature benifit Eligibility id  is required.
     * Expected: {common.not-null}
     */
    @Test
	public void featureBenefitEligibilityIdNotNull() {
    	FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = setFeatureBenefitEligibilityUsageResource();
    	featureBenefitEligibilityUsageResource.setFeatureBenefitEligibilityId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(featureBenefitEligibilityUsageResource, localValidatorFactory));
	}
    
	/**
     * feature benifit Eligibility id should be numeric.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void featureBenefitEligibilityIdPattern() {
    	FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = setFeatureBenefitEligibilityUsageResource();
    	featureBenefitEligibilityUsageResource.setFeatureBenefitEligibilityId("abc");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitEligibilityUsageResource, localValidatorFactory));
	}
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = setFeatureBenefitEligibilityUsageResource();
    	featureBenefitEligibilityUsageResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(featureBenefitEligibilityUsageResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = setFeatureBenefitEligibilityUsageResource();
    	featureBenefitEligibilityUsageResource.setStatus("ABC");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitEligibilityUsageResource, localValidatorFactory));
	}
}
