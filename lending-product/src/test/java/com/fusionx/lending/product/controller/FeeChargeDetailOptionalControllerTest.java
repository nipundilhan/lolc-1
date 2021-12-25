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
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalPendingRepository;
import com.fusionx.lending.product.repository.FeeChargeDetailOptionalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeChargeDetailOptionalControllerTest {

	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private FeeChargeDetailOptionalPendingRepository feeChargeDetailOptionalPendingRepository;	
	
	@Autowired
	private FeeChargeDetailOptionalRepository feeChargeDetailOptionalRepository;
	
	int feeChargeId=1;
	int feeChargePendingId=0;
	int feeChargeDetailOptionalId=1;
	int categoryId=1;
	String status= "ACTIVE";
	int size=0;
	
	
	@Before
	public void init() {
		List<FeeChargeDetailOptionalPending> feeChargeDetailOptionalPendingList = feeChargeDetailOptionalPendingRepository.findAll();
		List<FeeChargeDetailOptional> feeChargeDetailOptionalList = feeChargeDetailOptionalRepository.findAll();
        size = feeChargeDetailOptionalList.size();
        if(feeChargeDetailOptionalList.size() != 0) {
            for (FeeChargeDetailOptional item : feeChargeDetailOptionalList) {
            	feeChargeDetailOptionalId = item.getId().intValue();
            	feeChargeId = item.getFeeCharge().getId().intValue();
            	status =  item.getStatus().toString();
            	categoryId = item.getFeeCategory().getId().intValue();
                break;
            }
        }
        if(feeChargeDetailOptionalPendingList.size() != 0) {
            for (FeeChargeDetailOptionalPending item : feeChargeDetailOptionalPendingList) {
            	feeChargePendingId = item.getFeeChargePending().getId().intValue();
                break;
            }
        }

	}
	
	@Test
    public void findByFeeChargeIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge/"+feeChargeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge/"+feeChargeId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }
	
	@Test
    public void getDetailsIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+feeChargeDetailOptionalId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+feeChargeDetailOptionalId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }
	
	@Test
    public void getDetailsByFeeChargePendingIdTest() throws Exception {
        if(feeChargePendingId != 0) {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge-pending/"+feeChargePendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/fee-charge-pending/"+feeChargePendingId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }
	
	
	@Test
    public void findByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void findByCategoryTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/category/"+categoryId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/fee-charge-detail-optional/"+ConstantsTest.DEFAULT_TENANT_ID+"/category/"+categoryId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }	

}
