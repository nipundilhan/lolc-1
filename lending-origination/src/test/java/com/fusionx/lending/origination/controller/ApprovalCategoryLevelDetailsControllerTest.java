package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.ApprovalCategoryLevelDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ApprovalCategoryLevelDetailsService;
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
/**
 * Approval Category Level Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-840 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApprovalCategoryLevelDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApprovalCategoryLevelDetailsService approvalCategoryLevelDetailsService;

	private int size = 0;
	private int id = 0;
	private String name = null;
	private String code = null;
	private CommonStatus status = null;

	@Before
	public void init() {
		List<ApprovalCategoryLevelDetails> approvalCategoryLevelDetailsList = approvalCategoryLevelDetailsService
				.getAll();
		size = approvalCategoryLevelDetailsList.size();
		if (size != 0) {
			for (ApprovalCategoryLevelDetails approvalCategoryLevelDetails : approvalCategoryLevelDetailsList) {
				id = approvalCategoryLevelDetails.getId().intValue();
				name = approvalCategoryLevelDetails.getName();
				code = approvalCategoryLevelDetails.getCode();
				status = approvalCategoryLevelDetails.getStatus();
				break;
			}
		}
	}

	@Test
	public void getApprovalCategoryLevelDetailsTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryLevelDetailsByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryLevelDetailsByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryLevelDetailsByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryLevelDetailsByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-level-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
