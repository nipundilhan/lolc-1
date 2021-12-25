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
import com.fusionx.lending.transaction.domain.WaiveOffApprovalTypes;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.WaiveOffApprovalTypesService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaiveOffApprovalTypesControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private WaiveOffApprovalTypesService waiveOffApprovalTypesService;
	
	int size = 0;
	int id = 0;
	CommonStatus status = null;
	long waiveOffType = 1;
	long waiveOffApprovalGroup = 1;
	
	@Before
	public void init() {
		List<WaiveOffApprovalTypes> waiveOffApprovalTypesDetails = waiveOffApprovalTypesService.findAll();
		size = waiveOffApprovalTypesDetails.size();
		if(size != 0) {
			for (WaiveOffApprovalTypes waiveOffApprovalTypes : waiveOffApprovalTypesDetails) {
				id = waiveOffApprovalTypes.getId().intValue();
				status = waiveOffApprovalTypes.getStatus();
				waiveOffType = waiveOffApprovalTypes.getWaiveOffType().getId();
				waiveOffApprovalGroup = waiveOffApprovalTypes.getWaiveOffApprovalGroup().getId();
				break;
			}
		}
	}
	
	@Test
	public void getAllWaiveOffApprovalTypeDetailsTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalTypeDetailsByIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" +id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalTypeDetailsByStatusTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalTypeDetailsByWaiveOffTypeIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-type-id/" + waiveOffType))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-type-id/" + waiveOffType))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalTypeDetailsByWaiveOffApprovalGroupIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-approval-group-id/" + waiveOffApprovalGroup))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-approval-group-id/" + waiveOffApprovalGroup))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
}
