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
import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.CoreProductService;
import com.fusionx.lending.product.service.DisbursementConditionsService;

/**
 * Core Product service
 * @author 	Dilhan
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021  			     FXL-795     Dilhan      Created
 *    
 ********************************************************************************************************
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoreProductControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private CoreProductService coreProductService;
	
	int size=0;
	int id = 0;
	String description, code;
	CommonStatus status= null;

	@Before
	public void init() {
		List<CoreProduct>  coreProducts = coreProductService.findAll();
        size = coreProducts.size();
        if(size != 0) {
            for (CoreProduct coreProduct : coreProducts) {
            	id = coreProduct.getId().intValue();
            	description = coreProduct.getDescription();
            	code = coreProduct.getCode();
            	status= coreProduct.getStatus();
                break;
            }
        }
	}
	
	//@Test
    public void getAllCoreProductTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/core-product/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/core-product/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getCoreProductByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getCoreProductByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getCoreProductByDescriptionTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/description/"+description)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/description/"+description)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	//@Test
    public void getCoreProductByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/core-product/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
