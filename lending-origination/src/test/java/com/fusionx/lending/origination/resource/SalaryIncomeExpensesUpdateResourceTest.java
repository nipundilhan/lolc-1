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
public class SalaryIncomeExpensesUpdateResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;

	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryIncomeExpensesUpdateResource setSalaryIncomeExpensesUpdateResource() {
		SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = new SalaryIncomeExpensesUpdateResource();
		salaryIncomeExpensesUpdateResource.setId("1");
		salaryIncomeExpensesUpdateResource.setSalaryExpenseTypeId("1");
		salaryIncomeExpensesUpdateResource.setSalaryIncomeTypeId("1");
		salaryIncomeExpensesUpdateResource.setTypeCode("ABCD");
		salaryIncomeExpensesUpdateResource.setDescription("desc");
		salaryIncomeExpensesUpdateResource.setAmount("10.00");
		salaryIncomeExpensesUpdateResource.setFreequencyId("1");
		salaryIncomeExpensesUpdateResource.setFreequencyCode("PQRS");
		return salaryIncomeExpensesUpdateResource;
	}
	
	
	@Test
    public void idNotNull() {
		SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
		salaryIncomeExpensesUpdateResource.setId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }
	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void idPattern() {
    	SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
    	salaryIncomeExpensesUpdateResource.setId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }

    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void salaryExpenseTypeIdPattern() {
    	SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
    	salaryIncomeExpensesUpdateResource.setSalaryExpenseTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }

	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void salaryIncomeTypeIdPattern() {
    	SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
    	salaryIncomeExpensesUpdateResource.setSalaryIncomeTypeId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }
    
    
	@Test
    public void freequencyIdNotNull() {
		SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
		salaryIncomeExpensesUpdateResource.setFreequencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }
	
    /**
     * Commitment Type Id  should consists of alphanumeric characters only.
     * Expected: {common.numeric-pattern}
     */
    @Test
    public void freequencyIdPattern() {
    	SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
    	salaryIncomeExpensesUpdateResource.setFreequencyId("ABC");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }
    
    
	@Test
    public void typeCodeNotNull() {
		SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
		salaryIncomeExpensesUpdateResource.setTypeCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }
	
	
    /**
     * check rate patter.
     * Expected: {rate.pattern}
     */
    @Test
    public void amountPattern() {
    	SalaryIncomeExpensesUpdateResource salaryIncomeExpensesUpdateResource = setSalaryIncomeExpensesUpdateResource();
    	salaryIncomeExpensesUpdateResource.setAmount("ABC");
		assertEquals("{amount.pattern}", TestUtils.getFieldErrorMessageKey(salaryIncomeExpensesUpdateResource, localValidatorFactory));
    }

}
