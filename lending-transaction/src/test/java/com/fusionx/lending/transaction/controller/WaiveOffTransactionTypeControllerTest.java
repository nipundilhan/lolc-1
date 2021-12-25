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
import com.fusionx.lending.transaction.domain.WaiveOffTransactionType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.WaiveOffTransactionTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaiveOffTransactionTypeControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private WaiveOffTransactionTypeService waiveOffTransactionTypeService;
	
	int size = 0;
	int id = 0;
	CommonStatus status = CommonStatus.ACTIVE;
	Long waiveOffType = 1L;
	
	@Before
	public void init() {
		List<WaiveOffTransactionType> waiveOffTransactionTypeDetails = waiveOffTransactionTypeService.findAll();
		size = waiveOffTransactionTypeDetails.size();
		if(size != 0) {
			for (WaiveOffTransactionType waiveOffTransactionType : waiveOffTransactionTypeDetails) {
				id = waiveOffTransactionType.getId().intValue();
				status = waiveOffTransactionType.getStatus();
				waiveOffType = waiveOffTransactionType.getWaiveOffType().getId();
				break;
			}
		}
	}
	
	@Test
	public void getAllWaiveOffTransactionTypeDetailsTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTransactionTypeById() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTransactionTypeByStatus() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTransactionTypeByWaiveOffTypeId() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-type/" + waiveOffType))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-transaction-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-type/" + waiveOffType))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
}
