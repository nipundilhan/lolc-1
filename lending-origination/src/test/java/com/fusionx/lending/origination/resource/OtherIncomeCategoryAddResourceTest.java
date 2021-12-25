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
 * Other Income Category Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639          FXL-535      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeCategoryAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeCategoryAddResource setOtherIncomeCategoryAddResource() {
		OtherIncomeCategoryAddResource otherIncomeCategoryAddResource = new OtherIncomeCategoryAddResource();
		otherIncomeCategoryAddResource.setCode("LOLC");
		otherIncomeCategoryAddResource.setName("Test");
		otherIncomeCategoryAddResource.setDescription("Test");
		otherIncomeCategoryAddResource.setStatus("ACTIVE");
		return otherIncomeCategoryAddResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource = setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource = setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource =setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource = setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource =setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource =setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource =setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	OtherIncomeCategoryAddResource otherIncomeCategoryAddResource =setOtherIncomeCategoryAddResource();
    	otherIncomeCategoryAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryAddResource, localValidatorFactory));
		
    }

}
