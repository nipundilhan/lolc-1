package com.fusionx.lending.origination.controller;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.service.BusinessTypeService;

import com.fusionx.lending.origination.domain.BusinessSubCenter;
import com.fusionx.lending.origination.repository.BusinessSubCenterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessSubCenterControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private BusinessSubCenterRepository  businessSubCenterRepository;
	
	int size=0;
	Long businessCenterId = 0l;
	Long id = 0l;
	String name="name";
	String code="LBO1";
	String status="ACTIVE";
	
	@Before
	public void init() {
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterRepository.findAll();
		size = businessSubCenterList.size();
		if (businessSubCenterList.size() != 0) {
			for (BusinessSubCenter businessSubCenter : businessSubCenterList) {
				id = businessSubCenter.getId();
				businessCenterId = businessSubCenter.getBusinessCenter().getId();
				status = businessSubCenter.getStatus().toString();
				name = businessSubCenter.getName().toString();
				code = businessSubCenter.getCode().toString();
				break;
			}
		}
	}
	
	@Test
	public void getAllTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
		}
	}

	@Test
	public void getByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}
	
	@Test
	public void getByBusinessCenterIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/business-center/" + businessCenterId + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/business-center/" + businessCenterId + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-sub-center/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}
	
	
	
	
}
