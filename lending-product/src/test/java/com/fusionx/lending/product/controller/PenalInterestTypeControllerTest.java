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
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.PenalInterestTypeService;

/**
 * Penal Interest Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-08-2020   	                        Dilhan       Created
 *    
 ********************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class PenalInterestTypeControllerTest {
/*	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private  PenalInterestTypeService  penalInterestTypeService;
	
	int size=0;
	int id = 0;
	String code="AAA",name="aaa";
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<PenalInterestType> penalInterestType = penalInterestTypeService.getAll();
        size = penalInterestType.size();
        if(size != 0) {
            for (PenalInterestType item : penalInterestType) {
            	id = item.getId().intValue();
            	code = item.getCode();
            	name = item.getName();
            	status= item.getStatus();
                break;
            }
        }
	}
	@Test
	public void getAllPenalInterestTypeTest() throws Exception {}
	
	/*@Test
    public void getAllPenalInterestTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/penal-interest-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/penal-interest-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
	
	/*@Test
    public void getPenalInterestTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
	
	/*@Test
    public void getPenalInterestTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
	
	/*@Test
    public void getPenalInterestTypeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/

	/*@Test
    public void getPenalInterestTypeByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/penal-interest-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
}
