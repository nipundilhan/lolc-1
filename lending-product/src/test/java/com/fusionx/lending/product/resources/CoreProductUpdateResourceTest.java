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
 * Core Product Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021                  FXL-795   Dilhan       Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CoreProductUpdateResourceTest {
/*
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CoreProductUpdateResource setCoreProductUpdateResource() {
		
		CoreProductUpdateResource coreProductUpdateResource = new CoreProductUpdateResource();
		coreProductUpdateResource.setCode("DIS1");
		coreProductUpdateResource.setDescription("Test");
		coreProductUpdateResource.setComment("Test1233");
		coreProductUpdateResource.setEarlyPaymentApplicable("YES");
		coreProductUpdateResource.setOverPaymentApplicable("NO");
		coreProductUpdateResource.setOverPaymentAllow("YES");
		coreProductUpdateResource.setFullPartialRepaymentAllow("NO");
		coreProductUpdateResource.setSalesAccessChannelId("1");
		coreProductUpdateResource.setSalesAccessChannelName("Test");
		coreProductUpdateResource.setServiceAccessChannelId("1");
		coreProductUpdateResource.setServiceAccessChannelName("Test");
		coreProductUpdateResource.setFeeTypeId("1");
		coreProductUpdateResource.setFeeTypeName("Test");
		coreProductUpdateResource.setCurrencyId("722");
		coreProductUpdateResource.setCurrencyName("Sri Lanka Rupees");
		coreProductUpdateResource.setStatus("ACTIVE");
		coreProductUpdateResource.setVersion("1");
		return coreProductUpdateResource;
	}
	
	 /**
     * version is required.
     * Expected: {version.not-null}
     */
 /*   @Test
	public void versionNotNull() {
    	CoreProductUpdateResource coreProductUpdateResource = setCoreProductUpdateResource();
    	coreProductUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(coreProductUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	CoreProductUpdateResource coreProductUpdateResource = setCoreProductUpdateResource();
    	coreProductUpdateResource.setVersion("A");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(coreProductUpdateResource, localValidatorFactory));
	}
*/
}

