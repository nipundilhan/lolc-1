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
 * 	Cultivation Income Details List Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021  	             FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultivationIncomeDetailsListResourceTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private CultivationIncomeDetailsListResource setCultivationIncomeDetailsListResource() {
		CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource = new CultivationIncomeDetailsListResource();
		cultivationIncomeDetailsListResource.setCultivationCategoryId("1");
		cultivationIncomeDetailsListResource.setCultivationCategoryName("test");
		cultivationIncomeDetailsListResource.setLandOwnershipId("1");
		cultivationIncomeDetailsListResource.setLandOwnershipName("test");
		cultivationIncomeDetailsListResource.setPlantOwnershipId("1");
		cultivationIncomeDetailsListResource.setPlantOwnershipName("test");
		cultivationIncomeDetailsListResource.setDescription("xxxxxxxxxx");
		cultivationIncomeDetailsListResource.setSizeOfLand("xxx");
		cultivationIncomeDetailsListResource.setNoOfEmployees("4");
		cultivationIncomeDetailsListResource.setNoOfYears("3");
		cultivationIncomeDetailsListResource.setSourceType("PRIMARY");
		cultivationIncomeDetailsListResource.setStatus("ACTIVE");
		return cultivationIncomeDetailsListResource;
	}
	
	 /**
     * cultivationCategoryId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void designationIdNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setCultivationCategoryId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
	/**
     * cultivationCategoryId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void cultivationCategoryIdPattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setCultivationCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * cultivationCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void cultivationCategoryNameNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setCultivationCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
   	 * cultivationCategoryName cannot be greater than 70 characters.
   	 * Expected: {common-name.size}
   	 */
   	@Test
   	public void cultivationCategoryNameSize() {
   		CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
   		cultivationIncomeDetailsListResource.setCultivationCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
   		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
   		
   	}
   	
   	/**
     * noOfYears is required.
     * Expected: {common.not-null}
     */
    @Test
	public void noOfYearsNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setNoOfYears(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * noOfYears should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void noOfYearsPattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setNoOfYears("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * landOwnershipId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void landOwnershipIdNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setLandOwnershipId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
	
    /**
     * landOwnershipId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void landOwnershipIdPattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setLandOwnershipId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * landOwnershipName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void landOwnershipNameNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setLandOwnershipName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
	 * landOwnershipName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void landOwnershipNameSize() {
		CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
		cultivationIncomeDetailsListResource.setLandOwnershipName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
		
	}
	
	 /**
     * plantOwnershipId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void plantOwnershipIdNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setPlantOwnershipId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
	
    /**
     * plantOwnershipId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void plantOwnershipIdPattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setPlantOwnershipId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
     * plantOwnershipName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void plantOwnershipNameNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setPlantOwnershipName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    /**
	 * plantOwnershipName cannot be greater than 70 characters.
	 * Expected: {common-name.size}
	 */
	@Test
	public void plantOwnershipNameSize() {
		CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
		cultivationIncomeDetailsListResource.setPlantOwnershipName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
		
	}
	
	/**
     * sourceType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void sourceTypeNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setSourceType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
	/**
     * sourceType should be PRIMARY or SECONDARY.
     * Expected: {common-source.pattern}
     */
    @Test
	public void sourceTypePattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setSourceType("ABCDEF");
		assertEquals("{common-source.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
	
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	CultivationIncomeDetailsListResource cultivationIncomeDetailsListResource =setCultivationIncomeDetailsListResource();
    	cultivationIncomeDetailsListResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(cultivationIncomeDetailsListResource, localValidatorFactory));
	}
    
    
   
}
