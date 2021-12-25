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
 * 	Cultivation Income Expense Resource Test
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
public class CultivationIncomeExpenseResourceTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CultivationIncomeExpenseResource setCultivationIncomeExpenseResource() {
		CultivationIncomeExpenseResource cultivationIncomeExpenseResource = new CultivationIncomeExpenseResource();
		cultivationIncomeExpenseResource.setIncomeTypeId("1");
		cultivationIncomeExpenseResource.setTypeName("test");
		cultivationIncomeExpenseResource.setExpenseTypeId("1");
		cultivationIncomeExpenseResource.setAmount("5000.00");
		cultivationIncomeExpenseResource.setDescription("xxxxxxxxxx");
		cultivationIncomeExpenseResource.setStatus("ACTIVE");
		return cultivationIncomeExpenseResource;
	}
	
	/**
     * incomeTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void incomeTypeIdPattern() {
    	CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
    	cultivationIncomeExpenseResource.setIncomeTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
	}
    /**
     * expenseTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void expenseTypeIdPattern() {
    	CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
    	cultivationIncomeExpenseResource.setExpenseTypeId("A");
    	assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
    }
    
    /**
     * typeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void typeNameNotNull() {
    	CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
    	cultivationIncomeExpenseResource.setTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
	}
    
    /**
   	 * typeName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void typeNameSize() {
   		CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
   		cultivationIncomeExpenseResource.setTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
   		
   	}
   	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
    	cultivationIncomeExpenseResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
	}
    
	/**
     * status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
    	cultivationIncomeExpenseResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
	}
    
    /**
   * amount should be digits.
   * Expected: {common-amount.pattern}
   */
   @Test
   public void costPattern() {
	   CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
	   cultivationIncomeExpenseResource.setAmount("ABCD");
	   assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
   }
  
  /**
   * amount Cannot be blank.
   * Expected: {common.not-null}
   */
	@Test
    public void costNotNull() {
		CultivationIncomeExpenseResource cultivationIncomeExpenseResource =setCultivationIncomeExpenseResource();
		cultivationIncomeExpenseResource.setAmount(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpenseResource, localValidatorFactory));
    }
   
}
