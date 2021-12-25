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
 * Exception Approval Group Type Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionApprovalGroupTypeAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ExceptionApprovalGroupTypeAddResource setExceptionApprovalGroupTypeAddResource() {
		ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = new ExceptionApprovalGroupTypeAddResource();
		exceptionApprovalGroupTypeAddResource.setExceptionApprovalGroupId("1");
		exceptionApprovalGroupTypeAddResource.setExceptionTypeId("1");
		exceptionApprovalGroupTypeAddResource.setExceptionTypeName("Test");
		exceptionApprovalGroupTypeAddResource.setStatus("ACTIVE");
		return exceptionApprovalGroupTypeAddResource;
	}
	
	 /**
     * exceptionApprovalGroupId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void exceptionApprovalGroupIdNotNull() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionApprovalGroupId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
    
    /**
     * exceptionApprovalGroupId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void exceptionApprovalGroupIdPattern() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionApprovalGroupId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
	 /**
     * exceptionTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void exceptionTypeIdNotNull() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
    
    /**
     * exceptionTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void exceptionTypeIdPattern() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
	
    /**
     *  exceptionTypeName is required.
     * Expected: {common.not-null}
     */
    @Test
	public void eexceptionTypeNameNotNull() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource = setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
    
    /**
     * exceptionTypeName cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void exceptionTypeNameSize() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource =setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setExceptionTypeName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
		
    }
	
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource =setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource =setExceptionApprovalGroupTypeAddResource();
    	exceptionApprovalGroupTypeAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeAddResource, localValidatorFactory));
	}
   
}
