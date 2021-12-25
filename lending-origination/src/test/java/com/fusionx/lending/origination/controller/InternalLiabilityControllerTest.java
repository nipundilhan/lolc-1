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
import com.fusionx.lending.origination.domain.InternalLiability;
import com.fusionx.lending.origination.repository.InternalLiabilityRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InternalLiabilityControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private InternalLiabilityRepository internalLiabilityRepository;
	
	
	int size=0;
	Long customerId = 0l;
	Long internalLiabilityId = 0l;
	String status="ACTIVE";
	
	@Before
	public void init() {
		List<InternalLiability> internalLiabilityList = internalLiabilityRepository.findAll();
		size = internalLiabilityList.size();
        if(size != 0) {
            for (InternalLiability internalLiability : internalLiabilityList) {
            	internalLiabilityId =  internalLiability.getId();
            	customerId = internalLiability.getCustomer().getId();
            	status = internalLiability.getStatus().toString();
                break;
            }
        }
	}
	
	
	@Test
    public void getInternalLiabilitesByCustomerIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

	
	
	@Test
    public void getInternalLiabilitesByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+internalLiabilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+internalLiabilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAllInternalLiabilitesTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getInternalLiabilitesByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/internal-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
