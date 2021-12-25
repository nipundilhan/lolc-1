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
 * Salary Expense Type Update Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-08-2021   FXL-521          FXL-685      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryExpenseTypeUpdateTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private SalaryExpenseTypeUpdateResource setSalaryExpenseTypeUpdateResource() {
		SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = new SalaryExpenseTypeUpdateResource();
		salaryExpenseTypeUpdateResource.setExpenseTypeId("1");
		salaryExpenseTypeUpdateResource.setExpenseTypeName("Test");
		salaryExpenseTypeUpdateResource.setStatus("ACTIVE");
		salaryExpenseTypeUpdateResource.setVersion("1");
		return salaryExpenseTypeUpdateResource;
	}
	
	 /**
     * expenseTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void expenseTypeIdNotNull() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setExpenseTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * expenseTypeId Pattern is "^$|[0-9]+".
     * Expected: {common.invalid-number-format}
     */
    @Test
	public void expenseTypeIdPattern() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setExpenseTypeId("A");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
	
    /**
     *  expenseTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void expenseTypeNameNotNull() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setExpenseTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * expenseTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void expenseTypeNameSize() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource =setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setExpenseTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
		
    }
    
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource =setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.patternn}
     */
    @Test
	public void statusPattern() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource =setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource = setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource =setSalaryExpenseTypeUpdateResource();
    	salaryExpenseTypeUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(salaryExpenseTypeUpdateResource, localValidatorFactory));
	}


}
