package com.fusionx.lending.origination.controller;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

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

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.OtherIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.OtherIncomeExpensesService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeExpensesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private OtherIncomeExpensesService otherIncomeExpensesService;

	@Autowired
	public void setOtherIncomeExpensesService(OtherIncomeExpensesService otherIncomeExpensesService) {
		this.otherIncomeExpensesService = otherIncomeExpensesService;
	}

	int size = 0;
	int id = 0;
	CommonStatus status = CommonStatus.ACTIVE;
	Long otherIncomeDetailsId = 1l;

	@Before
	public void init() {
		List<OtherIncomeExpenses> otherIncomeExpenses = otherIncomeExpensesService.findAll();
		size = otherIncomeExpenses.size();
		if (size != 0) {
			for (OtherIncomeExpenses otherIncomeExpense : otherIncomeExpenses) {
				id = otherIncomeExpense.getId().intValue();
				status = otherIncomeExpense.getStatus();
				otherIncomeDetailsId = otherIncomeExpense.getOtherIncomeDetailsId().getId();
				break;
			}
		}
	}

	@Test
	public void getAllOtherIncomeExpensesTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getOtherIncomeExpensesByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getOtherIncomeExpensesByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

//	@Test
//	public void getByOtherIncomeDetailsIdTest() throws Exception {
//		if (size != 0) {
//			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/other-income-details/"
//					+ otherIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//					.andExpect(status().isOk());
//		} else {
//			mockMvc.perform(get("/other-income-expenses/" + ConstantsTest.DEFAULT_TENANT_ID + "/other-income-details/"
//					+ otherIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//					.andExpect(status().isNoContent());
//		}
//	}

}
