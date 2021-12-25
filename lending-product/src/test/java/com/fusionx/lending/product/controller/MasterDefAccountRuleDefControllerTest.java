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
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.repository.MasterDefAccountRuleSetStandardPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.service.FeeChargeService;
import com.fusionx.lending.product.service.MasterDefAccountRuleService;
import com.fusionx.lending.product.service.MasterDefinitionService;

/**
 * Master Def Account Rule  Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021             				 Nipun        Created
 *    
 ********************************************************************************************************
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDefAccountRuleDefControllerTest {
	
/*
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private MasterDefAccountRuleService masterDefAccountRuleService;
	
	@Autowired
	private MasterDefinitionService masterDefinitionService;
	
	@Autowired
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;
	
	@Autowired
	private MasterDefAccountRuleSetStandardPendingRepository masterDefAccountRuleSetStandardPendingRepository;
	
	int size=0;
	int id = 0;
	Long masterDefinitionId = 0l;
	Long masterDefinitionPendingId = 0l;	
	String name, code = null;

	@Before
	public void init() {
		List<MasterDefAccountRuleSetStandardPending> masterDefinitionAccountRuleSetStandardPendingList = masterDefAccountRuleSetStandardPendingRepository.findAll();
        size = masterDefinitionAccountRuleSetStandardPendingList.size();
        if(size != 0) {
            for (MasterDefAccountRuleSetStandardPending masterDefAccountRuleSetStandardPending : masterDefinitionAccountRuleSetStandardPendingList) {
            	masterDefinitionId = masterDefAccountRuleSetStandardPending.getMasterDefinitionPending().getMasterDefinition().getId();
            	masterDefinitionPendingId =  masterDefAccountRuleSetStandardPending.getMasterDefinitionPending().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAccountRuleDetailsByMasterDefIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/master-def-account-rule/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-def-pending/"+masterDefinitionPendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/master-def-account-rule/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-def-pending/"+masterDefinitionPendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isUnprocessableEntity());
        }
    }
	
	
	@Test
    public void getAccountRuleDetailsByMasterDefPendingIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/master-def-account-rule/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/master-def-account-rule/"+ConstantsTest.DEFAULT_TENANT_ID+"/master-definition/"+masterDefinitionId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isUnprocessableEntity());
        }
    }
   */

}
