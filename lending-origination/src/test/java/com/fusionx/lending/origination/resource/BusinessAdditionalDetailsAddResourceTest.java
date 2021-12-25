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
 * 	Business Additional Details Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessAdditionalDetailsAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessAdditionalDetailsAddResource setBusinessAdditionalDetailsAddResource() {
		BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource = new BusinessAdditionalDetailsAddResource();
		businessAdditionalDetailsAddResource.setBusinessTypeId("1");
		businessAdditionalDetailsAddResource.setBusinessTypeName("TEST");
		businessAdditionalDetailsAddResource.setBusinessSubTypeId("1");
		businessAdditionalDetailsAddResource.setBusinessSubTypeName("TEST");
		businessAdditionalDetailsAddResource.setOwnershipId("1");
		businessAdditionalDetailsAddResource.setOwnershipName("TEST");
		businessAdditionalDetailsAddResource.setBusinessName("TEST");
		businessAdditionalDetailsAddResource.setBusinessSizeId("1");
		businessAdditionalDetailsAddResource.setBusinessSizeName("TEST");
		businessAdditionalDetailsAddResource.setNoOfYearsInBusiness("5");
		businessAdditionalDetailsAddResource.setBusiOpertaionStartDate("2020-09-01");
		businessAdditionalDetailsAddResource.setBusinessRegiNo("TEST");
		businessAdditionalDetailsAddResource.setBusinessRegiDate("2021-09-01");
		//businessAdditionalDetailsAddResource.setDescription("TEST");
		businessAdditionalDetailsAddResource.setProfitMargin("10.00");
		//businessAdditionalDetailsAddResource.setComment("TEST");
		//businessAdditionalDetailsAddResource.setNoOfBranches(10l);
		//businessAdditionalDetailsAddResource.setSkillsOfKeyPerson("TEST");
		//businessAdditionalDetailsAddResource.setPreviousBusiHistory("TEST");
		//businessAdditionalDetailsAddResource.setBusiContinuityPlan("TEST");
		businessAdditionalDetailsAddResource.setLeadId("1");
		businessAdditionalDetailsAddResource.setCustomerId("1");
		businessAdditionalDetailsAddResource.setLinkedPersonId("1");
		businessAdditionalDetailsAddResource.setSourceType("PRIMARY");
		businessAdditionalDetailsAddResource.setStatus("ACTIVE");
		return businessAdditionalDetailsAddResource;
	}
	
	/**
     * businessTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeIdNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessTypeIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeNameNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * businessSubTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSubTypeIdNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSubTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessSubTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessSubTypeIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSubTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessSubTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSubTypeNameNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSubTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * ownershipId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void ownershipIdNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setOwnershipId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * ownershipId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void ownershipIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setOwnershipId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * ownershipName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void ownershipNameNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setOwnershipName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * businessSizeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSizeIdNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSizeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessSizeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessSizeIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSizeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessSizeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSizeNameNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessSizeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessNameNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
	 * businessName cannot be greater than 300 characters.
	 * Expected: {business-name.size}
	 */
	@Test
	public void businessNameSize() {
		BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
		businessAdditionalDetailsAddResource.setBusinessName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{business-name.size}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
		
	}
	
	/**
     * noOfYearsInBusiness is required.
     * Expected: {common.not-null}
     */
    @Test
	public void noOfYearsInBusinessNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setNoOfYearsInBusiness(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessOpertaionStartDate is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessOpertaionStartDateNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusiOpertaionStartDate(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * businessOpertaionStartDate should be in "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
     * Expected: {common.invalid-date-pattern}
     */
    @Test
	public void businessOpertaionStartDatePattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusiOpertaionStartDate("A");
		assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
	 * businessRegiNo cannot be greater than 50 characters.
	 * Expected: {br-no.size}
	 */
	@Test
	public void businessRegiNoSize() {
		BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
		businessAdditionalDetailsAddResource.setBusinessRegiNo("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{br-no.size}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
		
	}
    
    /**
     * businessRegiDate should be in "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
     * Expected: {common.invalid-date-pattern}
     */
    @Test
	public void businessRegiDatePattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setBusinessRegiDate("A");
		assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    /**
     * profitMargin should be in "^\\d*[0-9](\\.\\d{1,2})?$" pattern.
     * Expected: {common.invalid-amount-pattern}
     */
    @Test
	public void profitMarginPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setProfitMargin("A");
		assertEquals("{common.invalid-amount-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
	
	 /**
     * leadId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void leadIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setLeadId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
	
	 /**
     * customerId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void customerIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setCustomerId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
	
    /**
     * linkedPersonId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void linkedPersonIdPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setLinkedPersonId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource =setBusinessAdditionalDetailsAddResource();
    	businessAdditionalDetailsAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsAddResource, localValidatorFactory));
	}
    
    
   
}
