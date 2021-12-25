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
import com.fusionx.lending.product.domain.MasterDefCountry;
import com.fusionx.lending.product.domain.MasterDefCountryPending;
import com.fusionx.lending.product.repository.MasterDefCountryPendingRepository;
import com.fusionx.lending.product.repository.MasterDefCountryRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDefCountryControllerTest {
	
	/*
	@Autowired
    private MockMvc mockMvc;
	
	int size=0;
	int id = 0;
	Long masterDefinitionId = 0l;
	Long masterDefinitionPendingId = 0l;	
	String name, code = null;

	@Autowired
	private MasterDefCountryRepository masterDefCountryRepository;
	
	@Autowired
	private MasterDefCountryPendingRepository masterDefCountryPendingRepository;
	
	@Before
	public void init() {
		List<MasterDefCountryPending> pendingCountryList = masterDefCountryPendingRepository.findAll();
        size = pendingCountryList.size();
        if(size != 0) {
            for (MasterDefCountryPending countryPending : pendingCountryList) {
            	masterDefinitionId = countryPending.getMasterDefinitionPending().getMasterDefinition().getId();
            	masterDefinitionPendingId =  countryPending.getMasterDefinitionPending().getId();
                break;
            }
        }
        List<MasterDefCountry> countryList = masterDefCountryRepository.findAll();
        if(size == 0) {
        	size = countryList.size();
			if (size != 0) {
				for (MasterDefCountry country : countryList) {
					masterDefinitionId = country.getMasterDefinition().getId();
					break;
				}
			}
        }
        
	}
	
	
	@Test
    public void getByMasterDefIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/master-def-country/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/master-def-country/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByMasterDefPendingIdTest() throws Exception {
        if(masterDefinitionPendingId != 0l) {
            mockMvc.perform(get("/master-def-country/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition-pending/"+masterDefinitionPendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/master-def-country/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition-pending/"+masterDefinitionPendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
    */

}
