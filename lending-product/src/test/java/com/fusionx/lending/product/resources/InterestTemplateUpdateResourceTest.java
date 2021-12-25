package com.fusionx.lending.product.resources;

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

import com.fusionx.lending.product.utill.TestUtils;

/**
 * Interest Template Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-09-2021   FXL_156  	FXL-931	      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterestTemplateUpdateResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTemplateUpdateResource setInterestTemplateUpdateResource() {
		InterestTemplateUpdateResource interestTemplateUpdateResource = new InterestTemplateUpdateResource();
		interestTemplateUpdateResource.setCode("RF01");
		interestTemplateUpdateResource.setName("Test");
		interestTemplateUpdateResource.setStatus("ACTIVE");
		interestTemplateUpdateResource.setVersion("1");
		return interestTemplateUpdateResource;
	}
	
	/**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource = setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource = setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource =setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource = setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource =setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource =setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource =setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
	
	 /**
     * version is required.
     * Expected: {version.not-null}
     */
    @Test
	public void versionNotNull() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource = setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
    @Test
	public void versionPattern() {
    	InterestTemplateUpdateResource interestTemplateUpdateResource =setInterestTemplateUpdateResource();
    	interestTemplateUpdateResource.setVersion("A");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(interestTemplateUpdateResource, localValidatorFactory));
	}

}
