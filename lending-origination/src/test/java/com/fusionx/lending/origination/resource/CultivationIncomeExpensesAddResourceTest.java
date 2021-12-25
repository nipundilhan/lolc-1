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
 * 	Cultivation Income Expenses Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021  	             FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultivationIncomeExpensesAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CultivationIncomeExpensesAddResource setCultivationIncomeExpensesAddResource() {
		CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = new CultivationIncomeExpensesAddResource();
		cultivationIncomeExpensesAddResource.setCultivationIncomeDetailsId("1");
		cultivationIncomeExpensesAddResource.setFrequencyCode("WEEKLY");
		cultivationIncomeExpensesAddResource.setFrequencyId("1");
		cultivationIncomeExpensesAddResource.setCurrencyId("1");
		cultivationIncomeExpensesAddResource.setCurrencyName("Sri Lanka Rupe");
		return cultivationIncomeExpensesAddResource;
	}
	
	 /**
     * cultivationIncomeDetailsId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void cultivationIncomeDetailsIdNotNull() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setCultivationIncomeDetailsId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
    
    /**
     * cultivationIncomeDetailsId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void cultivationIncomeDetailsIdPattern() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setCultivationIncomeDetailsId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
    /**
     * frequencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void frequencyIdNotNull() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setFrequencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
	
    /**
     * frequencyId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void frequencyIdPattern() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setFrequencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
    
    /**
     * frequencyCode is required.
     * Expected: {code.not-null}
     */
    @Test
	public void frequencyCodeNotNull() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setFrequencyCode(null);
		assertEquals("{code.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}

	/**
     * currencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyIdNotNull() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
    
    /**
     * currencyName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyNameNotNull() {
    	CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource = setCultivationIncomeExpensesAddResource();
    	cultivationIncomeExpensesAddResource.setCurrencyName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesAddResource, localValidatorFactory));
	}
    
}
