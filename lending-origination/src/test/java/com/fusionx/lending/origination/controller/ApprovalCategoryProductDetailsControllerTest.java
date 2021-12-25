package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ApprovalCategoryProductDetailsService;
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
public class ApprovalCategoryProductDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApprovalCategoryProductDetailsService approvalCategoryProductDetailsService;

	private int size = 0;
	private int id = 0;
	private String name = null;
	private String code = null;
	private CommonStatus status = null;

	@Before
	public void init() {
		List<ApprovalCategoryProductDetails> approvalCategoryProductDetailsList = approvalCategoryProductDetailsService
				.getAll();
		size = approvalCategoryProductDetailsList.size();
		if (size != 0) {
			for (ApprovalCategoryProductDetails approvalCategoryProductDetails : approvalCategoryProductDetailsList) {
				id = approvalCategoryProductDetails.getId().intValue();
				name = approvalCategoryProductDetails.getName();
				code = approvalCategoryProductDetails.getCode();
				status = approvalCategoryProductDetails.getStatus();
				break;
			}
		}
	}

	@Test
	public void getApprovalCategoryProductDetailsTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryProductDetailsByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryProductDetailsByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryProductDetailsByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getApprovalCategoryProductDetailsByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(
					get("/approval-category-product-details/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
