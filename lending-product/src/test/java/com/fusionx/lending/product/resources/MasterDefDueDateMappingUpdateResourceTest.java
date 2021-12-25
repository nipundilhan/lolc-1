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
 * MasterDef Due Date Mapping Update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021    FXL-680  	 FXL-924	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDefDueDateMappingUpdateResourceTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private MasterDefDueDateMappingUpdateResource setMasterDefDueDateMappingUpdateResource() {
		MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource = new MasterDefDueDateMappingUpdateResource();
		masterDefDueDateMappingUpdateResource.setDueDateTemplateId("1");
		masterDefDueDateMappingUpdateResource.setDueDateTemplateCode("LOLC");
		masterDefDueDateMappingUpdateResource.setRemark("Remark");
		return masterDefDueDateMappingUpdateResource;
	}
	
	/**
     * dueDateTemplateId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void dueDateTemplateIdNotNull() {
    	MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource =setMasterDefDueDateMappingUpdateResource();
    	masterDefDueDateMappingUpdateResource.setDueDateTemplateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDueDateMappingUpdateResource, localValidatorFactory));
	}
    
	/**
     * dueDateTemplateId should be in "^$|[0-9]+" pattern.
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void dueDateTemplateIdPattern() {
    	MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource =setMasterDefDueDateMappingUpdateResource();
    	masterDefDueDateMappingUpdateResource.setDueDateTemplateId("ABCDEF");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(masterDefDueDateMappingUpdateResource, localValidatorFactory));
	}
	
	 /**
     * dueDateTemplateCode is required.
     * Expected: {common.not-null}
     */
    @Test
	public void dueDateTemplateCodeNotNull() {
    	MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource = setMasterDefDueDateMappingUpdateResource();
    	masterDefDueDateMappingUpdateResource.setDueDateTemplateCode(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(masterDefDueDateMappingUpdateResource, localValidatorFactory));
	}
    
    /**
     * dueDateTemplateCode cannot be greater than or less than 4 characters.
     * Expected: {common-code.size}
     */
    @Test
	public void dueDateTemplateCodeSize() {
    	MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource = setMasterDefDueDateMappingUpdateResource();
    	masterDefDueDateMappingUpdateResource.setDueDateTemplateCode("ABCDEF");
		assertEquals("{common-code.size}", TestUtils.getFieldErrorMessageKey(masterDefDueDateMappingUpdateResource, localValidatorFactory));
	}
    
    /**
     * dueDateTemplateCode should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {common.code-pattern}
     */
    @Test
	public void dueDateTemplateCodePattern() {
    	MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource =setMasterDefDueDateMappingUpdateResource();
    	masterDefDueDateMappingUpdateResource.setDueDateTemplateCode("ABC!");
		assertEquals("{common.code-pattern}", TestUtils.getFieldErrorMessageKey(masterDefDueDateMappingUpdateResource, localValidatorFactory));
	}
    
	
    
    
}
