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
public class OtherIncomeCategoryUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private OtherIncomeCategoryUpdateResource setOtherIncomeCategoryUpdateResource() {
		OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = new OtherIncomeCategoryUpdateResource();
		otherIncomeCategoryUpdateResource.setCode("LOLC");
		otherIncomeCategoryUpdateResource.setName("Test");
		otherIncomeCategoryUpdateResource.setDescription("Test");
		otherIncomeCategoryUpdateResource.setStatus("ACTIVE");
		otherIncomeCategoryUpdateResource.setVersion("1");
		return otherIncomeCategoryUpdateResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource = setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource =setOtherIncomeCategoryUpdateResource();
    	otherIncomeCategoryUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(otherIncomeCategoryUpdateResource, localValidatorFactory));
	}

}
