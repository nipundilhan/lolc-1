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
 * 	Exception Approval Group Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionApprovalGroupAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ExceptionApprovalGroupAddResource setExceptionApprovalGroupAddResource() {
		ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource = new ExceptionApprovalGroupAddResource();
		exceptionApprovalGroupAddResource.setCode("LOLC");
		exceptionApprovalGroupAddResource.setName("Test");
		exceptionApprovalGroupAddResource.setDescription("Test");
		exceptionApprovalGroupAddResource.setStatus("ACTIVE");
		return exceptionApprovalGroupAddResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource = setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource = setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource =setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource = setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource =setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource =setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource =setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource =setExceptionApprovalGroupAddResource();
    	exceptionApprovalGroupAddResource.setDescription("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupAddResource, localValidatorFactory));
		
    }

}
