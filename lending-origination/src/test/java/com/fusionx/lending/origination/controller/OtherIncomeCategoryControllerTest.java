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
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.OtherIncomeCategoryService;
/**
 * Other Income Category Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-639  	 FXL-535       Piyumi     Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherIncomeCategoryControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private OtherIncomeCategoryService otherIncomeCategoryService;
	
	@Autowired
	public void setOtherIncomeCategoryService(OtherIncomeCategoryService otherIncomeCategoryService) {
		this.otherIncomeCategoryService = otherIncomeCategoryService;
	}
	
	int size=0;
	int id = 0;
	String name = "Test";
	String code = "AAAA";
	CommonStatus status= CommonStatus.ACTIVE;

	@Before
	public void init() {
		List<OtherIncomeCategory> otherIncomeCategories = otherIncomeCategoryService.findAll();
        size = otherIncomeCategories.size();
        if(size != 0) {
            for (OtherIncomeCategory otherIncomeCategory : otherIncomeCategories) {
            	id = otherIncomeCategory.getId().intValue();
            	name = otherIncomeCategory.getName();
            	code = otherIncomeCategory.getCode();
            	status= otherIncomeCategory.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllOtherIncomeCategoryTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-category/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-category/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeCategoryByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeCategoryByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeCategoryByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherIncomeCategoryByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-income-category/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
