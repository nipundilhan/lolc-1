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
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.LeadStatus;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.service.LeadInfoService;


/**
 * Lead Info Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021   FXL-380        FXL-421      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeadInfoControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private LeadInfoService leadInfoService;

	
	int size=0;
	int id = 0;
	long branchId = 0l;
	String name = "TESTNAME";
	String leadStatus = LeadStatus.COMPLETED.toString();
	String date = "1900-01-01" ;
	String status = CommonStatus.ACTIVE.toString();

//	@Before
	public void init() {
		List<LeadInfo> leadInfoList =  leadInfoRepository.findAll();
        size = leadInfoList.size();
        if(size != 0) {
            for (LeadInfo leadInfo : leadInfoList) {
            	//leadInfo = leadInfoService.findDetailById(leadInfo.getId());
            	id = leadInfo.getId().intValue();
            	//name = leadInfo.getCustomers() != null ? leadInfo.getCustomers().get(0).getFullName(): null;
            	//branchId = leadInfo.getBranchId();
            	//leadStatus= leadInfo.getLeadStatus().toString();
            	date = leadInfo.getCreatedDate().toString();
            	status = leadInfo.getStatus().toString();
                break;
            }
        }
	}
	
	@Test
    public void getAllLeadInfoTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }/*else {
            mockMvc.perform(get("/lead-info/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }*/
    }
	
	@Test
    public void getLeadInfoDetailByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
    //@Test
    public void getByLeadStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/lead-status/"+leadStatus)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/lead-status/"+leadStatus)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
//	@Test
    public void getByCreatedDateTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/date/"+date)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/date/"+date)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }	
	
	//@Test
    public void getByCustomerNameContainingTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer-name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/customer-name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getByBranchTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/branch/"+branchId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/branch/"+branchId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
//	@Test
    public void getByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/lead-info/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
