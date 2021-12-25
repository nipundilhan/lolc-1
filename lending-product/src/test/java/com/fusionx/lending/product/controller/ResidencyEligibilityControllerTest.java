package com.fusionx.lending.product.controller;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.ResidencyEligibilityService;
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class ResidencyEligibilityControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ResidencyEligibilityService residencyEligibilityService;
	
	int size=0;
	int id = 0;
	CommonStatus status=null;
	
	@Before
	public void init() {
		List<ResidencyEligibility> residencyEligibility = residencyEligibilityService.getAll();
        size = residencyEligibility.size();
        if(size != 0) {
            for (ResidencyEligibility residencyEligibilityRec : residencyEligibility) {
            	id = residencyEligibilityRec.getId().intValue();
            	status = residencyEligibilityRec.getStatus();
                break;
            }
        }
	}
	
/*	@Test
    public void getAllResidencyEligibilityTest() throws Exception {
        /*if(size != 0) {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }*/
//    }
	
	/*@Test
    public void getResidencyEligibilityByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityDetailByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/detail/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/detail/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/

}
