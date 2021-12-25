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
 * 	Business Sub Center Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-08-2021             	 FXL-649       Nipun Dilhan     Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessSubCenterAddResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private BusinessSubCenterAddResource setBusinessSubCenterAddResource() {
		BusinessSubCenterAddResource businessSubCenterAddResource = new BusinessSubCenterAddResource();
		businessSubCenterAddResource.setBusinessCenterId("1");
		businessSubCenterAddResource.setBusinessCenterName("name");
		businessSubCenterAddResource.setCode("LB01");
		businessSubCenterAddResource.setName("name");
		businessSubCenterAddResource.setEmpId("10");
		businessSubCenterAddResource.setEmpNo("20EMP");
		businessSubCenterAddResource.setEmpName("EMPNAME");
		businessSubCenterAddResource.setMaxClientPerSubCenter("20");
		businessSubCenterAddResource.setMaxSubCenterLimit("1000");
		businessSubCenterAddResource.setStatus("ACTIVE");		
		return businessSubCenterAddResource;
	}
	
	
	
	 /**
     * businessTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessCenterIdNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setBusinessCenterId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void  businessCenterIdPattern() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setBusinessCenterId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
	
    /**
     *  businessTypename is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessCenterNameNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setBusinessCenterName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    

    
	 /**
     * code is required.
     * Expected: {code.not-null}
     */
    @Test
	public void codeNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common.code.size}
     */
    @Test
	public void codeSize() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {code.pattern}
     */
    @Test
	public void codePattern() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {name.not-null}
     */
    @Test
	public void nameNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {status.not-null}
     */
    @Test
	public void statusNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {value.pattern}
     */
    @Test
	public void statusPattern() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    
    @Test
	public void maxClientPerSubCenterNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setMaxClientPerSubCenter(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void  maxClientPerSubCenterIdPattern() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setMaxClientPerSubCenter("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
  
    
    
    @Test
	public void maxSubCenterLimitNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setMaxSubCenterLimit(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void  maxSubCenterLimitPattern() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setMaxSubCenterLimit("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    
    @Test
	public void empIdNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setEmpId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
//    /**
//     * businessTypeId Pattern is "^$|[0-9]+".
//     * Expected: {common-numeric.pattern}
//     */
//    @Test
//	public void  empIdPattern() {
//    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
//    	businessSubCenterAddResource.setEmpId("A");
//		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
//	}
    
    @Test
	public void empNameNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setEmpName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    @Test
	public void empNoNotNull() {
    	BusinessSubCenterAddResource businessSubCenterAddResource = setBusinessSubCenterAddResource();
    	businessSubCenterAddResource.setEmpNo(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(businessSubCenterAddResource, localValidatorFactory));
	}
    
    
}
