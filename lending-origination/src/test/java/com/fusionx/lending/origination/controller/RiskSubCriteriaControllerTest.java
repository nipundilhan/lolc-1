package com.fusionx.lending.origination.controller;

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.service.RiskSubCriteriaService;
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
public class RiskSubCriteriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RiskSubCriteriaService riskSubCriteriaService;

    private int size=0;
    private int id = 0;
    private String name = null;
    private String code = null;
    private String status = null;

    @Before
    public void init() {
        List<RiskSubCriteria> riskSubCriteriaList = riskSubCriteriaService.findAll();
        size = riskSubCriteriaList.size();
        if (size != 0) {
            for (RiskSubCriteria riskSubCriteria : riskSubCriteriaList) {
                id = riskSubCriteria.getId().intValue();
                name = riskSubCriteria.getName();
                code = riskSubCriteria.getCode();
                status= riskSubCriteria.getStatus();
                break;
            }
        }
    }

    @Test
    public void getRiskSubCriteriaTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/all"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    public void getRiskSubCriteriaByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    public void getRiskSubCriteriaByCodeTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/code/" + code))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    public void getRiskSubCriteriaByNameTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/name/" + name))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent());
        }
    }

    @Test
    public void getRiskSubCriteriaByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/risk-sub-criteria/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isNoContent());
        }
    }

}
