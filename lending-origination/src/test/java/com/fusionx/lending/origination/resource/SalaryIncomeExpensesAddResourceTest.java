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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryIncomeExpensesAddResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryIncomeExpensesAddResource setSalaryIncomeExpensesAddResource() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = new SalaryIncomeExpensesAddResource();
		
		salaryIncomeExpensesAddResource.setSalaryExpenseTypeId("1");
		salaryIncomeExpensesAddResource.setSalaryIncomeTypeId("1");
		salaryIncomeExpensesAddResource.setTypeCode("ABCD");
		salaryIncomeExpensesAddResource.setDescription("desc");
		salaryIncomeExpensesAddResource.setAmount("10.00");
		salaryIncomeExpensesAddResource.setFreequencyId("1");
		salaryIncomeExpensesAddResource.setFreequencyCode("PQRS");
		return salaryIncomeExpensesAddResource;
	}
	
	
	

    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void salaryExpenseTypeIdPattern() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setSalaryExpenseTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }

	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void salaryIncomeTypeIdPattern() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setSalaryIncomeTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }
    
    
	@Test
    public void freequencyIdNotNull() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setFreequencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }
	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void freequencyIdPattern() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setFreequencyId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }
    
    
	@Test
    public void typeCodeNotNull() {
		SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setTypeCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }
	
	
    /**
     * check rate patter.
     * Expected: {rate.pattern}
     */
    @Test
    public void amountPattern() {
    	SalaryIncomeExpensesAddResource salaryIncomeExpensesAddResource = setSalaryIncomeExpensesAddResource();
		salaryIncomeExpensesAddResource.setAmount("ABC");
		assertEquals("{amount.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesAddResource, localValidatorFactory));
    }
}
