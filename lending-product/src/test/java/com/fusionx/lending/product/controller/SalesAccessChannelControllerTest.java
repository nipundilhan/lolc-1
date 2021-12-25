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
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.SalesAccessChannelService;

/**
 * Sales Access Channel Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class SalesAccessChannelControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private SalesAccessChannelService salesAccessChannelService;
	
	int size=0;
	int id = 0;
	String name, code;
	CommonStatus status= null;

	@Before
	public void init() {
		List<SalesAccessChannel> salesAccessChannels = salesAccessChannelService.getAll();
        size = salesAccessChannels.size();
        if(size != 0) {
            for (SalesAccessChannel salesAccessChannel : salesAccessChannels) {
            	id = salesAccessChannel.getId().intValue();
            	name = salesAccessChannel.getName();
            	code = salesAccessChannel.getCode();
            	status= salesAccessChannel.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllSalesAccessChannelTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sales-access-channel/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sales-access-channel/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalesAccessChannelByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalesAccessChannelByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalesAccessChannelByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalesAccessChannelByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sales-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
