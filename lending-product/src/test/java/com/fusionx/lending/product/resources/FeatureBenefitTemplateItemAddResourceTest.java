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
public class FeatureBenefitTemplateItemAddResourceTest {

	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	private FeatureBenefitTemplateItemAddResource setFeatureBenefitTemplateItemAddResource() {
		FeatureBenefitTemplateItemAddResource  featureBenefitTemplateItemAddResource = new  FeatureBenefitTemplateItemAddResource();
		
		FeatureBenefitItemUsageResource featureBenefitItemUsageResource = new FeatureBenefitItemUsageResource();
		featureBenefitItemUsageResource.setFeatureBenefitItemId("1");
		featureBenefitItemUsageResource.setStatus("ACTIVE");			
		
		featureBenefitTemplateItemAddResource.setFeatureBenefitTemplateId("1");
		featureBenefitTemplateItemAddResource.setFeatureBenefitItemUsageResource(featureBenefitItemUsageResource);
			
		return featureBenefitTemplateItemAddResource;
	}
	
	
	/**
     * feature benifit template id  is required.
     * Expected: {common.not-null}
     */
    @Test
	public void featureBenefitTemplateIdNotNull() {
    	FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource = setFeatureBenefitTemplateItemAddResource();
    	featureBenefitTemplateItemAddResource.setFeatureBenefitTemplateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(featureBenefitTemplateItemAddResource, localValidatorFactory));
	}
    
	/**
     * feature benifit template id should be numeric.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void  featureBenefitTemplateIdPattern() {
    	FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource = setFeatureBenefitTemplateItemAddResource();
    	featureBenefitTemplateItemAddResource.setFeatureBenefitTemplateId("asd");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitTemplateItemAddResource, localValidatorFactory));
	}
}
