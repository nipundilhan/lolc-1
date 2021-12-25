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
 * Common list Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020      		     FX-6514	Senitha      Created
 *    
 ********************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CommonListUpdateResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CommonListUpdateResource setCommonListUpdateResource() {
		
		CommonListUpdateResource commonListUpdateResource = new CommonListUpdateResource();
		commonListUpdateResource.setCode("TEST");
		commonListUpdateResource.setReferenceCode("TESTREFCODE");
		commonListUpdateResource.setName("Test");
		commonListUpdateResource.setStatus("ACTIVE");
		commonListUpdateResource.setAttribute1("Attribute1");
		commonListUpdateResource.setAttribute2("Attribute2");
		commonListUpdateResource.setAttribute3("Attribute3");
		commonListUpdateResource.setAttribute4("Attribute4");
		commonListUpdateResource.setAttribute5("Attribute5");
		commonListUpdateResource.setVersion("0");
		return commonListUpdateResource;
		
	}

	/**
     * Version is required.
     * Expected: {version.not-null}
     */
/*    @Test
	public void versionNotNull() {
    	CommonListUpdateResource commonListUpdateResource = setCommonListUpdateResource();
    	commonListUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListUpdateResource, localValidatorFactory));
	}
    
	/**
     * Invalid version.
     * Expected: {version.pattern}
     */
 /*   @Test
	public void versionPattern() {
    	CommonListUpdateResource commonListUpdateResource = setCommonListUpdateResource();
    	commonListUpdateResource.setVersion("ABCDE");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(commonListUpdateResource, localValidatorFactory));
	}
    */
}
