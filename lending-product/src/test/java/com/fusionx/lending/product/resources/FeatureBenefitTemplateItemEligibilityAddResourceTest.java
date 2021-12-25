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
public class FeatureBenefitTemplateItemEligibilityAddResourceTest {

	/*private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	
	private FeatureBenefitTemplateItemEligibilityAddResource setFeatureBenefitTemplateItemEligibilityAddResource() {
		FeatureBenefitTemplateItemEligibilityAddResource  featureBenefitTemplateItemEligibilityAddResource = new  FeatureBenefitTemplateItemEligibilityAddResource();
		
		FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = new FeatureBenefitEligibilityUsageResource();
		featureBenefitEligibilityUsageResource.setFeatureBenefitEligibilityId("1");
		featureBenefitEligibilityUsageResource.setStatus("ACTIVE");				
		
		featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitTemplateId("1");
		featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitTemplateItemId("1");
		featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitEligibilityUsageResource(featureBenefitEligibilityUsageResource);
		return featureBenefitTemplateItemEligibilityAddResource;
	}
	
	

    
	/**
     * feature benifit template id should be numeric.
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void  featureBenefitTemplateIdPattern() {
    	FeatureBenefitTemplateItemEligibilityAddResource  featureBenefitTemplateItemEligibilityAddResource = setFeatureBenefitTemplateItemEligibilityAddResource();
    	featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitTemplateId("asd");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitTemplateItemEligibilityAddResource, localValidatorFactory));
	}
    

    
	/**
     * feature benifit template item id should be numeric.
     * Expected: {common-numeric.pattern}
     */
/*    @Test
	public void  featureBenefitTemplateItemIdPattern() {
    	FeatureBenefitTemplateItemEligibilityAddResource  featureBenefitTemplateItemEligibilityAddResource = setFeatureBenefitTemplateItemEligibilityAddResource();
    	featureBenefitTemplateItemEligibilityAddResource.setFeatureBenefitTemplateItemId("asd");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(featureBenefitTemplateItemEligibilityAddResource, localValidatorFactory));
	}
    */
}
