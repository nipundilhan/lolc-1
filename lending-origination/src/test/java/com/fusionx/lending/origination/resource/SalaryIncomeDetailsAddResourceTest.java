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
 * 	Salary Income Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryIncomeDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryIncomeDetailsAddResource setSalaryIncomeDetailsAddResource() {
		SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource = new SalaryIncomeDetailsAddResource();
		salaryIncomeDetailsAddResource.setIncomeSourceDetailsId("1");
		return salaryIncomeDetailsAddResource;
	}
	
	 /**
     * incomeSourceDetailsId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void incomeSourceDetailsIdNotNull() {
    	SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource = setSalaryIncomeDetailsAddResource();
    	salaryIncomeDetailsAddResource.setIncomeSourceDetailsId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * incomeSourceDetailsId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void incomeSourceDetailsIdPattern() {
    	SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource = setSalaryIncomeDetailsAddResource();
    	salaryIncomeDetailsAddResource.setIncomeSourceDetailsId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeDetailsAddResource, localValidatorFactory));
	}
	

}
