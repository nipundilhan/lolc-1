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
 * 	Exception Approval Group Update Resource Test
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
public class ExceptionApprovalGroupUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ExceptionApprovalGroupUpdateResource setExceptionApprovalGroupUpdateResource() {
		ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = new ExceptionApprovalGroupUpdateResource();
		exceptionApprovalGroupUpdateResource.setCode("LOLC");
		exceptionApprovalGroupUpdateResource.setName("Test");
		exceptionApprovalGroupUpdateResource.setDescription("Test");
		exceptionApprovalGroupUpdateResource.setStatus("ACTIVE");
		exceptionApprovalGroupUpdateResource.setVersion("1");
		return exceptionApprovalGroupUpdateResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setDescription("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
		
    }
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource = setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource =setExceptionApprovalGroupUpdateResource();
    	exceptionApprovalGroupUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupUpdateResource, localValidatorFactory));
	}

}
