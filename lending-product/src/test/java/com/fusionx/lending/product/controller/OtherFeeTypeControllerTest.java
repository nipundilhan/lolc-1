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
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.service.OtherFeeTypeService;

/**
 * Other Fee Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class OtherFeeTypeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private OtherFeeTypeService otherFeeTypeService;
	
	int size=0;
	int id = 0;
	String name, code = null;

	@Before
	public void init() {
		List<OtherFeeType> otherFeeTypes = otherFeeTypeService.getAll("AnRkr");
        size = otherFeeTypes.size();
        if(size != 0) {
            for (OtherFeeType otherFeeType : otherFeeTypes) {
            	id = otherFeeType.getId().intValue();
            	name = otherFeeType.getName();
            	code = otherFeeType.getCode();
                break;
            }
        }
	}
	
	//@Test
    public void getAllOtherFeeTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getOtherFeeTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getOtherFeeTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getOtherFeeTypeByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
