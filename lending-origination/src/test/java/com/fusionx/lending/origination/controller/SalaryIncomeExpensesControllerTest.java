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
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.SalaryIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeExpensesRepository;

/**
 * 	Salary Income Expenses Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021   			  	 FXL-784       Nipun Dilhan       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryIncomeExpensesControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private SalaryIncomeExpensesRepository salaryIncomeExpensesRepository;
	
	int size=0;
	int id = 0;
	Long salaryIncomeDetailsId = 1l;
	
	@Before
	public void init() {
		List<SalaryIncomeExpenses> incomeExpensesList = salaryIncomeExpensesRepository.findAll();
        size = incomeExpensesList.size();
        if(size != 0) {
            for (SalaryIncomeExpenses salaryIncomeExpenses : incomeExpensesList) {
            	id = salaryIncomeExpenses.getId().intValue();
            	salaryIncomeDetailsId= salaryIncomeExpenses.getSalaryIncomeDetails().getId();
                break;
            }
        }
	}
	
	
	@Test
    public void getSalaryIncomeExpensesBySalaryIncomeDetailTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-expenses/"+ ConstantsTest.DEFAULT_TENANT_ID+"/salary-income-detail/"+salaryIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-expenses/"+ ConstantsTest.DEFAULT_TENANT_ID+"/salary-income-detail/"+salaryIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryIncomeExpensesByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
