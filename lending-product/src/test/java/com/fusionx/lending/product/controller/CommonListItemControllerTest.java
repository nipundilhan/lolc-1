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
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.service.CommonListItemService;

/**
 * Common list Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020      		     FX-6514	Senitha      Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CommonListItemControllerTest {
/*
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private CommonListItemService commonListItemService;
	
	int size=0;
	int id = 0;
	String name, code, referenceCode = null;

	@Before
	public void init() {
		List<CommonListItem> commonListItem = commonListItemService.findAll();
        size = commonListItem.size();
        if(size != 0) {
            for (CommonListItem commonListItem2 : commonListItem) {
            	id = commonListItem2.getId().intValue();
            	name = commonListItem2.getName();
            	code = commonListItem2.getCode();
            	referenceCode = commonListItem2.getReferenceCode();
                break;
            }
        }
	}
	
	@Test
    public void getAllCommonListTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getCommonListByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getCommonListsByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getCommonListsByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getCommonListsByReferenceCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/reference-code/"+referenceCode)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/common-list-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/reference-code/"+referenceCode)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
