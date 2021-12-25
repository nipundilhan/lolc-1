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
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeExpensesAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	private OtherIncomeExpensesAddResource setOtherIncomeExpensesAddResource() {
		OtherIncomeExpensesAddResource otherIncomeExpensesAddResource = new OtherIncomeExpensesAddResource();
		otherIncomeExpensesAddResource.setOtherIncomeDetailsId("1");
		return otherIncomeExpensesAddResource;
	}

	/**
	 * otherIncomeDetailId is required. Expected: {common.not-null}
	 */
	@Test
	public void otherIncomeDetailIdNotNull() {
		OtherIncomeExpensesAddResource otherIncomeExpensesAddResource = setOtherIncomeExpensesAddResource();
		otherIncomeExpensesAddResource.setOtherIncomeDetailsId(null);
		assertEquals("{common.not-null}",
				TestUtils.getFieldErrorMessageKey(otherIncomeExpensesAddResource, localValidatorFactory));
	}

	/**
	 * otherIncomeDetailId Pattern is "^$|[0-9]+". Expected:
	 * {common-numeric.pattern}
	 */
	@Test
	public void otherIncomeDetailIdPattern() {
		OtherIncomeExpensesAddResource otherIncomeExpensesAddResource = setOtherIncomeExpensesAddResource();
		otherIncomeExpensesAddResource.setOtherIncomeDetailsId("A");
		assertEquals("{common-numeric.pattern}",
				TestUtils.getFieldErrorMessageKey(otherIncomeExpensesAddResource, localValidatorFactory));
	}

}
