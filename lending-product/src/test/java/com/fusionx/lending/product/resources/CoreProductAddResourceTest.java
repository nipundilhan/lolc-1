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
 * Core Product Add Resource Test
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
public class CoreProductAddResourceTest {
/*	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CoreProductAddResource setCoreProductAddResource() {
		CoreProductAddResource coreProductAddResource = new CoreProductAddResource();
		coreProductAddResource.setCode("DIS1");
		coreProductAddResource.setDescription("Test");
		coreProductAddResource.setComment("Test1233");
		coreProductAddResource.setEarlyPaymentApplicable("YES");
		coreProductAddResource.setOverPaymentApplicable("NO");
		coreProductAddResource.setOverPaymentAllow("YES");
		coreProductAddResource.setFullPartialRepaymentAllow("NO");
		coreProductAddResource.setSalesAccessChannelId("1");
		coreProductAddResource.setSalesAccessChannelName("Test");
		coreProductAddResource.setServiceAccessChannelId("1");
		coreProductAddResource.setServiceAccessChannelName("Test");
		coreProductAddResource.setFeeTypeId("1");
		coreProductAddResource.setFeeTypeName("Test");
		coreProductAddResource.setStatus("ACTIVE");
		coreProductAddResource.setCurrencyId("722");
		coreProductAddResource.setCurrencyName("Sri Lanka Rupees");
		return coreProductAddResource;
	        
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void codeNotNull() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
  /*  @Test
	public void codeSize() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
 /*   @Test
	public void codePattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
	
    /**
     * salesAccessChannelId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void salesAccessChannelIdPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setSalesAccessChannelId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
   	 * salesAccessChannelName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
  /* 	@Test
   	public void salesAccessChannelNameSize() {
   		CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
   		coreProductAddResource.setSalesAccessChannelName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * serviceAccessChannelId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void serviceAccessChannelIdPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setServiceAccessChannelId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
   	 * serviceAccessChannelName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
 /*  	@Test
   	public void serviceAccessChannelNameSize() {
   		CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
   		coreProductAddResource.setServiceAccessChannelName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
   		
   	}

	/**
     * feeTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void feeTypeIdPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setFeeTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
   	 * feeTypeName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
  /* 	@Test
   	public void feeTypeNameSize() {
   		CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
   		coreProductAddResource.setFeeTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * earlyPaymentApplicable should be YES or NO.
     * Expected: {common-enable.pattern}
     */
 /*   @Test
	public void earlyPaymentApplicablePattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setEarlyPaymentApplicable("ABCDEF");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
	/**
     * overPaymentApplicable should be YES or NO.
     * Expected: {common-enable.pattern}
     */
/*    @Test
	public void overPaymentApplicablePattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setOverPaymentApplicable("ABCDEF");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
	/**
     * overPaymentAllow should be YES or NO.
     * Expected: {common-enable.pattern}
     */
 /*   @Test
	public void overPaymentAllowPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setOverPaymentAllow("ABCDEF");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
	/**
     * fullPartialRepaymentAllow should be YES or NO.
     * Expected: {common-enable.pattern}
     */
 /*   @Test
	public void fullPartialRepaymentAllowPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setFullPartialRepaymentAllow("ABCDEF");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
     * status is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
 /*   @Test
	public void statusPattern() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-length01.size}
     */
 /*   @Test
    public void descriptionSize() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-length01.size}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
		
    }
    
    /**
     * currencyId is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void currencyIdNotNull() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
    
    /**
     * currencyName is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void currencyNameNotNull() {
    	CoreProductAddResource coreProductAddResource = setCoreProductAddResource();
    	coreProductAddResource.setCurrencyName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(coreProductAddResource, localValidatorFactory));
	}
 */  
}
