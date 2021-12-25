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
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
/**
 * 	Income Expense Summary Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-115  	 FXL-786       Piyumi     Created
 *    
 ********************************************************************************************************
*/


//@RunWith(SpringRunner.class)
//@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeExpenseSummaryControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	int custSize=0;
	int leadSize=0;
	Long customerId = 0l;
	Long leadId = 0l;

	@Before
	public void init() {
		List<Customer> custList = customerRepository.findAll();		
		List<LeadInfo> leadList = leadInfoRepository.findAll();
        custSize = custList.size();
        if(custSize != 0) {
            for (Customer cust : custList) {
            	customerId = cust.getId();
                break;
            }
        }
        
        leadSize = leadList.size();
        if(leadSize != 0) {
            for (LeadInfo lead : leadList) {
            	leadId = lead.getId();
                break;
            }
        }
	}
	
	//@Test
    public void getIncomeExpenseSummaryByCustomerIdTest() throws Exception {
        if(custSize != 0 ) {
            mockMvc.perform(get("/income-expense-summary/"+ ConstantsTest.DEFAULT_TENANT_ID+"/customer"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-expense-summary/"+ ConstantsTest.DEFAULT_TENANT_ID+"/customer"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getIncomeExpenseSummaryByLeadIdTest() throws Exception {
        if(leadSize != 0 ) {
            mockMvc.perform(get("/income-expense-summary/"+ConstantsTest.DEFAULT_TENANT_ID+"/lead"+leadId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-expense-summary/"+ConstantsTest.DEFAULT_TENANT_ID+"/lead"+leadId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	

}
