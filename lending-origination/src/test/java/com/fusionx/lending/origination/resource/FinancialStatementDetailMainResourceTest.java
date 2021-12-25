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
 * 	Financial Statement Detail Add Resource Test
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
public class FinancialStatementDetailMainResourceTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setUp() {
		localValidatorFactory = new LocalValidatorFactoryBean();
	    localValidatorFactory.setProviderClass(HibernateValidator.class);
	    localValidatorFactory.afterPropertiesSet();
	}
	
	public FinancialStatementDetailMainResource setFinancialStatementDetailMainResource() {
		
		List<FinancialStatementDetailNoteRequest> noteList =  new ArrayList<>();
		FinancialStatementDetailNoteRequest  financialStatementDetailNoteRequest = new FinancialStatementDetailNoteRequest();
		financialStatementDetailNoteRequest.setNoteId("1");
		financialStatementDetailNoteRequest.setItemName("name");
		financialStatementDetailNoteRequest.setDescription("desc");
		financialStatementDetailNoteRequest.setValue("10.00");
		financialStatementDetailNoteRequest.setNoOfItem("1");
		
		noteList.add(financialStatementDetailNoteRequest);
		
		FinancialStatementDetailAddResource financialStatementDetailAddResource = new FinancialStatementDetailAddResource();
		financialStatementDetailAddResource.setFinancailStatementTemplateDetailId("1");
		financialStatementDetailAddResource.setAmount("10.00");
		
		financialStatementDetailAddResource.setNoteList(noteList);
		
		
		List<FinancialStatementDetailAddResource> detailList =  new ArrayList<>();
		detailList.add(financialStatementDetailAddResource);
		
		FinancialStatementDetailMainResource financialStatementDetailMainResource = new FinancialStatementDetailMainResource();
		financialStatementDetailMainResource.setStatementDetailList(detailList);
		financialStatementDetailMainResource.setFinancailStatementTemplateId("1");
			
		return financialStatementDetailMainResource;
		
		
		
	}
	
	
	@Test
    public void templateDetailIdNotNull() {
		FinancialStatementDetailMainResource financialStatementDetailMainResource = setFinancialStatementDetailMainResource();
		financialStatementDetailMainResource.setFinancailStatementTemplateId(null);
		assertEquals("{common.not-null}", TestUtils.getFieldErrorMessageKey(financialStatementDetailMainResource, localValidatorFactory));
    }

	
	@Test
    public void templateDetailIdPattern() {
		FinancialStatementDetailMainResource financialStatementDetailMainResource = setFinancialStatementDetailMainResource();
		financialStatementDetailMainResource.setFinancailStatementTemplateId("ABCD");
		assertEquals("{common-numeric.pattern}", TestUtils.getFieldErrorMessageKey(financialStatementDetailMainResource, localValidatorFactory));
    }

}
