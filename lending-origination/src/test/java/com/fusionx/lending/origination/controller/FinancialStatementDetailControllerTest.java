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
import com.fusionx.lending.origination.domain.FinancialStatement;
import com.fusionx.lending.origination.domain.FinancialStatementDetail;
import com.fusionx.lending.origination.domain.FinancialStatementDetailNote;
import com.fusionx.lending.origination.repository.FinancialStatementDetailNoteRepository;
import com.fusionx.lending.origination.repository.FinancialStatementDetailRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FinancialStatementDetailControllerTest {
	
	
//	@Autowired
//    private MockMvc mockMvc;
//	
//	int size=0;
//	Long financialStatementId = 0l;
//	Long financialStatementDetailId = 0l;
//	String status="ACTIVE";
//	boolean findNote = false;
//	
//	@Autowired
//	private FinancialStatementDetailRepository financialStatementDetailRepository;
//	@Autowired
//	private FinancialStatementDetailNoteRepository financialStatementDetailNoteRepository;
//	
//	@Before
//	public void init() {
//		List<FinancialStatementDetail> list = financialStatementDetailRepository.findAll();
//		size = list.size();
//        if(size != 0) {
//            for (FinancialStatementDetail financialStatementDetail : list) {
//            	financialStatementDetailId =  financialStatementDetail.getId();
//            	financialStatementId = financialStatementDetail.getFinancialStatement().getId();
//                break;
//            }
//        }
//        
//        List<FinancialStatementDetailNote> detailNoteList = financialStatementDetailNoteRepository.findAll();
//        if(!detailNoteList.isEmpty()) {
//            for (FinancialStatementDetailNote note : detailNoteList) {
//            	findNote = true;
//            	financialStatementDetailId =  note.getFinancialStatementDetail().getId();
//                break;
//            }
//        }
//        
//        
//	}
//	
//	
//	@Test
//    public void getByfinancialStatementIdTest() throws Exception {
//        if(size != 0) {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/financial-statement/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//        }else {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/financial-statement/"+financialStatementId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
//    }
//
//	
//	
//	@Test
//    public void getByIdTest() throws Exception {
//        if(size != 0) {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+financialStatementDetailId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//        }else {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+financialStatementDetailId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
//    }
//	
//	
//	@Test
//    public void getAllTest() throws Exception {
//        if(size != 0) {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//        }else {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
//    }
//	
//	
//	@Test
//    public void getDocumentsByFinalcialStatemetIdTest() throws Exception {
//        if(findNote) {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/notes/statement-detail/"+financialStatementDetailId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//        }else {
//            mockMvc.perform(get("/financial-statement-detail/"+ConstantsTest.DEFAULT_TENANT_ID+"/notes/statement-detail/"+financialStatementDetailId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
//    }

}
