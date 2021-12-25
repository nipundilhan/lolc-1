package com.fusionx.lending.origination.controller;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.service.CheckListItemService;
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

import java.util.List;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckListItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CheckListItemService checkListItemService;

	private int size = 0;
	private int id = 0;
	private String name = null;
	private String code = null;
	private CommonStatus status = null;

	@Before
	public void init() {
		List<CheckListItem> checkListItemList = checkListItemService.getAll();
		size = checkListItemList.size();
		if (size != 0) {
			for (CheckListItem checkListItem : checkListItemList) {
				id = checkListItem.getId().intValue();
				name = checkListItem.getName();
				code = checkListItem.getCode();
				status = checkListItem.getStatus();
				break;
			}
		}
	}

	@Test
	public void getCheckListItemTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getCheckListItemByIdTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getCheckListItemByCodeTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getCheckListItemByNameTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

	@Test
	public void getCheckListItemByStatusTest() throws Exception {
		if (size != 0) {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
		} else {
			mockMvc.perform(get("/check-list-item/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isNoContent());
		}
	}

}
