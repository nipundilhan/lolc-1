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
import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.repository.MasterCurrencyRepository;

/**
 * Master Currency Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterCurrencyControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private MasterCurrencyRepository masterCurrencyRepository;
	
	int size=0;
	long id =0l;
	long masterDefinitionId = 0l;
	//long pendingId= 0l;

	@Before
	public void init() {
		List<MasterCurrency> masterCurrencies = masterCurrencyRepository.findAll();
        size = masterCurrencies.size();
        if(size != 0) {
            for (MasterCurrency masterCurrency : masterCurrencies) {
            	id = masterCurrency.getId().intValue();
            	masterDefinitionId = masterCurrency.getMasterDefinition().getId();
                break;
            }
        }
	}
	
	
	@Test
    public void getMasterCurrencyByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/master-currency/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/master-currency/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
//	@Test
//    public void getByMasterDefinitionIdTest() throws Exception {
//        if(size != 0) {
//            mockMvc.perform(get("/master-currency/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
//        }else {
//            mockMvc.perform(get("/master-currency/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
//    }
	

}
