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
 * 	Document Add Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-09-2021   			 	 		       Nipun       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FinanceStatementAddResourceTest {

	
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	private FinanceStatementAddRequest setFinanceStatementAddResource() {
		FinanceStatementAddRequest financeStatementAddRequest = new FinanceStatementAddRequest();
		financeStatementAddRequest.setCustomerId("1");
		financeStatementAddRequest.setStatementTemplateId("1");
		financeStatementAddRequest.setStatementTemplateName("templateName");
		financeStatementAddRequest.setStatementTypeId("1");
		financeStatementAddRequest.setStatementTypeName("typeName");
		financeStatementAddRequest.setAuditByUserName("user");
		financeStatementAddRequest.setFromDate("2021-08-12");
		financeStatementAddRequest.setToDate("2021-09-12");
		financeStatementAddRequest.setNoOfReports("10");
		return financeStatementAddRequest;
		
	}
	
	@Test
    public void customerNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setCustomerId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	

    @Test
    public void customerPattern() {
    	FinanceStatementAddRequest financeStatementAddRequest =  setFinanceStatementAddResource();
    	financeStatementAddRequest.setCustomerId("ABC");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	
	@Test
    public void templateNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setStatementTemplateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	

    @Test
    public void templatePattern() {
    	FinanceStatementAddRequest financeStatementAddRequest =  setFinanceStatementAddResource();
    	financeStatementAddRequest.setStatementTemplateId("ABC");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	@Test
    public void templateNameNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setStatementTemplateName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	@Test
    public void typeNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setStatementTypeId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	

    @Test
    public void typePattern() {
    	FinanceStatementAddRequest financeStatementAddRequest =  setFinanceStatementAddResource();
    	financeStatementAddRequest.setStatementTypeId("ABC");
		assertEquals("{common.invalid-number-format}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	@Test
    public void typeNameNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setStatementTypeName(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	@Test
    public void fromDateNotNull() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setFromDate(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	
	@Test
    public void fromDatePatter() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setFromDate("1232");
		assertEquals("{date.pattern}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	
	
	
	@Test
    public void noOFPattern() {
		FinanceStatementAddRequest financeStatementAddRequest = setFinanceStatementAddResource();
		financeStatementAddRequest.setNoOfReports("acbc");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financeStatementAddRequest, localValidatorFactory));
    }
	
	
	
}
