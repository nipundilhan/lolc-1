package com.fusionx.lending.origination.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDocument;
import com.fusionx.lending.origination.repository.FinancialStatementDocumentRepository;
import com.fusionx.lending.origination.repository.FinancialStatementRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FinanceStatementControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	int size=0;
	Long customerId = 0l;
	Long financialStatementId = 0l;
	String status="ACTIVE";
	boolean findDoc = false;
	
	@Autowired
	private FinancialStatementRepository financialStatementRepository;
	@Autowired
	private FinancialStatementDocumentRepository financialStatementDocumentRepository;
	
	@Before
	public void init() {
		List<FinancialStatement> list = financialStatementRepository.findAll();
		size = list.size();
        if(size != 0) {
            for (FinancialStatement financialStatement : list) {
            	financialStatementId =  financialStatement.getId();
            	customerId = financialStatement.getCustomer().getId();
                break;
            }
        }
        
        List<FinancialStatementDocument> docList = financialStatementDocumentRepository.findAll();
        if(!docList.isEmpty()) {
            for (FinancialStatementDocument doc : docList) {
            	financialStatementId =  doc.getFinancialStatement().getId();
            	findDoc = true;
                break;
            }
        }
	}
	
	
	@Test
    public void getByCustomerIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

	
	
	@Test
    public void getByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAllTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getDocumentsByFinalcialStatemetIdTest() throws Exception {
        if(findDoc) {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/documents/financial-statement/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/financial-statement/"+ConstantsTest.DEFAULT_TENANT_ID+"/documents/financial-statement/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
