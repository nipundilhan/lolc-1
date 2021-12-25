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
import com.fusionx.lending.transaction.domain.WaiveOffApprovalUsers;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.WaiveOffApprovalUsersService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaiveOffApprovalUsersControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private WaiveOffApprovalUsersService waiveOffApprovalUsersService;
	
	int size = 0;
	int id = 0;
	CommonStatus status = null;
	String userId = null;
	Long waiveOffApprovalGroupId = 1L;
	
	@Before
	public void init() {
		List<WaiveOffApprovalUsers> waiveOffApprovalUsersDetails = waiveOffApprovalUsersService.findAll();
		size = waiveOffApprovalUsersDetails.size();
		if(size != 0) {
			for (WaiveOffApprovalUsers waiveOffApprovalUsers : waiveOffApprovalUsersDetails) {
				id = waiveOffApprovalUsers.getId().intValue();
				status = waiveOffApprovalUsers.getStatus();
				userId = waiveOffApprovalUsers.getUserId();
				waiveOffApprovalGroupId = waiveOffApprovalUsers.getWaiveOffApprovalGroup().getId();
				break;
			}
		}
	}
	
	@Test
	public void getAllWaiveOffApprovalUserDetailsTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalUserDetailsByIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/" +id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalUserDetailsByStatusTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalUserDetailsByUserIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/user-id/" + userId))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/user-id/" + userId))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffApprovalUserDetailsByWaiveOffGroupIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-approval-group-id/" + waiveOffApprovalGroupId))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-approval-user/" + ConstantsTest.DEFAULT_TENANT_ID + "/waive-off-approval-group-id/" + waiveOffApprovalGroupId))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
}
