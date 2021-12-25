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
 * 	Business Income Expenses Add Resource Test
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
public class BusinessIncomeExpensesAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessIncomeExpensesAddResource setBusinessIncomeExpensesAddResource() {
		BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource = new BusinessIncomeExpensesAddResource();
		businessIncomeExpensesAddResource.setBusinessIncomeDetailId("1");
		return businessIncomeExpensesAddResource;
	}
	
	 /**
     * businessIncomeDetailId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessIncomeDetailIdNotNull() {
    	BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource = setBusinessIncomeExpensesAddResource();
    	businessIncomeExpensesAddResource.setBusinessIncomeDetailId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessIncomeExpensesAddResource, localValidatorFactory));
	}
    
    /**
     * businessIncomeDetailId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessIncomeDetailIdPattern() {
    	BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource = setBusinessIncomeExpensesAddResource();
    	businessIncomeExpensesAddResource.setBusinessIncomeDetailId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessIncomeExpensesAddResource, localValidatorFactory));
	}
	

}
