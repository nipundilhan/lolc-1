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
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.service.WaiveOffTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WaiveOffTypeControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private WaiveOffTypeService waiveOffTypeService; 
	
	int size = 0;
	int id = 0;
	String name = null;
	String description = null;
	String code = null;
	CommonStatus status = CommonStatus.ACTIVE;
	Long creditNoteTypeId = 1L;
	
	@Before
	public void init() {
		List<WaiveOffType> waiveOffTypeDetails = waiveOffTypeService.findAll();
		size = waiveOffTypeDetails.size();
		if(size != 0) {
			for (WaiveOffType waiveOffType : waiveOffTypeDetails) {
				id = waiveOffType.getId().intValue();
				name = waiveOffType.getName();
				description = waiveOffType.getDescription();
				code = waiveOffType.getCode();
				status = waiveOffType.getStatus();
				creditNoteTypeId = waiveOffType.getCreditNoteType().getId();
				break;
			}
		}
	}
	
	@Test
	public void getAllWaiveOffTypeDetailsTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTypeByIdTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTypeByCodeTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTypeByNameTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
	@Test
	public void getWaiveOffTypeByStatusTest() throws Exception {
		if(size != 0) {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk());
		} else {
			mockMvc.perform(get("/waive-off-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isNoContent());
		}
	}
	
}
