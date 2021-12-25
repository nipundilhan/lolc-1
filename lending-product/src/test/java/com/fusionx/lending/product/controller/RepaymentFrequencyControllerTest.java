package com.fusionx.lending.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fusionx.lending.product.ConstantsTest;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.RepaymentFrequencyService;

/**
 * Repayment Frequency Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021   FXL-1   		 FXL-2	    Dilki        Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepaymentFrequencyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RepaymentFrequencyService repaymentFrequencyService;

	int size = 0;
	int id = 0;
	CommonStatus status = null;
	String name, code = null;

	@Before
	public void init() {
		List<RepaymentFrequency> repaymentFrequency = repaymentFrequencyService.getAll();
		size = repaymentFrequency.size();
		if (size != 0) {
			for (RepaymentFrequency repaymentFrequencyRec : repaymentFrequency) {
				id = repaymentFrequencyRec.getId().intValue();
				status = repaymentFrequencyRec.getStatus();
				code = repaymentFrequencyRec.getCode();
				name = repaymentFrequencyRec.getName();
				break;
			}
		}
	}

	@Test
	public void getAllRepaymentFrequencyTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getRepaymentFrequencyByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getRepaymentFrequencyByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getRepaymentFrequencyByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getRepaymentFrequencyByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/repayment-frequency/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
