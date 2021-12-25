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
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.DueDateTemplatesService;

/**
 * Due Date Templates Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021    FXL-139  	 FXL-926	Piyumi       Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DueDateTemplateControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private DueDateTemplatesService dueDateTemplatesService;
	
	int size=0;
	int id = 0;
	String name, code = "TEST";
	CommonStatus status= CommonStatus.ACTIVE;
	String effectiveDate = "1900-01-01";

	@Before
	public void init() {
		List<DueDateTemplates> dueDateTemplates = dueDateTemplatesService.findAll();
        size = dueDateTemplates.size();
        if(size != 0) {
            for (DueDateTemplates dueDateTemplate : dueDateTemplates) {
            	id = dueDateTemplate.getId().intValue();
            	name = dueDateTemplate.getName();
            	code = dueDateTemplate.getCode();
            	status= dueDateTemplate.getStatus();
            	effectiveDate = dueDateTemplate.getEffectiveDate().toString();
                break;
            }
        }
	}
	
	@Test
    public void getAllDueDateTemplatesTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDueDateTemplatesByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDueDateTemplatesByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDueDateTemplatesByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDueDateTemplatesByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
//	@Test
    public void getDueDateTemplatesByEffectiveDateTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/effective-date/"+effectiveDate)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/due-date-template/"+ConstantsTest.DEFAULT_TENANT_ID+"/effective-date/"+effectiveDate)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
