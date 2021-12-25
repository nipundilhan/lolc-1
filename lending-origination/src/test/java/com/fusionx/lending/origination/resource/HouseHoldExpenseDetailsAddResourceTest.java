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
 * 	House Hold Expense Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-09-2021                               Dilhan       Created
 *    
 ********************************************************************************************************
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HouseHoldExpenseDetailsAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	private HouseHoldExpenseDetailsAddResource setHouseHoldExpenseDetailsAddResource() {
		HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource = new HouseHoldExpenseDetailsAddResource();
		houseHoldExpenseDetailsAddResource.setIncomeSourceDetailsId("1");
		return houseHoldExpenseDetailsAddResource;
	}
	
	 /**
     * incomeSourceDetailsId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void incomeSourceDetailsIdNotNull() {
    	HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource = setHouseHoldExpenseDetailsAddResource();
    	houseHoldExpenseDetailsAddResource.setIncomeSourceDetailsId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * incomeSourceDetailsId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void incomeSourceDetailsIdPattern() {
    	HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource = setHouseHoldExpenseDetailsAddResource();
    	houseHoldExpenseDetailsAddResource.setIncomeSourceDetailsId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseDetailsAddResource, localValidatorFactory));
	}

}
