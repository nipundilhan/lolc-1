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
 * Interest Template Add Resource Test
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
public class InterestTemplateAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private InterestTemplateAddResource setInterestTemplateAddResource() {
		InterestTemplateAddResource interestTemplateAddResource = new InterestTemplateAddResource();
		interestTemplateAddResource.setCode("RF01");
		interestTemplateAddResource.setName("Test");
		interestTemplateAddResource.setStatus("ACTIVE");
		return interestTemplateAddResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	InterestTemplateAddResource interestTemplateAddResource = setInterestTemplateAddResource();
    	interestTemplateAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	InterestTemplateAddResource interestTemplateAddResource = setInterestTemplateAddResource();
    	interestTemplateAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	InterestTemplateAddResource interestTemplateAddResource =setInterestTemplateAddResource();
    	interestTemplateAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	InterestTemplateAddResource interestTemplateAddResource = setInterestTemplateAddResource();
    	interestTemplateAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	InterestTemplateAddResource interestTemplateAddResource =setInterestTemplateAddResource();
    	interestTemplateAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\r\n"
    			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	InterestTemplateAddResource interestTemplateAddResource =setInterestTemplateAddResource();
    	interestTemplateAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	InterestTemplateAddResource interestTemplateAddResource =setInterestTemplateAddResource();
    	interestTemplateAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(interestTemplateAddResource, localValidatorFactory));
	}
    

}
