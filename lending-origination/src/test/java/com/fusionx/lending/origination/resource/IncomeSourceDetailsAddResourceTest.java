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
 * 	Income Source Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeSourceDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private IncomeSourceDetailsAddResource setIncomeSourceDetailsAddResource() {
		IncomeSourceDetailsAddResource incomeSourceDetailsAddResource = new IncomeSourceDetailsAddResource();
		incomeSourceDetailsAddResource.setLeadId("1");
		incomeSourceDetailsAddResource.setCustomerId("1");
		incomeSourceDetailsAddResource.setCustomerFullname("Test");
		incomeSourceDetailsAddResource.setLinkedPersonId("1");
		incomeSourceDetailsAddResource.setLinkedPersonName("Test");
		incomeSourceDetailsAddResource.setIncomeType("SALARY");
		incomeSourceDetailsAddResource.setSourceType("PRIMARY");
		incomeSourceDetailsAddResource.setStatus("ACTIVE");
		return incomeSourceDetailsAddResource;
	}
	
	/**
     * leadId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void leadIdPattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setLeadId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * customerId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void customerIdPattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setCustomerId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
    /**
	 * customerFullname cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void customerFullnameSize() {
		IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
		incomeSourceDetailsAddResource.setCustomerFullname("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
		
	}
	
    /**
     * linkedPersonId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void linkedPersonIdPattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setLinkedPersonId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
    /**
	 * linkedPersonName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void linkedPersonNameSize() {
		IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
		incomeSourceDetailsAddResource.setLinkedPersonName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
		
	}
	
	/**
     * incomeType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void incomeTypeNotNull() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setIncomeType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * incomeType should be SALARY|BUSINESS|CULTIVATION|OTHER.
     * Expected: {common-income.pattern}
     */
    @Test
	public void incomeTypePattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setIncomeType("ABCDEF");
		assertEquals("{common-income.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
	
	/**
     * sourceType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sourceTypeNotNull() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setSourceType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	IncomeSourceDetailsAddResource incomeSourceDetailsAddResource =setIncomeSourceDetailsAddResource();
    	incomeSourceDetailsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(incomeSourceDetailsAddResource, localValidatorFactory));
	}
    
    
   
}
