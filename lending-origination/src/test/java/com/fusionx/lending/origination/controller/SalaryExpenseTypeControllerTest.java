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
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.SalaryExpenseTypeService;
/**
 * Salary Expense Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *     1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryExpenseTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private SalaryExpenseTypeService salaryExpenseTypeService;
	
	@Autowired
	public void setSalaryExpenseTypeService(SalaryExpenseTypeService salaryExpenseTypeService) {
		this.salaryExpenseTypeService = salaryExpenseTypeService;
	}
	
	int size=0;
	int id = 0;
	String code = "AAAA";
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<SalaryExpenseType> salaryExceptionTypes = salaryExpenseTypeService.findAll();
        size = salaryExceptionTypes.size();
        if(size != 0) {
            for (SalaryExpenseType salaryExceptionType : salaryExceptionTypes) {
            	id = salaryExceptionType.getId().intValue();
            	code = salaryExceptionType.getExpenseTypesCode();
            	status= salaryExceptionType.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-expense-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-expense-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryExpenseTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryExpenseTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getSalaryExpenseTypeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-expense-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
