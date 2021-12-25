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
import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.WaiveOffApprovalGroupService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaiveOffApprovalGroupControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private WaiveOffApprovalGroupService waiveOffApprovalGroupService;
	
	int size = 0;
	int id = 1;
	String name = "name";
	String code = "AA01";
	CommonStatus status = CommonStatus.ACTIVE;
	
	@Before
	public void init() {
		List<WaiveOffApprovalGroup> waiveOffApprovalGroupList = waiveOffApprovalGroupService.findAll();
		size = waiveOffApprovalGroupList.size();
		if(size != 0) {
			for (WaiveOffApprovalGroup waiveOffApprovalGroup : waiveOffApprovalGroupList) {
				id = waiveOffApprovalGroup.getId().intValue();
				name = waiveOffApprovalGroup.getName();
				code = waiveOffApprovalGroup.getCode();
				status = waiveOffApprovalGroup.getStatus();
				
			}
		}
	}
	
	@Test
	public void getAllWaiveOffApprovalGroupDetailsTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalGroupDetailsByIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/" +id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalGroupDetailsByCodeTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalGroupDetailsByStatusTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalGroupDetailsByNamweTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-group/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
}
