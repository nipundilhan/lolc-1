package com.fusionx.lending.origination.controller;

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
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.FinancialStatementTemplateService;

/**
 * 	Financial Statement Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-338  	 FXL-655       Dilki        Created
 *    
 ********************************************************************************************************
*/

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FinancialStatementControllerTest {
/*
	@Autowired
	private MockMvc mockMvc;

	private FinancialStatementTemplateService financialStatementService;

	@Autowired
	public void setFinancialStatementService(FinancialStatementTemplateService financialStatementService) {
		this.financialStatementService = financialStatementService;
	}

	int size = 0;
	int id = 0;
	String name = "Test";
	String code = "TEST";
	CommonStatus status = CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<FinancialStatementTemplate> financialStatements = financialStatementService.getAll();
		size = financialStatements.size();
		if (size != 0) {
			for (FinancialStatementTemplate financialStatement : financialStatements) {
				id = financialStatement.getId().intValue();
				name = financialStatement.getName();
				code = financialStatement.getCode();
				status = financialStatement.getStatus();
				break;
			}
		}
	}

	@Test
	public void getAllFinancialStatementTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getFinancialStatementByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getFinancialStatementByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getFinancialStatementByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getFinancialStatementByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/financial-statement-template/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}
*/
}
