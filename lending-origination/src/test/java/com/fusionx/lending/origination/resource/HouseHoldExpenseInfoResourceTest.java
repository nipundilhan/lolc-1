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
 * 	House Hold Expense Info Resource Test
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
public class HouseHoldExpenseInfoResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private HouseHoldExpenseInfoResource setHouseHoldExpenseInfoResource() {
		HouseHoldExpenseInfoResource houseHoldExpenseInfoResource = new HouseHoldExpenseInfoResource();
		houseHoldExpenseInfoResource.setId("1");
		houseHoldExpenseInfoResource.setCalculationFrequencyCode("WEEKLY");
		houseHoldExpenseInfoResource.setCalculationFrequencyId("2");
		houseHoldExpenseInfoResource.setExpenseTypeId("1");
		houseHoldExpenseInfoResource.setExpenseTypeName("house hold");
		houseHoldExpenseInfoResource.setHouseHoldExpenseCategoryId("2");
		houseHoldExpenseInfoResource.setHouseHoldExpenseCategoryName("house hold category");
		houseHoldExpenseInfoResource.setCost("5000.00");
		houseHoldExpenseInfoResource.setOccuranceFrequencyId("1");
		houseHoldExpenseInfoResource.setOccuranceFrequencyCode("WEEKLY");
		houseHoldExpenseInfoResource.setFinalCost("10000.00");
		houseHoldExpenseInfoResource.setCurrencyId("1");
		houseHoldExpenseInfoResource.setCurrencyName("Sri Lanka Rupee");
		houseHoldExpenseInfoResource.setStatus("ACTIVE");
		return houseHoldExpenseInfoResource;
	}
	

	/**
     * id should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
	 @Test
	 public void idPattern() {
		 HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
		 houseHoldExpenseInfoResource.setId("ABC");
		 assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	 }
	
	/**
     * houseHoldExpenseCategoryId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void houseHoldExpenseCategoryIdPattern() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setHouseHoldExpenseCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    /**
     * houseHoldExpenseCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void houseHoldExpenseCategoryNameNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setHouseHoldExpenseCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    /**
   	 * houseHoldExpenseCategoryName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void houseHoldExpenseCategoryNameSize() {
   		HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
   		houseHoldExpenseInfoResource.setHouseHoldExpenseCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * expenseTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void expenseTypeIdPattern() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setExpenseTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    /**
     * expenseTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void expenseTypeNameNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setExpenseTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    /**
   	 * houseHoldExpenseCategoryName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void expenseTypeNameSize() {
   		HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
   		houseHoldExpenseInfoResource.setExpenseTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
   		
   	}
   	
    /**
     * calculationFrequencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void calculationFrequencyIdNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setCalculationFrequencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
	
    /**
     * calculationFrequencyId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void calculationFrequencyIdPattern() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setCalculationFrequencyId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    /**
     * calculationFrequencyCode is required.
     * Expected: {code.not-null}
     */
    @Test
	public void calculationFrequencyCodeNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setCalculationFrequencyCode(null);
		assertEquals("{code.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
    
    /**
   * Cost should be digits.
   * Expected: {common-amount.pattern}
   */
   @Test
   public void costPattern() {
	   HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
	   houseHoldExpenseInfoResource.setCost("ABCD");
	   assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
   }
  
  /**
   * Cost Cannot be blank.
   * Expected: {common.not-null}
   */
	@Test
    public void costNotNull() {
		HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
		houseHoldExpenseInfoResource.setCost(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
    }
	
	/**
     * currencyId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void currencyIdNotNull() {
    	HouseHoldExpenseInfoResource houseHoldExpenseInfoResource =setHouseHoldExpenseInfoResource();
    	houseHoldExpenseInfoResource.setCurrencyId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(houseHoldExpenseInfoResource, localValidatorFactory));
	}
    
}
