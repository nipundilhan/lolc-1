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
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.origination.repository.BusinessRiskTypeRepository;
import com.fusionx.lending.origination.repository.BusinessSubCenterProductMapRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessRiskTypeControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private BusinessRiskTypeRepository businessRiskTypeRepository;
	
	int size=0;
	Long id = 0l;
	String status="ACTIVE";
	String code="CD01";
	String name="name";
	
	@Before
	public void init() {
		List<BusinessRiskType> businessRiskTypeList = businessRiskTypeRepository.findAll();
		size = businessRiskTypeList.size();
		if (businessRiskTypeList.size() != 0) {
			for (BusinessRiskType businessRiskType : businessRiskTypeList) {
				id = businessRiskType.getId();
				code = businessRiskType.getCode();
				status = businessRiskType.getStatus().toString();
				name = businessRiskType.getName();
				break;
			}
		}
	}
	
	

	
	@Test
    public void getByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAllTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getByNameTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/name/"+name)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	
	@Test
    public void getByCodeTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }
        else {
            mockMvc.perform(get("/business-risk-type/"+ConstantsTest.DEFAULT_TENANT_ID+"/code/"+code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
