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
import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.CultivationIncomeDetailsRepository;

/**
 * 	Cultivation Income Details Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021     	         FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultivationIncomeDetailsControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private CultivationIncomeDetailsRepository cultivationIncomeDetailsRepository;
	
	@Autowired
	public void setCultivationIncomeDetailsRepository(
			CultivationIncomeDetailsRepository cultivationIncomeDetailsRepository) {
		this.cultivationIncomeDetailsRepository = cultivationIncomeDetailsRepository;
	}

	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;
	Long incomeSourceDetailsId = 1l;

	@Before
	public void init() {
		List<CultivationIncomeDetails> cultivationIncomeDetails = cultivationIncomeDetailsRepository.findAll();
        size = cultivationIncomeDetails.size();
        if(size != 0) {
            for (CultivationIncomeDetails cultivationIncomeDetail : cultivationIncomeDetails) {
            	id = cultivationIncomeDetail.getId().intValue();
            	status = cultivationIncomeDetail.getStatus();
            	incomeSourceDetailsId = cultivationIncomeDetail.getIncomeSourceDetail().getId();
                break;
            }
        }
	}
	
	@Test
    public void getAllSalaryIncomeDetailsTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-income-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-income-details/"+ ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getSalaryIncomeDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getSalaryIncomeDetailsByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getCultivationIncomeDetailsByIncomeSourceDetailsIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/income-source-details/"+incomeSourceDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-income-details/"+ConstantsTest.DEFAULT_TENANT_ID+"/income-source-details/"+incomeSourceDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
