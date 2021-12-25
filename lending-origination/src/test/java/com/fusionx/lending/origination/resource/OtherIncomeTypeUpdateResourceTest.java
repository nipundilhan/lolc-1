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
 * Other Income Type Update Resource Test
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
public class OtherIncomeTypeUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeTypeUpdateResource setUpdateOtherIncomeTypeUpdateResource() {
		OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = new OtherIncomeTypeUpdateResource();
		otherIncomeTypeUpdateResource.setOtherIncomeCategoryId("1");
		otherIncomeTypeUpdateResource.setOtherIncomeCategoryName("Test");
		otherIncomeTypeUpdateResource.setCode("LOLC");
		otherIncomeTypeUpdateResource.setName("Test");
		otherIncomeTypeUpdateResource.setDescription("Test");
		otherIncomeTypeUpdateResource.setStatus("ACTIVE");
		otherIncomeTypeUpdateResource.setVersion("1");
		return otherIncomeTypeUpdateResource;
	}
	
	 /**
     * otherIncomeCategoryId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryIdNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setOtherIncomeCategoryId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void otherIncomeCategoryIdPattern() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setOtherIncomeCategoryId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
	
    /**
     *  otherIncomeCategoryName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void otherIncomeCategoryNameNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setOtherIncomeCategoryName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * otherIncomeCategoryName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void otherIncomeCategoryNameSize() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setOtherIncomeCategoryName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
		
    }
    
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {description.size}
     */
    @Test
    public void descriptionSize() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{description.size}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource = setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource =setUpdateOtherIncomeTypeUpdateResource();
    	otherIncomeTypeUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeTypeUpdateResource, localValidatorFactory));
	}


}
