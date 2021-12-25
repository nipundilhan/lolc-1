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
 * 	Business Income Expense Type Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessIncomeExpenseTypeResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessIncomeExpenseTypeResource setBusinessIncomeExpenseTypeResource() {
		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = new BusinessIncomeExpenseTypeResource();
		businessIncomeExpenseTypeResource.setBusinessIncomeTypeId("1");
		businessIncomeExpenseTypeResource.setBusinessExpenseTypeId("1");
		businessIncomeExpenseTypeResource.setAmount("100.00");
		businessIncomeExpenseTypeResource.setDescription("Test");
		businessIncomeExpenseTypeResource.setFrequencyId("1");
		businessIncomeExpenseTypeResource.setFrequencyName("Test");
		businessIncomeExpenseTypeResource.setCurrencyId("1");
		businessIncomeExpenseTypeResource.setCurrencyName("Test");
		businessIncomeExpenseTypeResource.setStatus("ACTIVE");
		businessIncomeExpenseTypeResource.setVersion("1");
		return businessIncomeExpenseTypeResource;
	}
	
    /**
     * businessIncomeTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessIncomeTypeIdPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setBusinessIncomeTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
    
    /**
     * businessExpenseTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessExpenseTypeIdPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setBusinessExpenseTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
    
    /**
   	 * amount is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void amountNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setAmount(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
   	
   	/**
     * amount Pattern is "^$|\\d{1,20}\\.\\d{1,2}$".
     * Expected: {common-amount.pattern}
     */
    @Test
	public void amountPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setAmount("A");
		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
    
    /**
   	 * description is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void descriptionNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setDescription(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
   	
   	/**
   	 * frequencyId is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void frequencyIdNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setFrequencyId(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
	
   	/**
     * frequencyId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void frequencyIdPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setFrequencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
    
    /**
   	 * frequencyName is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void frequencyNameNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setFrequencyName(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
   	
   	/**
   	 * currencyId is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void currencyIdNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setCurrencyId(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
	
   	/**
     * currencyId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void currencyIdPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setCurrencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
    
    /**
   	 * currencyName is required.
   	 * Expected: {common.not-null}
   	 */
   	@Test
   	public void currencyNameNotNull() {
   		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
   		businessIncomeExpenseTypeResource.setCurrencyName(null);
   		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
   	}
   	
   	/**
	 * status is required.
	 * Expected: {common.not-null}
	 */
	@Test
	public void statusNotNull() {
		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
		businessIncomeExpenseTypeResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
	
	/**
	 * Status should be ACTIVE or INACTIVE.
	 * Expected: {common-status.pattern}
	 */
	@Test
	public void statusPattern() {
		BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
		businessIncomeExpenseTypeResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}
	
 	/**
     * version Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource = setBusinessIncomeExpenseTypeResource();
    	businessIncomeExpenseTypeResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpenseTypeResource, localValidatorFactory));
	}

}
