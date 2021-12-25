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
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.service.IncomeTypeService;

/**
 * Other Income Category Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-340  	 FXL-534       Piyumi     	Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeTypeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private IncomeTypeService incomeTypeService;
	
	int size=0;
	int id = 0;
	String name = "AAAA";
	String code = "TEST";
	Long businessTypeId = 1l;
	String status= "ACTIVE";

	@Before
	public void init() {
		List<IncomeType> incomeTypes = incomeTypeService.findAll();
        size = incomeTypes.size();
        if(size != 0) {
            for (IncomeType incomeType : incomeTypes) {
            	id = incomeType.getId().intValue();
            	name = incomeType.getName();
            	code = incomeType.getCode();
            	status= incomeType.getStatus();
            	businessTypeId = incomeType.getBusinessType().getId();
                break;
            }
        }
	}
	
	@Test
    public void getIncomeTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeTypeIdByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeTypeByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeTypeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getIncomeTypeByBusinessTypeIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-type/"+businessTypeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-type/"+businessTypeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
