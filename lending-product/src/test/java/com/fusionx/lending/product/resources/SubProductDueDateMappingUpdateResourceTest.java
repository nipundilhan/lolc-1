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
 * Sub Product Due Date Mapping Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021    FXL-155  	 FXL-933	Piyumi      Created
 *    
 ********************************************************************************************************
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubProductDueDateMappingUpdateResourceTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private SubProductDueDateMappingUpdateResource setSubProductDueDateMappingUpdateResource() {
		SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource = new SubProductDueDateMappingUpdateResource();
		subProductDueDateMappingUpdateResource.setDueDateTemplateId("1");
		subProductDueDateMappingUpdateResource.setDueDateTemplateCode("LOLC");
		subProductDueDateMappingUpdateResource.setRemark("Remark");
		return subProductDueDateMappingUpdateResource;
	}
	
	/**
     * dueDateTemplateId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void dueDateTemplateIdNotNull() {
    	SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource =setSubProductDueDateMappingUpdateResource();
    	subProductDueDateMappingUpdateResource.setDueDateTemplateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(subProductDueDateMappingUpdateResource, localValidatorFactory));
	}
    
	/**
     * dueDateTemplateId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void dueDateTemplateIdPattern() {
    	SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource =setSubProductDueDateMappingUpdateResource();
    	subProductDueDateMappingUpdateResource.setDueDateTemplateId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(subProductDueDateMappingUpdateResource, localValidatorFactory));
	}
	
	 /**
     * dueDateTemplateCode is required.
     * Expected: {common.not-null}
     */
    @Test
	public void dueDateTemplateCodeNotNull() {
    	SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource = setSubProductDueDateMappingUpdateResource();
    	subProductDueDateMappingUpdateResource.setDueDateTemplateCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(subProductDueDateMappingUpdateResource, localValidatorFactory));
	}
    
    /**
     * dueDateTemplateCode cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void dueDateTemplateCodeSize() {
    	SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource = setSubProductDueDateMappingUpdateResource();
    	subProductDueDateMappingUpdateResource.setDueDateTemplateCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(subProductDueDateMappingUpdateResource, localValidatorFactory));
	}
    
    /**
     * dueDateTemplateCode should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void dueDateTemplateCodePattern() {
    	SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource =setSubProductDueDateMappingUpdateResource();
    	subProductDueDateMappingUpdateResource.setDueDateTemplateCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(subProductDueDateMappingUpdateResource, localValidatorFactory));
	}
    
	
    
    
}
