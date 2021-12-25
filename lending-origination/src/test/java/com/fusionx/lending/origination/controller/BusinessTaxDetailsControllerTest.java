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
import com.fusionx.lending.origination.domain.BusinessIncomeDetails;
import com.fusionx.lending.origination.domain.BusinessTaxDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.BusinessIncomeDetailsService;
import com.fusionx.lending.origination.service.BusinessTaxDetailsService;
/**
 * 	Business Tax Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessTaxDetailsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private BusinessTaxDetailsService businessTaxDetailsService;
	
	@Autowired
	public void setBusinessTaxDetailsService(BusinessTaxDetailsService businessTaxDetailsService) {
		this.businessTaxDetailsService = businessTaxDetailsService;
	}
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;
	Long businessIncomeDetailsId = 1l;

	@Before
	public void init() {
		List<BusinessTaxDetails> businessTaxDetails = businessTaxDetailsService.findAll();
        size = businessTaxDetails.size();
        if(size != 0) {
            for (BusinessTaxDetails businessTaxDetail : businessTaxDetails) {
            	id = businessTaxDetail.getId().intValue();
            	status = businessTaxDetail.getStatus();
            	businessIncomeDetailsId = businessTaxDetail.getBusinessIncomeDetail().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAllBusinessTaxDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-tax-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-tax-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getBusinessTaxDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getBusinessTaxDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByBusinessIncomeDetailIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-income-details/"+businessIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-tax-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-income-details/"+businessIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }



}
