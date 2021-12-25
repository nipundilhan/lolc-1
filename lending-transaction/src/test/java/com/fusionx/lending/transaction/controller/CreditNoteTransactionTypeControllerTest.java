package com.fusionx.lending.transaction.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fusionx.casa.transaction.ConstantsTest;
import com.fusionx.lending.transaction.domain.CreditNoteTransactionType;
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.CreditNoteTransactionTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditNoteTransactionTypeControllerTest {
	
	int size = 0;
	int id = 0;
	String name = null;
	String description = null;
	String code = null;
	CommonStatus status = CommonStatus.ACTIVE;
	Long creditNoteTypeId = 1L;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private CreditNoteTransactionTypeService creditNoteTransactionTypeService;
	
	
	@Before
	public void init() {
		List<CreditNoteTransactionType> list = creditNoteTransactionTypeService.getAll();
		size = list.size();
		if(size != 0) {
			for (CreditNoteTransactionType creditNoteTransactionType : list) {
				id = creditNoteTransactionType.getId().intValue();
				status = creditNoteTransactionType.getStatus();
				creditNoteTypeId = creditNoteTransactionType.getCreditNoteType().getId();
				break;
			}
		}
	}
	
	
	@Test
	public void getAllCreditNoteTransactionTypeTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getCreditNoteTransactionTypeIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	

	
	@Test
	public void getCreditNoteTransactionTypeStatusTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}

	
	@Test
	public void getByCreditNoteTypeIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/credit-note-type/" + creditNoteTypeId))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/credit-note-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/credit-note-type/" + creditNoteTypeId))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}

}
