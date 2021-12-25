package com.fusionx.lending.product.controller;

/**
 * Age Eligibility service
 * @author 	MenukaJ
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021  						    MenukaJ      Created
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
import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.service.AgeEligibilityService;



/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class AgeEligibilityControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private AgeEligibilityService ageEligibilityService;
	
	int size=0;
	int id = 0;
	String status=null;
	
	@Before
	public void init() {
		List<AgeEligibility> ageEligibility = ageEligibilityService.findAll();
        size = ageEligibility.size();
        if(size != 0) {
            for (AgeEligibility ageEligibilityRec : ageEligibility) {
            	id = ageEligibilityRec.getId().intValue();
            	status = ageEligibilityRec.getStatus().toString();
                break;
            }
        }
	}
	
	@Test
    public void getAgeEligibilityTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getAgeEligibilityByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAgeEligibilityByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/age-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
