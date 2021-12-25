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
 * 	Business Additional Details Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-09-2021   FXL-115  	 FXL-783       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessAdditionalDetailsUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessAdditionalDetailsUpdateResource setBusinessAdditionalDetailsUpdateResource() {
		BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource = new BusinessAdditionalDetailsUpdateResource();
		businessAdditionalDetailsUpdateResource.setBusinessTypeId("1");
		businessAdditionalDetailsUpdateResource.setBusinessTypeName("TEST");
		businessAdditionalDetailsUpdateResource.setBusinessSubTypeId("1");
		businessAdditionalDetailsUpdateResource.setBusinessSubTypeName("TEST");
		businessAdditionalDetailsUpdateResource.setOwnershipId("1");
		businessAdditionalDetailsUpdateResource.setOwnershipName("TEST");
		businessAdditionalDetailsUpdateResource.setBusinessName("TEST");
		businessAdditionalDetailsUpdateResource.setBusinessSizeId("1");
		businessAdditionalDetailsUpdateResource.setBusinessSizeName("TEST");
		businessAdditionalDetailsUpdateResource.setNoOfYearsInBusiness("5");
		businessAdditionalDetailsUpdateResource.setBusiOpertaionStartDate("2020-09-01");
		businessAdditionalDetailsUpdateResource.setBusinessRegiNo("TEST");
		businessAdditionalDetailsUpdateResource.setBusinessRegiDate("2021-09-01");
		businessAdditionalDetailsUpdateResource.setProfitMargin("10.00");
		businessAdditionalDetailsUpdateResource.setSourceType("PRIMARY");
		businessAdditionalDetailsUpdateResource.setStatus("ACTIVE");
		businessAdditionalDetailsUpdateResource.setVersion("1");
		return businessAdditionalDetailsUpdateResource;
	}
	
	/**
     * businessTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeIdNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessTypeIdPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeNameNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * businessSubTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSubTypeIdNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSubTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessSubTypeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessSubTypeIdPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSubTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessSubTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSubTypeNameNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSubTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * ownershipId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void ownershipIdNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setOwnershipId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * ownershipId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void ownershipIdPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setOwnershipId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * ownershipName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void ownershipNameNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setOwnershipName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * businessSizeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSizeIdNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSizeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessSizeId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessSizeIdPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSizeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessSizeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessSizeNameNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessSizeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessNameNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
	 * businessName cannot be greater than 300 characters.
	 * Expected: {business-name.size}
	 */
	@Test
	public void businessNameSize() {
		BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
		businessAdditionalDetailsUpdateResource.setBusinessName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
				+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{business-name.size}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
		
	}
	
	/**
     * noOfYearsInBusiness is required.
     * Expected: {common.not-null}
     */
    @Test
	public void noOfYearsInBusinessNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setNoOfYearsInBusiness(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessOpertaionStartDate is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessOpertaionStartDateNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusiOpertaionStartDate(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * businessOpertaionStartDate should be in "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
     * Expected: {common.invalid-date-pattern}
     */
    @Test
	public void businessOpertaionStartDatePattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusiOpertaionStartDate("A");
		assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
	 * businessRegiNo cannot be greater than 50 characters.
	 * Expected: {br-no.size}
	 */
	@Test
	public void businessRegiNoSize() {
		BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
		businessAdditionalDetailsUpdateResource.setBusinessRegiNo("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{br-no.size}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
		
	}
    
    /**
     * businessRegiDate should be in "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
     * Expected: {common.invalid-date-pattern}
     */
    @Test
	public void businessRegiDatePattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setBusinessRegiDate("A");
		assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * profitMargin should be in "^\\d*[0-9](\\.\\d{1,2})?$" pattern.
     * Expected: {common.invalid-amount-pattern}
     */
    @Test
	public void profitMarginPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setProfitMargin("A");
		assertEquals("{common.invalid-amount-pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource =setBusinessAdditionalDetailsUpdateResource();
    	businessAdditionalDetailsUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessAdditionalDetailsUpdateResource, localValidatorFactory));
	}
    
    
   
}
