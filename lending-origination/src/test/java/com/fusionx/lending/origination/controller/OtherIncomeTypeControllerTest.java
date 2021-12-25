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
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.service.OtherIncomeTypeService;

/**
 * Other Income Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
*    1   18-08-2021     FXL-639     FXL-537			Piyumi		Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeTypeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private OtherIncomeTypeService otherIncomeTypeService;
	
	int size=0;
	int id = 0;
	String name = "AAAA";
	String code = "ABCD";
	String status= "ACTIVE";
	Long otherIncomeCategoryId = 1l;

	@Before
	public void init() {
		List<OtherIncomeType> otherIncomeTypes = otherIncomeTypeService.findAll();
        size = otherIncomeTypes.size();
        if(size != 0) {
            for (OtherIncomeType otherIncomeType : otherIncomeTypes) {
            	id = otherIncomeType.getId().intValue();
            	name = otherIncomeType.getName();
            	code = otherIncomeType.getCode();
            	status= otherIncomeType.getStatus();
            	otherIncomeCategoryId = otherIncomeType.getOtherIncomeCategory().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAllOtherIncomeTypesTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeTypesByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeTypesByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeTypesByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeTypesByOtherIncomeCategoryIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/other-income-category/"+otherIncomeCategoryId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/other-income-category/"+otherIncomeCategoryId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
