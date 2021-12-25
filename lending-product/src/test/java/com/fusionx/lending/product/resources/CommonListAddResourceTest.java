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
 * Common list Add Resource Test
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
public class CommonListAddResourceTest {
/*
	private LocalValidatorFactoryBean localValidatorFactory;
/*	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
*/	
/*	private CommonListAddResource setCommonListAddResource() {
		
		CommonListAddResource commonListAddResource = new CommonListAddResource();
//	commonListAddResource.setCode("TEST");
//		commonListAddResource.setReferenceCode("TESTREFCODE");
//		commonListAddResource.setName("Test");
//		commonListAddResource.setStatus("ACTIVE");
//		commonListAddResource.setAttribute1("Attribute1");
//		commonListAddResource.setAttribute2("Attribute2");
//		commonListAddResource.setAttribute3("Attribute3");
//		commonListAddResource.setAttribute4("Attribute4");
//		commonListAddResource.setAttribute5("Attribute5");
//		return commonListAddResource;
		
	}
*/
	/**
     * Code Cannot be blank.
     * Expected: {common.not-null}
     */
/*	@Test
    public void codeNotNull() {
		CommonListAddResource commonListAddResource = setCommonListAddResource();
		commonListAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListAddResource, localValidatorFactory));
    }
	
	/**
     * Code Invalid value.
     * Expected: {common.invalid-value}
     */
/*    @Test
    public void codeSize() {
    	CommonListAddResource commonListAddResource = setCommonListAddResource();
    	commonListAddResource.setCode("WWWWW");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(commonListAddResource, localValidatorFactory));
    }
    
    /**
     * Code should consists of alphanumeric characters only.
     * Expected: {common.code-pattern}
     */
/*    @Test
    public void codePattern() {
    	CommonListAddResource commonListAddResource = setCommonListAddResource();
    	commonListAddResource.setCode("!@#$");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(commonListAddResource, localValidatorFactory));
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
 /*   @Test
    public void statusNotNull() {
    	CommonListAddResource commonListAddResource = setCommonListAddResource();
    	commonListAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(commonListAddResource, localValidatorFactory));
		
    }
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
    public void statusPattern() {
    	CommonListAddResource commonListAddResource = setCommonListAddResource();
    	commonListAddResource.setStatus("ABCD");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(commonListAddResource, localValidatorFactory));
    }
 */   
}
