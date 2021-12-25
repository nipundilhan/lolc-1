package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ApprovalCategoryDetailsService;
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
public class ApprovalCategoryDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApprovalCategoryDetailsService approvalCategoryDetailsService;

	private int size = 0;
	private int id = 0;
	private String name = null;
	private String code = null;
	private CommonStatus status = null;

	@Before
	public void init() {
		List<ApprovalCategoryDetails> approvalCategoryDetailsList = approvalCategoryDetailsService.getAll();
		size = approvalCategoryDetailsList.size();
		if (size != 0) {
			for (ApprovalCategoryDetails approvalCategoryDetails : approvalCategoryDetailsList) {
				id = approvalCategoryDetails.getId().intValue();
				name = approvalCategoryDetails.getName();
				code = approvalCategoryDetails.getCode();
				status = approvalCategoryDetails.getStatus();
				break;
			}
		}
	}

	@Test
	public void getApprovalCategoryDetailsTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryDetailsByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryDetailsByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryDetailsByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryDetailsByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
