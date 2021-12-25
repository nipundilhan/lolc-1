package com.fusionx.lending.product.controller;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

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
import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.ResidencyIncludeService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResidencyIncludeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ResidencyIncludeService residencyIncludeService;
	
	int size=0;
	int id = 0;
	int residencyEligibilityid = 0;
	CommonStatus status=null;
	
	@Before
	public void init() {
		List<ResidencyInclude> residencyInclude = residencyIncludeService.findAll(ConstantsTest.DEFAULT_TENANT_ID);
        size = residencyInclude.size();
        if(size != 0) {
            for (ResidencyInclude residencyIncludeRec : residencyInclude) {
            	id = residencyIncludeRec.getId().intValue();
            	residencyEligibilityid = residencyIncludeRec.getResidencyEligibility().getId().intValue();
            	status = residencyIncludeRec.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getAllResidencyIncludeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyIncludeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyIncludeByResidencyEligibilityIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+residencyEligibilityid)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+residencyEligibilityid)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyIncludeByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-include/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
