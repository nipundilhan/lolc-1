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
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.InterestTierBandSetPending;
import com.fusionx.lending.product.repository.InterestTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.InterestTierBandSetRepository;

/**
 * InterestTierBandSetControllerTest 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   21-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterestTierBandSetControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private InterestTierBandSetRepository interestTierBandSetRepository;
	
	@Autowired
	private InterestTierBandSetPendingRepository interestTierBandSetPendingRepository;
	
	int size=0;
	int pendingSize=0;
	long id=0,interestTemplateId=0,pendingId= 0;
	

	@Before
	public void init() {
		List<InterestTierBandSet> interestTierBandSets = interestTierBandSetRepository.findAll();
        size = interestTierBandSets.size();
        if(size != 0) {
            for (InterestTierBandSet interestTierBandSet : interestTierBandSets) {
            	id = interestTierBandSet.getId();
            	interestTemplateId = interestTierBandSet.getInterestTemplate().getId();
                break;
            }
        }
        
        List<InterestTierBandSetPending> interestTierBandSetPendings = interestTierBandSetPendingRepository.findAll();
        pendingSize = interestTierBandSetPendings.size();
        if(pendingSize != 0) {
            for (InterestTierBandSetPending interestTierBandSetPending : interestTierBandSetPendings) {
            	pendingId = interestTierBandSetPending.getId();
                break;
            }
        }
        
	}
	
	@Test
    public void getInterestTierBandSetByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getByInterestTemplateIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/interest-template/"+interestTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/interest-template/"+interestTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getInterestTierBandSetByPendingIdTest() throws Exception {
        if(pendingSize != 0) {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/interest-tier-band-set/"+ConstantsTest.DEFAULT_TENANT_ID+"/pending/"+pendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

}
