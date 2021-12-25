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
 * Other Income Category Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639          FXL-535      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateIncomeTypeRequestTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private UpdateIncomeTypeRequest setUpdateIncomeTypeRequest() {
		UpdateIncomeTypeRequest updateIncomeTypeRequest = new UpdateIncomeTypeRequest();
		updateIncomeTypeRequest.setBusinessTypeId("1");
		updateIncomeTypeRequest.setBusinessTypename("Test");
		updateIncomeTypeRequest.setCode("LOLC");
		updateIncomeTypeRequest.setName("Test");
		updateIncomeTypeRequest.setDescription("Test");
		updateIncomeTypeRequest.setStatus("ACTIVE");
		updateIncomeTypeRequest.setVersion("1");
		return updateIncomeTypeRequest;
	}
	
	 /**
     * businessTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeIdNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setBusinessTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessTypeIdPattern() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setBusinessTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
	
    /**
     *  businessTypename is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypenameNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setBusinessTypename(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * businessTypename cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void businessTypenameSize() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setBusinessTypename("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
		
    }
    
	 /**
     * code is required.
     * Expected: {common.not-null}
     */
    @Test
	public void codeNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setCode(null);
		assertEquals("{code.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common.code.size}
     */
    @Test
	public void codeSize() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setCode("ABCDEF");
		assertEquals("{common.code.size}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {code.pattern}
     */
    @Test
	public void codePattern() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setCode("ABC!");
		assertEquals("{code.pattern}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {name.not-null}
     */
    @Test
	public void nameNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setName(null);
		assertEquals("{name.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {status.not-null}
     */
    @Test
	public void statusNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setStatus(null);
		assertEquals("{status.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {value.pattern}
     */
    @Test
	public void statusPattern() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setStatus("ABCDEF");
		assertEquals("{value.pattern}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
		
    }
    
    /**
     * version is required.
     * Expected: {version.not-null}
     */
    @Test
	public void versionNotNull() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest = setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setVersion(null);
		assertEquals("{version.not-null}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * version should be in "^$|[0-9]+" pattern
     * Expected: {version.pattern}
     */
    @Test
	public void versionPattern() {
    	UpdateIncomeTypeRequest updateIncomeTypeRequest =setUpdateIncomeTypeRequest();
    	updateIncomeTypeRequest.setVersion("A");
		assertEquals("{version.pattern}", TestUtils.getFieldErrorMessageKey(updateIncomeTypeRequest, localValidatorFactory));
	}


}
