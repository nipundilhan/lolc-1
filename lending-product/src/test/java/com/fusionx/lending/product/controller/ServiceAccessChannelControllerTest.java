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
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.SalesAccessChannelService;
import com.fusionx.lending.product.service.ServiceAccessChannelService;

/**
 * Service Access Channel Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceAccessChannelControllerTest {
	
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ServiceAccessChannelService serviceAccessChannelService;
	
	int size=0;
	int id = 0;
	String name = "Test";
	String code = "Test";
	String status= "ACTIVE";
	
	@Before
	public void init() {
		List<ServiceAccessChannel> serviceAccessChannels = serviceAccessChannelService.getAll();
        size = serviceAccessChannels.size();
        if(size != 0) {
            for (ServiceAccessChannel serviceAccessChannel : serviceAccessChannels) {
            	id = serviceAccessChannel.getId().intValue();
            	name = serviceAccessChannel.getName();
            	code = serviceAccessChannel.getCode();
            	status= serviceAccessChannel.getStatus().toString();
                break;
            }
        }
	}
	
	
	@Test
    public void getAllServiceAccessChannelTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/service-access-channel/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/service-access-channel/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getServiceAccessChannelByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getServiceAccessChannelByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getServiceAccessChannelByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSServiceAccessChannelByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/service-access-channel/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
