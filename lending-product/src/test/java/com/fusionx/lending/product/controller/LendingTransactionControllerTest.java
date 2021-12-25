package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.ConstantsTest;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.domain.LendingTransaction;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.LendingTransactionService;
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

/**
 * LendingAccountDetailControllerTest
 *
 * @author Rohan
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-10-2021  		-               -      Thushan      Created
 * <p>
 * *******************************************************************************************************
 */

/*
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class LendingTransactionControllerTest {
/*    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LendingTransactionService lendingTransactionService;

    int size = 0;
    int id = 0;
    String accountId;
    CommonStatus status = null;

    @Before
    public void init() {
        List<LendingTransaction> lendingTransactionList = lendingTransactionService.getAll();
        size = lendingTransactionList.size();
        if (size != 0) {
            for (LendingTransaction lendingTransaction : lendingTransactionList) {
                id = lendingTransaction.getId().intValue();
                status = lendingTransaction.getStatus();
                accountId = String.valueOf(lendingTransaction.getLendingAccountId().getId());
                break;
            }
        }
    }


    @Test
    public void getLendingTransactionByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getLendingTransactionByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getLendingTransactionByLendingAccountIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + accountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/lending-transaction-controller/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + accountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
    */
}
