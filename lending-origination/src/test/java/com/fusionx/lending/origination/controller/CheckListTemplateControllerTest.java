package com.fusionx.lending.origination.controller;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.CheckListTemplateService;
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

import java.util.List;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckListTemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CheckListTemplateService checkListTemplateService;

	private int size = 0;
	private int id = 0;
	private String name = null;
	private String code = null;
	private CommonStatus status = null;

//	@Before
	public void init() {
		List<CheckListTemplate> checkListTemplateList = checkListTemplateService.getAll();
		size = checkListTemplateList.size();
		if (size != 0) {
			for (CheckListTemplate checkListTemplate : checkListTemplateList) {
				id = checkListTemplate.getId().intValue();
				name = checkListTemplate.getName();
				code = checkListTemplate.getCode();
				status = checkListTemplate.getStatus();
				break;
			}
		}
	}

//	@Test
	public void getCheckListTemplateTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getCheckListTemplateByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

//	@Test
	public void getCheckListTemplateByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

//	@Test
	public void getCheckListTemplateByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

//	@Test
	public void getCheckListTemplateByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
