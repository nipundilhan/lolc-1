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
import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupService;

/**
 * 	Exception Approval Group Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionApprovalGroupControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private ExceptionApprovalGroupService exceptionApprovalGroupService;
	
	@Autowired
	public void setExceptionApprovalGroupService(ExceptionApprovalGroupService exceptionApprovalGroupService) {
		this.exceptionApprovalGroupService = exceptionApprovalGroupService;
	}
	
	int size=0;
	int id = 0;
	String name = "Test";
	String code = "TEST";
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<ExceptionApprovalGroup> exceptionApprovalGroups = exceptionApprovalGroupService.findAll();
        size = exceptionApprovalGroups.size();
        if(size != 0) {
            for (ExceptionApprovalGroup exceptionApprovalGroup : exceptionApprovalGroups) {
            	id = exceptionApprovalGroup.getId().intValue();
            	name = exceptionApprovalGroup.getName();
            	code = exceptionApprovalGroup.getCode();
            	status= exceptionApprovalGroup.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllExceptionApprovalGroupTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
