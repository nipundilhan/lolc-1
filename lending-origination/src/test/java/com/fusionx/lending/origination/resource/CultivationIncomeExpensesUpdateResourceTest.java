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
 * 	Cultivation Income Expenses Update Resource Test
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
public class CultivationIncomeExpensesUpdateResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CultivationIncomeExpensesUpdateResource setCultivationIncomeExpensesUpdateResource() {
		CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResource = new CultivationIncomeExpensesUpdateResource();
		//cultivationIncomeExpensesAddResource.setCultivationIncomeDetailsId("1");
		cultivationIncomeExpensesUpdateResource.setFrequencyCode("WEEKLY");
		cultivationIncomeExpensesUpdateResource.setFrequencyId("1");
		cultivationIncomeExpensesUpdateResource.setCurrencyId("1");
		cultivationIncomeExpensesUpdateResource.setCurrencyName("Sri Lanka Rupe");
		cultivationIncomeExpensesUpdateResource.setIncomeTypeId("1");
		cultivationIncomeExpensesUpdateResource.setTypeName("test");
		cultivationIncomeExpensesUpdateResource.setExpenseTypeId("1");
		cultivationIncomeExpensesUpdateResource.setAmount("5000.00");
		cultivationIncomeExpensesUpdateResource.setDescription("xxxxxxxxxx");
		cultivationIncomeExpensesUpdateResource.setStatus("ACTIVE");
		cultivationIncomeExpensesUpdateResource.setVersion("1");
		return cultivationIncomeExpensesUpdateResource;
	}
	
    /**
     * frequencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void frequencyIdNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setFrequencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
	
    /**
     * frequencyId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void frequencyIdPattern() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setFrequencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
    /**
     * frequencyCode is required.
     * Expected: {code.not-null}
     */
    @Test
	public void frequencyCodeNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setFrequencyCode(null);
		assertEquals("{code.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}

	/**
     * currencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyIdNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
    /**
     * currencyName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyNameNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setCurrencyName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
    /**
     * incomeTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void incomeTypeIdPattern() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setIncomeTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    /**
     * expenseTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
    public void expenseTypeIdPattern() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setExpenseTypeId("A");
    	assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
    }
    
    /**
     * typeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void typeNameNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
    /**
   	 * typeName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void typeNameSize() {
   		CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
   		cultivationIncomeExpensesUpdateResourc.setTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
   		
   	}
   	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
	/**
     * status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
   /**
   * amount should be digits.
   * Expected: {common-amount.pattern}
   */
   @Test
   public void costPattern() {
	   CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
	   cultivationIncomeExpensesUpdateResourc.setAmount("ABCD");
	   assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
   }
  
  /**
   * amount Cannot be blank.
   * Expected: {common.not-null}
   */
	@Test
    public void costNotNull() {
		CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
		cultivationIncomeExpensesUpdateResourc.setAmount(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
    }
	
	  /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
    	cultivationIncomeExpensesUpdateResourc.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
    /**
	 * version should be in "^$|[0-9]+" pattern
	 * Expected: {common-numeric.pattern}
	 */
	@Test
	public void versionPattern() {
		CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResourc = setCultivationIncomeExpensesUpdateResource();
		cultivationIncomeExpensesUpdateResourc.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeExpensesUpdateResourc, localValidatorFactory));
	}
    
}
