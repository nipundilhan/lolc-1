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
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityRepository;
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeatureBenefitTemplateItemEligibilityControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private FeatureBenefitTemplateItemEligibilityRepository featureBenefitTemplateItemEligibilityRepository;
	
	
	int size=0;
	Long featureBenifitTemplateItemEligibilityId = 0l;
	Long featureBenifitTemplateItemId = 0l;

	@Before
	public void init() {
		List<FeatureBenefitTemplateItemEligibility> featureBenefitTemplateIteEligibilitymAll = featureBenefitTemplateItemEligibilityRepository.findAll();
        size = featureBenefitTemplateIteEligibilitymAll.size();
        if(size != 0) {
            for (FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility : featureBenefitTemplateIteEligibilitymAll) {
            	featureBenifitTemplateItemEligibilityId = featureBenefitTemplateItemEligibility.getId();
            	featureBenifitTemplateItemId = featureBenefitTemplateItemEligibility.getFeatureBenefitTemplateItem().getId();
                break;
            }
        }
	}
	
	
	@Test
    public void findByFeatureBenifitTemplatItemIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-template-item-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/template-item/"+featureBenifitTemplateItemId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/feature-benefit-template-item-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/template-item/"+featureBenifitTemplateItemId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }
	
	@Test
    public void findByFeatureBenifitTemplateItemEligibilityIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-template-item-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+featureBenifitTemplateItemEligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-template-item-eligibility/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+featureBenifitTemplateItemEligibilityId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
