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
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemRepository;
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class FeatureBenefitTemplateItemControllerTest {
/*	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private FeatureBenefitTemplateItemRepository featureBenefitTemplateItemRepository;
	
	
	int size=0;
	Long featureBenifitTemplateId = 0l;
	Long featureBenifitTemplateItemId = 0l;

	@Before
	public void init() {
		List<FeatureBenefitTemplateItem> featureBenefitTemplateItemAll = featureBenefitTemplateItemRepository.findAll();
        size = featureBenefitTemplateItemAll.size();
        if(size != 0) {
            for (FeatureBenefitTemplateItem featureBenefitTemplateItem : featureBenefitTemplateItemAll) {
            	featureBenifitTemplateId = featureBenefitTemplateItem.getFeatureBenefitTemplate().getId();
            	featureBenifitTemplateItemId = featureBenefitTemplateItem.getId();
                break;
            }
        }
	}
	
	
	@Test
    public void findByFeatureBenifitTemplatItemIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-template-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+featureBenifitTemplateItemId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/feature-benefit-template-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+featureBenifitTemplateItemId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void findByFeatureBenifitTemplateIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/feature-benefit-template-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/template/"+featureBenifitTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
//        else {
//            mockMvc.perform(get("/feature-benefit-template-item/"+ConstantsTest.DEFAULT_TENANT_ID+"/template/"+featureBenifitTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
//        }
    }
*/
}
