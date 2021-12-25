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
 * Exception Approval Group Type Update Resource Test
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
public class ExceptionApprovalGroupTypeUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private ExceptionApprovalGroupTypeUpdateResource setUpdateExceptionApprovalGroupTypeUpdateResource() {
		ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource = new ExceptionApprovalGroupTypeUpdateResource();
		exceptionApprovalGroupTypeUpdateResource.setStatus("ACTIVE");
		exceptionApprovalGroupTypeUpdateResource.setVersion("1");
		return exceptionApprovalGroupTypeUpdateResource;
	}
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource =setUpdateExceptionApprovalGroupTypeUpdateResource();
    	exceptionApprovalGroupTypeUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource =setUpdateExceptionApprovalGroupTypeUpdateResource();
    	exceptionApprovalGroupTypeUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version is required.
     * Expected: {common.not-null}
     */
    @Test
	public void versionNotNull() {
    	ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource = setUpdateExceptionApprovalGroupTypeUpdateResource();
    	exceptionApprovalGroupTypeUpdateResource.setVersion(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void versionPattern() {
    	ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource =setUpdateExceptionApprovalGroupTypeUpdateResource();
    	exceptionApprovalGroupTypeUpdateResource.setVersion("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(exceptionApprovalGroupTypeUpdateResource, localValidatorFactory));
	}


}
