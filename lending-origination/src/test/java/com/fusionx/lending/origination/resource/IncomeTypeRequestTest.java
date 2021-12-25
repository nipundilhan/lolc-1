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
public class IncomeTypeRequestTest {
	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
	}
	
	private IncomeTypeRequest setIncomeTypeRequest() {
		IncomeTypeRequest incomeTypeRequest = new IncomeTypeRequest();
		incomeTypeRequest.setBusinessTypeId("1");
		incomeTypeRequest.setBusinessTypename("Test");
		incomeTypeRequest.setCode("LOLC");
		incomeTypeRequest.setName("Test");
		incomeTypeRequest.setDescription("Test");
		incomeTypeRequest.setStatus("ACTIVE");
		return incomeTypeRequest;
	}
	
	 /**
     * businessTypeId is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypeIdNotNull() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setBusinessTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * businessTypeId Pattern is "^$|[0-9]+".
     * Expected: {common-numeric.pattern}
     */
    @Test
	public void businessTypeIdPattern() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setBusinessTypeId("A");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
	
    /**
     *  businessTypename is required.
     * Expected: {common.not-null}
     */
    @Test
	public void businessTypenameNotNull() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setBusinessTypename(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * businessTypename cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void businessTypenameSize() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setBusinessTypename("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
		
    }
    
	 /**
     * code is required.
     * Expected: {code.not-null}
     */
    @Test
	public void codeNotNull() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setCode(null);
		assertEquals("{code.not-null}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * code cannot be greater than or less than 4 characters.
     * Expected: {common.code.size}
     */
    @Test
	public void codeSize() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setCode("ABCDEF");
		assertEquals("{common.code.size}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * code should be in "^$|^[a-zA-Z0-9]+$" pattern
     * Expected: {code.pattern}
     */
    @Test
	public void codePattern() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setCode("ABC!");
		assertEquals("{code.pattern}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
	
	 /**
     *  Name is required.
     * Expected: {name.not-null}
     */
    @Test
	public void nameNotNull() {
    	IncomeTypeRequest incomeTypeRequest = setIncomeTypeRequest();
    	incomeTypeRequest.setName(null);
		assertEquals("{name.not-null}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * name cannot be greater than 70 characters.
     * Expected: {common-name.size}
     */
    @Test
    public void nameSize() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setName("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		assertEquals("{common-name.size}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
		
    }
    
	/**
     * status is required.
     * Expected: {status.not-null}
     */
    @Test
	public void statusNotNull() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setStatus(null);
		assertEquals("{status.not-null}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
	/**
     * Status should be ACTIVE or INACTIVE.
     * Expected: {value.pattern}
     */
    @Test
	public void statusPattern() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setStatus("ABCDEF");
		assertEquals("{value.pattern}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
	}
    
    /**
     * description cannot be greater than 350 characters.
     * Expected: {common-description.size}
     */
    @Test
    public void descriptionSize() {
    	IncomeTypeRequest incomeTypeRequest =setIncomeTypeRequest();
    	incomeTypeRequest.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\r\n"
    			+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		assertEquals("{common-description.size}", TestUtils.getFieldErrorMessageKey(incomeTypeRequest, localValidatorFactory));
		
    }
    
   
}
