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
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.FeeChargeDetailsService;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeeChargeDetailsControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private  FeeChargeDetailsService  feeChargeDetailsService;
	
	int size=0;
	int id = 0;
	String code;
	CommonStatus status= null;

	@Before
	public void init() {
		List<FeeChargeDetails> feeChargeDetails = feeChargeDetailsService.getAll();
        size = feeChargeDetails.size();
        if(size != 0) {
            for (FeeChargeDetails item : feeChargeDetails) {
            	id = item.getId().intValue();
            	code = item.getCode();
            	status= item.getStatus();
                break;
            }
        }
	}
	
	
	@Test
    public void getAllFeeChargeDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/fee-charge-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getFeeChargeDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getFeeChargeDetailsByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getFeeChargeDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/fee-charge-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
