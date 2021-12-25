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
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ExceptionTypeService;
/**
 * Exception Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-08-2021   FXL-627  	 FXL-563       Piyumi     Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private ExceptionTypeService exceptionTypeService;
	
	@Autowired
	public void setExceptionTypeService(ExceptionTypeService exceptionTypeService) {
		this.exceptionTypeService = exceptionTypeService;
	}
	
	int size=0;
	int id = 0;
	String name = "Test";
	String code = "AAAA";
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<ExceptionType> exceptionTypes = exceptionTypeService.findAll();
        size = exceptionTypes.size();
        if(size != 0) {
            for (ExceptionType exceptionType : exceptionTypes) {
            	id = exceptionType.getId().intValue();
            	name = exceptionType.getName();
            	code = exceptionType.getCode();
            	status= exceptionType.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllExceptionTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionTypeByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionTypeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
