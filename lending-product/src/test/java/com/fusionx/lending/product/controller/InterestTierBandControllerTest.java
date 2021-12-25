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
import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandPending;
import com.fusionx.lending.product.repository.InterestTierBandPendingRepository;
import com.fusionx.lending.product.repository.InterestTierBandRepository;

/**
 * InterestTierBandControllerTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   26-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class InterestTierBandControllerTest {
	/*
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private InterestTierBandRepository interestTierBandRepository;

	@Autowired
	private InterestTierBandPendingRepository interestTierBandPendingRepository;

	
	int size=0;
	int pendingSize=0;
	long id=0,interestTierBandSetId=0,pendingId= 0;
	
	@Before
	public void init() {
		List<InterestTierBand> interestTierBands = interestTierBandRepository.findAll();
        size = interestTierBands.size();
        if(size != 0) {
            for (InterestTierBand interestTierBand : interestTierBands) {
            	id = interestTierBand.getId();
            	interestTierBandSetId = interestTierBand.getInterestTierBandSet().getId();
                break;
            }
        }
        
        List<InterestTierBandPending> interestTierBandPendings = interestTierBandPendingRepository.findAll();
        pendingSize = interestTierBandPendings.size();
        if(pendingSize != 0) {
            for (InterestTierBandPending interestTierBandPending : interestTierBandPendings) {
            	pendingId = interestTierBandPending.getId();
                break;
            }
        }
        
	}

	
	@Test
    public void getInterestTierBandByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByInterestTemplateIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/interest-tier-band-set/"+interestTierBandSetId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/interest-tier-band-set/"+interestTierBandSetId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getInterestTierBandByPendingIdTest() throws Exception {
        if(pendingSize != 0) {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

*/
}
