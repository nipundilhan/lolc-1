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
 * Other Income Expense Type Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeExpenseTypeAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeExpenseTypeAddResource setOtherIncomeExpenseTypeAddResource() {
		OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = new OtherIncomeExpenseTypeAddResource();
		otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryId("1");
		otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryName("Test");
		otherIncomeExpenseTypeAddResource.setStatus("ACTIVE");
		return otherIncomeExpenseTypeAddResource;
	}
	
	 /**
     * otherIncomeCategoryId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryIdNotNull() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void otherIncomeCategoryIdPattern() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
	}
	
    /**
     *  otherIncomeCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryNameNotNull() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource = setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void otherIncomeCategoryNameSize() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource =setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setOtherIncomeCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
		
    }
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource =setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource =setOtherIncomeExpenseTypeAddResource();
    	otherIncomeExpenseTypeAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeAddResource, localValidatorFactory));
	}
   
}
