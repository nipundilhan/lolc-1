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
import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupTypeService;

/**
 * Exception Approval Group Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionApprovalGroupTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private ExceptionApprovalGroupTypeService exceptionApprovalGroupTypeService;
	
	@Autowired
	public void setExceptionApprovalGroupTypeService(ExceptionApprovalGroupTypeService exceptionApprovalGroupTypeService) {
		this.exceptionApprovalGroupTypeService = exceptionApprovalGroupTypeService;
	}
	
	int size=0;
	int id = 0;
	Long exceptionApprovalGroupId =  1l;
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<ExceptionApprovalGroupType> exceptionApprovalGroupTypes = exceptionApprovalGroupTypeService.findAll();
        size = exceptionApprovalGroupTypes.size();
        if(size != 0) {
            for (ExceptionApprovalGroupType exceptionApprovalGroupType : exceptionApprovalGroupTypes) {
            	id = exceptionApprovalGroupType.getId().intValue();
            	status= exceptionApprovalGroupType.getStatus();
            	exceptionApprovalGroupId = exceptionApprovalGroupType.getExceptionApprovalGroup().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAllTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getExceptionApprovalGroupTypeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getAllByExceptionApprovalGroupIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/exception-approval-group/"+exceptionApprovalGroupId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/exception-approval-group-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/exception-approval-group/"+exceptionApprovalGroupId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
