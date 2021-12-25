package com.fusionx.lending.product.controller;

//import com.fusionx.lending.product.ConstantsTest;
//import com.fusionx.lending.product.domain.CheckList;
//import com.fusionx.lending.product.enums.CommonStatus;
//import com.fusionx.lending.product.service.CheckListService;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Check List service
 *
 * @author Rohan
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   26-10-2021  			     -          Rohan      Created
 * <p>
 * *******************************************************************************************************
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class CheckListControllerTest {
/*
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private CheckListService checkListService;

    int size = 0;
    int id = 0;
    String lendingAccountId;
    CommonStatus status = null;

    @Before
    public void init() {
        List<CheckList> checkLists = checkListService.getAll();
        size = checkLists.size();
        if (size != 0) {
            for (CheckList checkList : checkLists) {
                id = checkList.getId().intValue();
                status = checkList.getStatus();
                lendingAccountId = checkList.getLendingAccountDetail().getId().toString();
                break;
            }
        }
    }

    @Test
    public void getAllCheckListTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getCheckListByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getCheckListByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getCheckListByLendingAccountTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/check-list/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/
}
