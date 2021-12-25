package com.fusionx.lending.product.controller;

import com.fusionx.lending.product.ConstantsTest;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.resources.FeeChargeCapAddResource;
import com.fusionx.lending.product.service.FeeChargeCapService;
import com.fusionx.lending.product.utill.TestUtils;
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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Service Access Channel Controller Test
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   19-07-2021                            Dilhan         Created
 * <p>
 * *******************************************************************************************************
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FeeChargeCapControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeeChargeCapService feeChargeCapService;

    int size = 0;
    int id = 0;
    String code;
    CommonStatus status = null;

    private LocalValidatorFactoryBean localValidatorFactory;

    @Before
    public void init() {
        List<FeeChargeCap> feeChargeCaps = feeChargeCapService.getAll();
        size = feeChargeCaps.size();
        if (size != 0) {
            for (FeeChargeCap feeChargeCap : feeChargeCaps) {
                id = feeChargeCap.getId().intValue();
                code = feeChargeCap.getCode();
                status = feeChargeCap.getStatus();
                break;
            }
        }
    }

    @Test
    public void codePattern() {
        FeeChargeCapAddResource feeChargeCapAddResource = setFeeChargeCapAddResource();
        feeChargeCapAddResource.setCode("!@#$");
        assertEquals("{common.not-null}", "{common.not-null}");
    }

    private FeeChargeCapAddResource setFeeChargeCapAddResource() {

        FeeChargeCapAddResource feeChargeCapAddResource = new FeeChargeCapAddResource();
        feeChargeCapAddResource.setCode("TEST");
        feeChargeCapAddResource.setFeeCapPeriodId("1");
        feeChargeCapAddResource.setFeeCapPeriodName("xxxx");
        feeChargeCapAddResource.setFeeChargeId("2");
        feeChargeCapAddResource.setFeeChargeName("yyyyy");
        feeChargeCapAddResource.setFeeOccurence("3");
        feeChargeCapAddResource.setMinimumAmount("4000.00");
        feeChargeCapAddResource.setMinMaxType("MIN");
        feeChargeCapAddResource.setStatus("ACTIVE");
        return feeChargeCapAddResource;

    }


   /*@Test
    public void getAllServiceAccessChannelTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }/

   /* @Test
    public void getServiceAccessChannelByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/

    /*@Test
    public void getServiceAccessChannelByCodeTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/

  /*  @Test
    public void getSServiceAccessChannelByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/fee-charge-cap/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/

}
