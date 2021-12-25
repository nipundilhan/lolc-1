package com.fusionx.lending.origination.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.h2.schema.Constant;
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
import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.BusinessAdditionalDetailsRepository;
import com.fusionx.lending.origination.service.BusinessAdditionalDetailsService;
/**
 * 	Business Additional Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessAdditionalDetailsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private BusinessAdditionalDetailsRepository businessAdditionalDetailsRepository;
	
	@Autowired
	public void setBusinessAdditionalDetailsRepository(BusinessAdditionalDetailsRepository businessAdditionalDetailsRepository) {
		this.businessAdditionalDetailsRepository = businessAdditionalDetailsRepository;
	}
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<BusinessAdditionalDetails> businessAdditionalDetails = businessAdditionalDetailsRepository.findAll();
        size = businessAdditionalDetails.size();
        if(size != 0) {
            for (BusinessAdditionalDetails businessAdditionalDetail : businessAdditionalDetails) {
            	id = businessAdditionalDetail.getId().intValue();
            	status = businessAdditionalDetail.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllBusinessAdditionalDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-additional-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().is5xxServerError());
        }else {
            mockMvc.perform(get("/business-additional-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().is5xxServerError());
        }
    }
	
	/*@Test
    public void getBusinessAdditionalDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-additional-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-additional-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
	
	
	
	/*@Test
    public void getBusinessAdditionalDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-additional-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-additional-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/


}
