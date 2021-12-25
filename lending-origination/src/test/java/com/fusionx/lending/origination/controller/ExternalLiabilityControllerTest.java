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
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.repository.ExternalLiabilityRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalLiabilityControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ExternalLiabilityRepository externalLiabilityRepository;
	
	
	int size=0;
	Long customerId = 0l;
	Long externalLiabilityId = 0l;
	String status="ACTIVE";
	
	@Before
	public void init() {
		List<ExternalLiability> externalLiabilityList = externalLiabilityRepository.findAll();
		size = externalLiabilityList.size();
        if(size != 0) {
            for (ExternalLiability externalLiability : externalLiabilityList) {
            	externalLiabilityId =  externalLiability.getId();
            	customerId = externalLiability.getCustomer().getId();
            	status = externalLiability.getStatus().toString();
                break;
            }
        }
	}
	
	@Test
    public void getExternalLiabilitesByCustomerIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer/"+customerId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

	
	
	@Test
    public void getExternalLiabilitesByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+externalLiabilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+externalLiabilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAllExternalLiabilitesTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getExternalLiabilitesByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/external-liability/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
