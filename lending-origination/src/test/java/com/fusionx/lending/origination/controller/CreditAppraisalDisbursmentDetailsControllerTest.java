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
import com.fusionx.lending.origination.domain.CreditAppraisalDisbursmentDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.CreditAppraisalDisbursmentDetailsService;




/**
 * CreditAppraisalDisbursmentDetails Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-10-2021    FXL-121      FXL-1005    Pasindu       Created
 *    
 ********************************************************************************************************
 */


//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditAppraisalDisbursmentDetailsControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private CreditAppraisalDisbursmentDetailsService creditAppraisalDisbursmentDetailsService;
	
	int size=0;
	int id = 0;
	String name, code;
	CommonStatus status= null;
	
	@Before
	public void init() {
		List<CreditAppraisalDisbursmentDetails> creditAppraisalDisbursmentDetails = creditAppraisalDisbursmentDetailsService.getAll();
        size = creditAppraisalDisbursmentDetails.size();
        if(size != 0) {
            for (CreditAppraisalDisbursmentDetails creditAppraisalDisbursmentDetail : creditAppraisalDisbursmentDetails) {
            	id = creditAppraisalDisbursmentDetail.getId().intValue();            
            	status= creditAppraisalDisbursmentDetail.getStatus();
                break;
            }
        }
	}
	
	//@Test
    public void getAllcreditAppraisalDisbursmentDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getcreditAppraisalDisbursmentDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
		
	
	//@Test
    public void getcreditAppraisalDisbursmentDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/credit-appraisal-disbursement-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
}
