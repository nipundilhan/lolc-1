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
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.IncomeSourceDetailsService;
/**
 * 	Income Source Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeSourceDetailsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private IncomeSourceDetailsService incomeSourceDetailsService;
	
	@Autowired
	public void setIncomeSourceDetailsService(IncomeSourceDetailsService incomeSourceDetailsService) {
		this.incomeSourceDetailsService = incomeSourceDetailsService;
	}
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<IncomeSourceDetails> incomeSourceDetails = incomeSourceDetailsService.findAll();
        size = incomeSourceDetails.size();
        if(size != 0) {
            for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetails) {
            	id = incomeSourceDetail.getId().intValue();
            	status = incomeSourceDetail.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllIncomeSourceDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-source-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-source-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeSourceDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-source-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-source-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getIncomeSourceDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-source-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-source-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
