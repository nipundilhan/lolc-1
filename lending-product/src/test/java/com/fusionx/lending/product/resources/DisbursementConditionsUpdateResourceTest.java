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
 * Disbursement Conditions Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-09-2021                  FXL-788   Dilhan       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisbursementConditionsUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private DisbursementConditionsUpdateResource setDisbursementConditionsUpdateResource() {
		
		DisbursementConditionsUpdateResource disbursementConditionsUpdateResource = new DisbursementConditionsUpdateResource();
		disbursementConditionsUpdateResource.setCode("DIS1");
		disbursementConditionsUpdateResource.setName("Test");
		disbursementConditionsUpdateResource.setDescription("Test");
		disbursementConditionsUpdateResource.setStatus("ACTIVE");
		disbursementConditionsUpdateResource.setConditionType("PERIOD");
		disbursementConditionsUpdateResource.setPeriodId("1");
		disbursementConditionsUpdateResource.setPeriodName("Bi Weekly");
		disbursementConditionsUpdateResource.setAmount("5000.00");
		disbursementConditionsUpdateResource.setPeriodNumber("2");
		disbursementConditionsUpdateResource.setIndicatorFlag("YES");
		disbursementConditionsUpdateResource.setTexual("wwwwwwww");
		disbursementConditionsUpdateResource.setVersion("1");
		return disbursementConditionsUpdateResource;
	}
	
	 /**
     * version is required.
     * Expected: {version.not-null}
     */
    @Test
	public void versionNotNull() {
    	DisbursementConditionsUpdateResource disbursementConditionsUpdateResource = setDisbursementConditionsUpdateResource();
    	disbursementConditionsUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
    @Test
	public void versionPattern() {
    	DisbursementConditionsUpdateResource disbursementConditionsUpdateResource = setDisbursementConditionsUpdateResource();
    	disbursementConditionsUpdateResource.setVersion("A");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsUpdateResource, localValidatorFactory));
	}

}

