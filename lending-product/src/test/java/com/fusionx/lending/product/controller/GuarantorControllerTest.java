package com.fusionx.lending.product.controller;

//import com.fusionx.lending.product.ConstantsTest;
//import com.fusionx.lending.product.domain.Guarantor;
//import com.fusionx.lending.product.enums.CommonStatus;
//import com.fusionx.lending.product.service.GuarantorService;
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
 * Guarantor service
 *
 * @author Rohan
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   27-10-2021  		-               -      Rohan      Created
 * <p>
 * *******************************************************************************************************
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class GuarantorControllerTest {
/*    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuarantorService guarantorService;

    int size = 0;
    int id = 0;
    String lendingAccountId;
    CommonStatus status = null;

    @Before
    public void init() {
        List<Guarantor> guarantors = guarantorService.getAll();
        size = guarantors.size();
        if (size != 0) {
            for (Guarantor guarantor : guarantors) {
                id = guarantor.getId().intValue();
                status = guarantor.getStatus();
                lendingAccountId = guarantor.getLendingAccountDetail().getId().toString();
                break;
            }
        }
    }


    @Test
    public void getGuarantorByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getGuarantorByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getGuarantorByLendingAccountTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/guarantor/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
}
