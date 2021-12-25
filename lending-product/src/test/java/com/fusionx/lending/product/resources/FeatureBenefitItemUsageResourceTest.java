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

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeatureBenefitItemUsageResourceTest {
/*
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private FeatureBenefitItemUsageResource setFeatureBenefitItemUsageResource() {
		FeatureBenefitItemUsageResource featureBenefitItemUsageResource = new FeatureBenefitItemUsageResource();
		featureBenefitItemUsageResource.setFeatureBenefitItemId("1");
		featureBenefitItemUsageResource.setStatus("ACTIVE");		
		return featureBenefitItemUsageResource;
	}
	
	/**
     * feature benifit item id  is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void featureBenefitItemIdNotNull() {
		FeatureBenefitItemUsageResource featureBenefitItemUsageResource = new FeatureBenefitItemUsageResource();
		featureBenefitItemUsageResource.setFeatureBenefitItemId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(featureBenefitItemUsageResource, localValidatorFactory));
	}
    
	/**
     * feature benifit item id should be numeric.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void featureBenefitItemIdPattern() {
    	FeatureBenefitItemUsageResource featureBenefitItemUsageResource = setFeatureBenefitItemUsageResource();
    	featureBenefitItemUsageResource.setFeatureBenefitItemId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitItemUsageResource, localValidatorFactory));
	}
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	FeatureBenefitItemUsageResource featureBenefitItemUsageResource = setFeatureBenefitItemUsageResource();
    	featureBenefitItemUsageResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(featureBenefitItemUsageResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
  /*  @Test
	public void statusPattern() {
    	FeatureBenefitItemUsageResource featureBenefitItemUsageResource = setFeatureBenefitItemUsageResource();
    	featureBenefitItemUsageResource.setStatus("ABC");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitItemUsageResource, localValidatorFactory));
	}*/
}
