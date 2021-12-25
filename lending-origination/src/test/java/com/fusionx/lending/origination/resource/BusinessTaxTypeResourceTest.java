package com.fusionx.lending.origination.resource;

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

import com.fusionx.lending.origination.utill.TestUtils;

/**
 * 	Business Tax Type Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessTaxTypeResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessTaxTypeResource setBusinessTaxTypeResource() {
		BusinessTaxTypeResource businessTaxTypeResource = new BusinessTaxTypeResource();
		businessTaxTypeResource.setTaxCodeId("1");
		businessTaxTypeResource.setTaxCodeName("Test");
		businessTaxTypeResource.setApplicableOn("Test");
		businessTaxTypeResource.setFrequencyId("1");
		businessTaxTypeResource.setFrequencyName("Test");
		businessTaxTypeResource.setRate("32.55");
		//businessTaxTypeResource.setRate("12");
		businessTaxTypeResource.setAmount("100.00");
		businessTaxTypeResource.setCurrencyId("1");
		businessTaxTypeResource.setCurrencyName("Test");
		businessTaxTypeResource.setStatus("ACTIVE");
		businessTaxTypeResource.setVersion("1");
		return businessTaxTypeResource;
	}
	
	 /**
   	 * taxCodeId is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void taxCodeIdNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setTaxCodeId(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
	
    /**
     * taxCodeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    //@Test
	public void taxCodeIdPattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setTaxCodeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
    
    /**
   	 * taxCodeName is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void taxCodeNameNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setTaxCodeName(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
    /**
   	 * applicableOn is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void applicableOnNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setApplicableOn(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
   	/**
   	 * frequencyId is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void frequencyIdNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setFrequencyId(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
	
   	/**
     * frequencyId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    //@Test
	public void frequencyIdPattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setFrequencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
    
    /**
   	 * frequencyName is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void frequencyNameNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setFrequencyName(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
    /**
   	 * rate is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void rateNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setRate(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
   	/**
     * rate Pattern is "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$".
     * Expected: {rate.pattern}
     */
    //@Test
	public void ratePattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setRate("A");
		assertEquals("{rate.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
    /**
   	 * amount is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void amountNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setAmount(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
   	/**
     * amount Pattern is "^$|\\d{1,20}\\.\\d{1,2}$".
     * Expected: {common-amount.pattern}
     */
    //@Test
	public void amountPattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setAmount("A");
		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
   	
   	/**
   	 * currencyId is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void currencyIdNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setCurrencyId(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
	
   	/**
     * currencyId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    //@Test
	public void currencyIdPattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setCurrencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
    
    /**
   	 * currencyName is required.
   	 * Expected: {common.not-null}
   	 */
   	//@Test
   	public void currencyNameNotNull() {
   		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
   		businessTaxTypeResource.setCurrencyName(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
   	}
   	
   	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	//@Test
	public void statusNotNull() {
		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
		businessTaxTypeResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	//@Test
	public void statusPattern() {
		BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
		businessTaxTypeResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}
	
 	/**
     * version Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    //@Test
	public void versionPattern() {
    	BusinessTaxTypeResource businessTaxTypeResource = setBusinessTaxTypeResource();
    	businessTaxTypeResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessTaxTypeResource, localValidatorFactory));
	}

}
