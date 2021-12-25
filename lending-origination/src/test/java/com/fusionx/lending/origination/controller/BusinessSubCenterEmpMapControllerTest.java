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
import com.fusionx.lending.origination.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.origination.repository.BusinessSubCenterEmpMapRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessSubCenterEmpMapControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private BusinessSubCenterEmpMapRepository  businessSubCenterEmpMapRepository;
	
	int size=0;
	Long businessSubCenterId = 0l;
	Long id = 0l;
	String status="ACTIVE";
	
	@Before
	public void init() {
		List<BusinessSubCenterEmpMap> businessSubCenterEmpMapList = businessSubCenterEmpMapRepository.findAll();
		size = businessSubCenterEmpMapList.size();
		if (businessSubCenterEmpMapList.size() != 0) {
			for (BusinessSubCenterEmpMap businessSubCenterEmpMap : businessSubCenterEmpMapList) {
				id = businessSubCenterEmpMap.getId();
				businessSubCenterId = businessSubCenterEmpMap.getBusinessSubCenter().getId();
				status = businessSubCenterEmpMap.getStatus().toString();
				break;
			}
		}
	}
	
	
	@Test
    public void getByBusinessSubCenterIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-sub-center/"+businessSubCenterId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/business-sub-center/"+businessSubCenterId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

	
	
	@Test
    public void getByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	@Test
    public void getAllTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	
	@Test
    public void getByStatusTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/business-sub-center-employee/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
}
