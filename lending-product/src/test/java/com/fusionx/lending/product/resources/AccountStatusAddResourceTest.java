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
 * Account Status Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021                  FXL-789   Dilhan       Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class AccountStatusAddResourceTest {
/*
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private AccountStatusAddResource setAccountStatusAddResource() {
		AccountStatusAddResource accountStatusAddResource = new AccountStatusAddResource();
		accountStatusAddResource.setCode("RF01");
		accountStatusAddResource.setName("Test");
		accountStatusAddResource.setDescription("Test");
		accountStatusAddResource.setStatus("ACTIVE");
		accountStatusAddResource.setOrder("2");
		return accountStatusAddResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void codeNotNull() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
/*    @Test
	public void codeSize() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
 /*   @Test
	public void codePattern() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
  /*  @Test
	public void nameNotNull() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
 /*   @Test
    public void nameSize() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
 /*   @Test
	public void statusNotNull() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
/*    @Test
	public void statusPattern() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    
    /**
     * order should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
 /*   @Test
	public void orderPattern() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setOrder("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
	}
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-length01.size}
     */
/*    @Test
    public void descriptionSize() {
    	AccountStatusAddResource accountStatusAddResource = setAccountStatusAddResource();
    	accountStatusAddResource.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-length01.size}", TestUtils.getFieldErrorMessageKey(accountStatusAddResource, localValidatorFactory));
		
    }
*/
}
