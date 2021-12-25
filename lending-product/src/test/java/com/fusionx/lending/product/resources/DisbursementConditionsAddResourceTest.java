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
 * Disbursement Conditions Add Resource Test
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
public class DisbursementConditionsAddResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private DisbursementConditionsAddResource setDisbursementConditionsAddResource() {
		DisbursementConditionsAddResource disbursementConditionsAddResource = new DisbursementConditionsAddResource();
		disbursementConditionsAddResource.setCode("DIS1");
		disbursementConditionsAddResource.setName("Test");
		disbursementConditionsAddResource.setDescription("Test");
		disbursementConditionsAddResource.setStatus("ACTIVE");
		disbursementConditionsAddResource.setConditionType("PERIOD");
		disbursementConditionsAddResource.setPeriodId("1");
		disbursementConditionsAddResource.setPeriodName("Bi Weekly");
		disbursementConditionsAddResource.setAmount("5000.00");
		disbursementConditionsAddResource.setPeriodNumber("2");
		disbursementConditionsAddResource.setIndicatorFlag("YES");
		disbursementConditionsAddResource.setTexual("wwwwwwww");
		return disbursementConditionsAddResource;
	        
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-length01.size}
     */
    @Test
    public void descriptionSize() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-length01.size}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
		
    }
    
    /**
     * conditionType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void conditionTypeNotNull() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setConditionType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
	/**
     * conditionType should be PERIOD or AMOUNT or INDICATOR or TEXUAL.
     * Expected: {condition.type.pattern}
     */
    @Test
	public void conditionTypePattern() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setConditionType("ABCDEF");
		assertEquals("{condition.type.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * indicatorFlag is required.
     * Expected: {common.not-null}
     */
    @Test
	public void indicatorFlagNotNull() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setIndicatorFlag(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
	/**
     * indicatorFlag should be YES or NO.
     * Expected: {common-enable.pattern}
     */
    @Test
	public void indicatorFlagPattern() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setIndicatorFlag("ABCDEF");
		assertEquals("{common-enable.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
	}
    
    /**
     * texual cannot be greater than 350 characters.
     * Expected: {common-length01.size}
     */
    @Test
    public void texualSize() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setTexual("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-length01.size}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
		
    }
    
    /**
     * amount should be digits.
     * Expected: {common-amount.pattern}
     */
     @Test
     public void amountPattern() {
       DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
       disbursementConditionsAddResource.setAmount("ABCD");
  	   assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
     }
    
     /**
      * frequencyId should be in "^$|[0-9]+" pattern.
      * Expected: {common-numeric.pattern}
      */
     @Test
 	 public void periodIdPattern() {
    	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
    	disbursementConditionsAddResource.setPeriodId("A");
 		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
 	}
     
     /**
      * periodName cannot be greater than 70 characters.
      * Expected: {common-name.size}
      */
     @Test
     public void periodNameSize() {
     	DisbursementConditionsAddResource disbursementConditionsAddResource = setDisbursementConditionsAddResource();
     	disbursementConditionsAddResource.setPeriodName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
 		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(disbursementConditionsAddResource, localValidatorFactory));
 		
     }

}
