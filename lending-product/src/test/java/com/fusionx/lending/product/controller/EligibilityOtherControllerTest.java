package com.fusionx.lending.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fusionx.lending.product.ConstantsTest;

/**
 * EligibilityOtherControllerTest
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING) */
public class EligibilityOtherControllerTest {

	/*
	@Autowired
    private MockMvc mockMvc;
	
	int size=0;
	long id,eligibilityId,pendingId= 0;

	
	@Test
    public void getEligibilityOtherByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByEligibilityTemplateIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getEligibilityOtherByPendingIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-other-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

	*/
}
