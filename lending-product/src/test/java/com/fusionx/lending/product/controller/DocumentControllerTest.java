package com.fusionx.lending.product.controller;

//import com.fusionx.lending.product.ConstantsTest;
//import com.fusionx.lending.product.domain.Document;
//import com.fusionx.lending.product.enums.CommonStatus;
//import com.fusionx.lending.product.service.DocumentService;
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
 * document service
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

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class DocumentControllerTest {
/*    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentService documentService;

    int size = 0;
    int id = 0;
    String lendingAccountId;
    CommonStatus status = null;

    @Before
    public void init() {
        List<Document> documents = documentService.getAll();
        size = documents.size();
        if (size != 0) {
            for (Document document : documents) {
                id = document.getId().intValue();
                status = document.getStatus();
                lendingAccountId = document.getLendingAccountDetail().getId().toString();
                break;
            }
        }
    }

    @Test
    public void getAllDocumentTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getDocumentByIdTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/" + id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getDocumentByStatusTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/status/" + status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }

    @Test
    public void getDocumentByLendingAccountTest() throws Exception {
        if (size != 0) {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        } else {
            mockMvc.perform(get("/document/" + ConstantsTest.DEFAULT_TENANT_ID + "/lending-account/" + lendingAccountId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }*/
}
