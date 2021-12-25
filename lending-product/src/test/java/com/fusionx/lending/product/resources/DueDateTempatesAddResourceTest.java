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
 * Common Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DueDateTempatesAddResourceTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private DueDateTemplatesAddResource setDueDateTemplatesAddResource() {
		DueDateTemplatesAddResource dueDateTemplatesAddResource = new DueDateTemplatesAddResource();
		dueDateTemplatesAddResource.setCode("RF01");
		dueDateTemplatesAddResource.setName("Test");
		dueDateTemplatesAddResource.setEffectiveDate("1990-01-01");
		dueDateTemplatesAddResource.setDueDateType("START");
		dueDateTemplatesAddResource.setStatus("ACTIVE");
		return dueDateTemplatesAddResource;
	}
	
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource = setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void codeSize() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource = setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void codePattern() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {common.not-null}
     */
    @Test
	public void nameNotNull() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource = setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {common.not-null}
     */
    @Test
	public void statusNotNull() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setStatus(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {common-status.pattern}
     */
    @Test
	public void statusPattern() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setStatus("ABCDEF");
		assertEquals("{common-status.pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
    /**
     * effectiveDate is required.
     * Expected: {common.not-null}
     */
    @Test
	public void effectiveDateNotNull() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setEffectiveDate(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
	/**
     * effectiveDate should be "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" pattern.
     * Expected: {common.invalid-date-pattern}
     */
    @Test
	public void effectiveDatePattern() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setEffectiveDate("1990/SEP/23");
		assertEquals("{common.invalid-date-pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    

	/**
     * dueDateType is required.
     * Expected: {common.not-null}
     */
    @Test
	public void dueDateTypeNotNull() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setDueDateType(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
	/**
     * dueDateType should be START or END or CUSTOMIZED or MULTIPLE.
     * Expected: {due-date-type.pattern}
     */
    @Test
	public void dueDateTypePattern() {
    	DueDateTemplatesAddResource dueDateTemplatesAddResource =setDueDateTemplatesAddResource();
    	dueDateTemplatesAddResource.setDueDateType("ABCDEF");
		assertEquals("{due-date-type.pattern}", TestUtils.getFieldErrorMessageKey(dueDateTemplatesAddResource, localValidatorFactory));
	}
    
    
}
