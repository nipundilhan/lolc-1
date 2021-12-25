package com.fusionx.lending.product.controller;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
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
import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeatureBenefitEligibilityTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private FeatureBenefitEligibilityTypeService featureBenefitEligibilityTypeService;
	
	int size=0;
	int id = 0;
	CommonStatus status=null;
	String name=null;
	String code=null;
	
	@Before
	public void init() {
		List<FeatureBenefitEligibilityType> featureBenefitEligibilityTypes = featureBenefitEligibilityTypeService.getAll();
        size = featureBenefitEligibilityTypes.size();
        if(size != 0) {
            for (FeatureBenefitEligibilityType featureBenefitEligibilityType : featureBenefitEligibilityTypes) {
            	id = featureBenefitEligibilityType.getId().intValue();
            	status = featureBenefitEligibilityType.getStatus();
            	name = featureBenefitEligibilityType.getName();
            	code = featureBenefitEligibilityType.getCode();
                break;
            }
        }
	}
	
	@Test
    public void getAllFeatureBenefitEligibilityTypeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getFeatureBenefitEligibilityTypeByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityDetailByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityDetailByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-eligibility-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
