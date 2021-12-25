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
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.SalaryIncomeDetailsRepository;
/**
 * 	Salary Income Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalaryIncomeDetailsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private SalaryIncomeDetailsRepository salaryIncomeDetailsRepository;
	
	@Autowired
	public void setSalaryIncomeDetailsRepository(SalaryIncomeDetailsRepository salaryIncomeDetailsRepository) {
		this.salaryIncomeDetailsRepository = salaryIncomeDetailsRepository;
	}
	
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;
	Long incomeSourceDetailsId = 1l;

	@Before
	public void init() {
		List<SalaryIncomeDetails> salaryIncomeDetails = salaryIncomeDetailsRepository.findAll();
        size = salaryIncomeDetails.size();
        if(size != 0) {
            for (SalaryIncomeDetails salaryIncomeDetail : salaryIncomeDetails) {
            	id = salaryIncomeDetail.getId().intValue();
            	status = salaryIncomeDetail.getStatus();
            	incomeSourceDetailsId = salaryIncomeDetail.getIncomeSourceDetail().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAllSalaryIncomeDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryIncomeDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getSalaryIncomeDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryIncomeDetailsByIncomeSourceDetailsIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/income-source-details/"+incomeSourceDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/salary-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/income-source-details/"+incomeSourceDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
