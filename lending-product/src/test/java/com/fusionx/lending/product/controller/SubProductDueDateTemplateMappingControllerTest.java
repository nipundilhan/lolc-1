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
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.repository.SubProductRepository;


/**
 * Sub Product Due Date Template Mapping Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021    FXL-155  	 FXL-933	Piyumi      Created
 *    
 ********************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class SubProductDueDateTemplateMappingControllerTest {
/*	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	int size=0;
	Long dueDateTemplateId = 0l;
	@Before
	public void init() {
		List<SubProduct> subProducts = subProductRepository.findByDueDateTemplateIdNotNull();
        size = subProducts.size();
        if(size > 0) {
            for (SubProduct subProduct : subProducts) {
            	dueDateTemplateId = subProduct.getDueDateTemplate().getId();
                break;
            }
        }
	}
	
	@Test
    public void getByDueDateTemplateIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/sub-product-due-date-template/"+ ConstantsTest.DEFAULT_TENANT_ID+"/"+dueDateTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/sub-product-due-date-template/"+ ConstantsTest.DEFAULT_TENANT_ID+"/"+dueDateTemplateId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
