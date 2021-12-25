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
import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.DisbursementConditionsService;

/**
 * Disbursement Conditions service
 * @author 	Dilhan
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-09-2021  			     FXL-788     Dilhan      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisbursementConditionsControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private DisbursementConditionsService disbursementConditionsService;
	
	int size=0;
	int id = 0;
	String name, code;
	CommonStatus status= null;

	@Before
	public void init() {
		List<DisbursementConditions>  disbursementConditions = disbursementConditionsService.getAll();
        size = disbursementConditions.size();
        if(size != 0) {
            for (DisbursementConditions disbursementCondition : disbursementConditions) {
            	id = disbursementCondition.getId().intValue();
            	name = disbursementCondition.getName();
            	code = disbursementCondition.getCode();
            	status= disbursementCondition.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllDisbursementConditionsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/disbursement-conditions/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/disbursement-conditions/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDisbursementConditionsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDisbursementConditionsByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDisbursementConditionsByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDisbursementConditionsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/disbursement-conditions/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
