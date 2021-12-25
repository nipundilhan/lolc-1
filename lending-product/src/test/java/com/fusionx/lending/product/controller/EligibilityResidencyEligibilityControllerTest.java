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
import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.repository.EligibilityResidencyEligibilityRepository;

/**
 * EligibilityResidencyEligibilityControllerTest
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EligibilityResidencyEligibilityControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private EligibilityResidencyEligibilityRepository eligibilityResidencyEligibilityRepository;
	
	int size=0;
	long id,eligibilityId,residencyEligibilityId,pendingId= 0;
	String status = "ACTIVE";
	
	@Before
	public void init() {
		List<EligibilityResidencyEligibility> eligibilityResidencyEligibilityList = eligibilityResidencyEligibilityRepository.findAll();
        size = eligibilityResidencyEligibilityList.size();
        if(size != 0) {
            for (EligibilityResidencyEligibility eligibilityResidencyEligibility : eligibilityResidencyEligibilityList) {
            	id = eligibilityResidencyEligibility.getId();
            	eligibilityId = eligibilityResidencyEligibility.getEligibility().getId();
            	residencyEligibilityId = eligibilityResidencyEligibility.getResidencyEligibility().getId();
            	status= eligibilityResidencyEligibility.getStatus().toString();
                break;
            }
        }
	}

	
	@Test
    public void getByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByEligibilityIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/eligibility/"+eligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByResidencyEligibilityIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+residencyEligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+residencyEligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	//@Test
    public void getEligibilityOtherByPendingIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/eligibility-residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
