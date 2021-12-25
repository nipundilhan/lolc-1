package com.fusionx.lending.origination.resource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
 * 	Financial Statement Detail update Resource Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   25-09-2021   			 	 		       Nipun       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FinancialStatementDetailUpdateResourceTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	public FinancialStatementDetailUpdateResource setFinancialStatementDetailUpdateResource() {
		
		FinancialStatementDetailUpdateResource financialStatementDetailUpdateResource = new FinancialStatementDetailUpdateResource();
		financialStatementDetailUpdateResource.setFinancailStatementTemplateDetailId("1");
		financialStatementDetailUpdateResource.setAmount("10.00");
		financialStatementDetailUpdateResource.setFinancailStatementDetailId("1");
		financialStatementDetailUpdateResource.setVersion("0");
		return financialStatementDetailUpdateResource;
			
	}
	
	@Test
    public void templateDetailIdNotNull() {
		FinancialStatementDetailUpdateResource financialStatementDetailUpdateResource = setFinancialStatementDetailUpdateResource();
		financialStatementDetailUpdateResource.setFinancailStatementTemplateDetailId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financialStatementDetailUpdateResource, localValidatorFactory));
    }

	
	@Test
    public void templateDetailIdPattern() {
		FinancialStatementDetailUpdateResource financialStatementDetailUpdateResource = setFinancialStatementDetailUpdateResource();
		financialStatementDetailUpdateResource.setFinancailStatementTemplateDetailId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailUpdateResource, localValidatorFactory));
    }
	
	
	@Test
    public void detailIdPattern() {
		FinancialStatementDetailUpdateResource financialStatementDetailUpdateResource = setFinancialStatementDetailUpdateResource();
		financialStatementDetailUpdateResource.setFinancailStatementDetailId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailUpdateResource, localValidatorFactory));
    }
	
	@Test
    public void amountPattern() {
		FinancialStatementDetailUpdateResource financialStatementDetailUpdateResource = setFinancialStatementDetailUpdateResource();
		financialStatementDetailUpdateResource.setAmount("ABCD");
		assertEquals("{common-amount.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailUpdateResource, localValidatorFactory));
    }

}
