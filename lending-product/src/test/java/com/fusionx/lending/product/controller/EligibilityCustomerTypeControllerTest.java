package com.fusionx.lending.product.controller;

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

import com.fusionx.lending.product.ConstantsTest;
import com.fusionx.lending.product.domain.EligibilityCustomerType;
import com.fusionx.lending.product.repository.EligibilityCustomerTypeRepository;

/**
 * EligibilityCustomerTypeControllerTest
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   29-07-2021    FXL_365			 	FXL-56		Piyumi      Created
 *    
 *******************************************************************************************************
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityCustomerTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private EligibilityCustomerTypeRepository eligibilityCustomerTypeRepository;
	
	int size=0;
	long id,eligibilityId,customerTypeId,pendingId= 0;
	String status = "ACTIVE";
	
	@Before
	public void init() {
		List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeRepository.findAll();
        size = eligibilityCustomerTypeList.size();
        if(size != 0) {
            for (EligibilityCustomerType eligibilityCustomerType : eligibilityCustomerTypeList) {
            	id = eligibilityCustomerType.getId();
            	eligibilityId = eligibilityCustomerType.getEligibility().getId();
            	customerTypeId = eligibilityCustomerType.getCustomerTypeId();
            	status= eligibilityCustomerType.getStatus().toString();
                break;
            }
        }
	}

	
	//@Test
    public void getByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getByEligibilityIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getByCustomerTypeIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer-types/"+customerTypeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer-types/"+customerTypeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	//@Test
    public void getByPendingIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-customer-types/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
