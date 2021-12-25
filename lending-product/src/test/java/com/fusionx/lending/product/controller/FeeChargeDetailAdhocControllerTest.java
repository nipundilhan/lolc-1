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
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhocPending;
import com.fusionx.lending.product.repository.FeeChargeDetailAdhocPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailAdhocRepository;
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeeChargeDetailAdhocControllerTest {
/*
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private FeeChargeDetailAdhocPendingRepository feeChargeDetailAdhocPendingRepository;
	
	@Autowired
    private FeeChargeDetailAdhocRepository feeChargeDetailAdhocRepository;
	
	int feeChargeId=1;
	int feeChargePendingId=0;	
	int feeChargeDetailAdhocId=1;
	int categoryId=1;
	String status= "ACTIVE";
	int size=0;
	
	
	
	@Before
	public void init() {
		List<FeeChargeDetailAdhoc> adhocList = feeChargeDetailAdhocRepository.findAll();
		List<FeeChargeDetailAdhocPending> adhocPendingList = feeChargeDetailAdhocPendingRepository.findAll();
        size = adhocList.size();
        if(adhocList.size() != 0) {
            for (FeeChargeDetailAdhoc item : adhocList) {
            	feeChargeDetailAdhocId = item.getId().intValue();
            	feeChargeId = item.getFeeCharge().getId().intValue();
            	status =  item.getStatus().toString();
            	categoryId = item.getFeeCategory().getId().intValue();
                break;
            }
        }
        if(adhocPendingList.size() != 0) {
            for (FeeChargeDetailAdhocPending item : adhocPendingList) {
            	feeChargePendingId = item.getFeeChargePending().getId().intValue();
                break;
            }
        }

	}
	
	
	@Test
    public void findByFeeChargeIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge/"+feeChargeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge/"+feeChargeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDetailsIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+feeChargeDetailAdhocId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+feeChargeDetailAdhocId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getDetailsByFeeChargePendingIdTest() throws Exception {
        if(feeChargePendingId != 0) {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge-pending/"+feeChargePendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/fee-charge-detail-adhoc/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge-pending/"+feeChargePendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	*/

}
