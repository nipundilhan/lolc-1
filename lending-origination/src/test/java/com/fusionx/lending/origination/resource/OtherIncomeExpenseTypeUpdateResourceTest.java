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
 * Other Income Expense Type Update Resource Test
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
public class OtherIncomeExpenseTypeUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeExpenseTypeUpdateResource setUpdateOtherIncomeExpenseTypeUpdateResource() {
		OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = new OtherIncomeExpenseTypeUpdateResource();
		otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryId("1");
		otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryName("Test");
		otherIncomeExpenseTypeUpdateResource.setStatus("ACTIVE");
		otherIncomeExpenseTypeUpdateResource.setVersion("1");
		return otherIncomeExpenseTypeUpdateResource;
	}
	
	 /**
     * otherIncomeCategoryId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryIdNotNull() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void otherIncomeCategoryIdPattern() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
	
    /**
     *  otherIncomeCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryNameNotNull() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void otherIncomeCategoryNameSize() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource =setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setOtherIncomeCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
		
    }
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource =setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    //@Test
	public void statusPattern() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource =setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource = setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    //@Test
	public void versionPattern() {
    	OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource =setUpdateOtherIncomeExpenseTypeUpdateResource();
    	otherIncomeExpenseTypeUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeExpenseTypeUpdateResource, localValidatorFactory));
	}


}
