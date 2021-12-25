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
import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.service.OtherFeeRateTypeService;

/**
 * Other Fee Rate Type Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OtherFeeRateTypeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private OtherFeeRateTypeService otherFeeRateTypeService;
	
	int size=0;
	int id = 0;
	String name, code = null;

	@Before
	public void init() {
		List<OtherFeeRateType> otherFeeRateTypes = otherFeeRateTypeService.getAll();
        size = otherFeeRateTypes.size();
        if(size != 0) {
            for (OtherFeeRateType otherFeeRateType : otherFeeRateTypes) {
            	id = otherFeeRateType.getId().intValue();
            	name = otherFeeRateType.getName();
            	code = otherFeeRateType.getCode();
                break;
            }
        }
	}
	
	@Test
    public void getAllOtherFeeRateTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherFeeRateTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherFeeRateTypeByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getOtherFeeRateTypeByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/other-fee-rate-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
