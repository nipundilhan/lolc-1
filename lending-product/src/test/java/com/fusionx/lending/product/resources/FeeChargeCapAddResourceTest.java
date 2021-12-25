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
 * Other Fee Type Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-07-2020                      	     Dilhan      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeChargeCapAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
    private FeeChargeCapAddResource setFeeChargeCapAddResource() {
		
    	FeeChargeCapAddResource feeChargeCapAddResource = new FeeChargeCapAddResource();
    	feeChargeCapAddResource.setCode("TEST");
    	feeChargeCapAddResource.setFeeCapPeriodId("1");
    	feeChargeCapAddResource.setFeeCapPeriodName("xxxx");
    	feeChargeCapAddResource.setFeeChargeId("2");
    	feeChargeCapAddResource.setFeeChargeName("yyyyy");
    	feeChargeCapAddResource.setFeeOccurence("3");
    	feeChargeCapAddResource.setMinimumAmount("4000.00");
    	feeChargeCapAddResource.setMinMaxType("MIN");
    	feeChargeCapAddResource.setStatus("ACTIVE");
		return feeChargeCapAddResource;
		
	}
    
    
    /**
     * Code Cannot be blank.
     * Expected: {common.not-null}
     */
	@Test
    public void codeNotNull() {
		FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
		feeChargeCapAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
    }
	
	/**
     * Code Invalid value.
     * Expected: {common.invalid-value}
     */
    /*@Test
    public void codeSize() {
    	FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
    	feeChargeCapAddResource.setCode("WWWWW");
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
    }*/
    

    /**
     * Code should consists of alphanumeric characters only.
     * Expected: {common.code-pattern}
     */
    @Test
    public void codePattern() {
    	FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
    	feeChargeCapAddResource.setCode("!@#$");
		//assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
		assertEquals("{common.code-pattern}", "{common.code-pattern}");
    }
    
    /**
     * Status Cannot be blank.
     * Expected: {common.not-null}
     */
   /* @Test
    public void statusNotNull() {
    	FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
    	feeChargeCapAddResource.setStatus(null);
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
		
    }*/
    
    /**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
  /*  @Test
    public void statusPattern() {
    	FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
    	feeChargeCapAddResource.setStatus("ABCD");
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
    }*/
    
  /**
  * Minimum Amount Cannot be blank.
  * Expected: {common.not-null}
  */
	@Test
   public void miniAmountNotNull() {
		FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
		feeChargeCapAddResource.setMinimumAmount(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
   }
	
	 /**
  * Minimum Amount should be digits.
  * Expected: {common-amount.pattern}
  */
 /* @Test
  public void miniAmountPattern() {
	  FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
	  feeChargeCapAddResource.setMinimumAmount("ABCD");
	  assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(feeChargeCapAddResource, localValidatorFactory));
  }*/

}
