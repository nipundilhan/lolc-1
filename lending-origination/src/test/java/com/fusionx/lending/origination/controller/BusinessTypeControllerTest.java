package com.fusionx.lending.origination.controller;

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

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessTypeControllerTest {

	/*@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BusinessTypeService businessTypeService;*/

	int size = 0;
	int id = -1;
	String status = null;
	String name = null;
	String code = null;

	/*@Before
	public void init() {
		List<BusinessType> businessType = businessTypeService.findAll();
		size = businessType.size();
		if (businessType.size() != 0) {
			for (BusinessType forBusinessType : businessType) {
				id = forBusinessType.getId().intValue();
				status = forBusinessType.getStatus().toString();
				name = forBusinessType.getName().toString();
				code = forBusinessType.getCode().toString();
				break;
			}
		}
	}

	@Test
	public void getAllBusinessTypesTest() throws Exception {
		if (id != -1) {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		}
	}

	@Test
	public void getBusinessTypeByIdTest() throws Exception {
		if (id != -1) {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
					.andExpect(jsonPath("id", is(id)));
		} else {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getBusinessTypesByStatusTest() throws Exception {
		if (status != null) {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getBusinessTypesByNameTest() throws Exception {
		if (status != null) {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getBusinessTypesByCodeTest() throws Exception {
		if (status != null) {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/business-type/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code + ""))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}*/
	
}
