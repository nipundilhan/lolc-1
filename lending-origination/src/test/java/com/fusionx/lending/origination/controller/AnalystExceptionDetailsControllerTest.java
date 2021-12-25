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
import com.fusionx.lending.origination.domain.AnalystDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.AnalystDetailsRepository;
import com.fusionx.lending.origination.service.AnalystExceptionDetailsService;
/**
 * 	Analyst Exception Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-08-2021   FXL-117  	 FXL-654       Piyumi     Created
 *    
 ********************************************************************************************************
*/


/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class AnalystExceptionDetailsControllerTest {
	/*
	@Autowired
    private MockMvc mockMvc;
	
	private AnalystExceptionDetailsService analystExceptionDetailsService;
	
	@Autowired
	public void setAnalystExceptionDetailsService(AnalystExceptionDetailsService analystExceptionDetailsService) {
		this.analystExceptionDetailsService = analystExceptionDetailsService;
	}
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<AnalystDetails> analystDetails = analystExceptionDetailsService.findAll(ConstantsTest.DEFAULT_TENANT_ID);
        size = analystDetails.size();
        if(size != 0) {
            for (AnalystDetails analystDetail : analystDetails) {
            	id = analystDetail.getId().intValue();
            	status= analystDetail.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllAnalystExceptionDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/analyst-exception/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/analyst-exception/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getAnalystDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/analyst-exception/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/analyst-exception/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getAnalystDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/analyst-exception/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/analyst-exception/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/

}
