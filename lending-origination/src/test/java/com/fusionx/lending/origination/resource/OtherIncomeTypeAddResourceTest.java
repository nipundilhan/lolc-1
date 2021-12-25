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
 * Other Income Type Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-08-2021   FXL-639       FXL-537      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeTypeAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeTypeAddResource setOtherIncomeTypeAddResource() {
		OtherIncomeTypeAddResource otherIncomeTypeAddResource = new OtherIncomeTypeAddResource();
		otherIncomeTypeAddResource.setOtherIncomeCategoryId("1");
		otherIncomeTypeAddResource.setOtherIncomeCategoryName("Test");
		otherIncomeTypeAddResource.setCode("LOLC");
		otherIncomeTypeAddResource.setName("Test");
		otherIncomeTypeAddResource.setDescription("Test");
		otherIncomeTypeAddResource.setStatus("ACTIVE");
		return otherIncomeTypeAddResource;
	}
	
	 /**
     * otherIncomeCategoryId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryIdNotNull() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setOtherIncomeCategoryId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
   

	/**
     * otherIncomeCategoryId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void otherIncomeCategoryIdPattern() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setOtherIncomeCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
	
    /**
     *  otherIncomeCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryNameNotNull() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setOtherIncomeCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void otherIncomeCategoryNameSize() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setOtherIncomeCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
		
    }
    
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource = setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {description.size}
     */
    @Test
    public void descriptionSize() {
    	OtherIncomeTypeAddResource otherIncomeTypeAddResource =setOtherIncomeTypeAddResource();
    	otherIncomeTypeAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{description.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeAddResource, localValidatorFactory));
		
    }
    
   
}
