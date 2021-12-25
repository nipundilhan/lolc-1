package com.fusionx.lending.product.controller;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

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

import com.fusionx.lending.product.ConstantsTest;
import com.fusionx.lending.product.domain.ResidencyEligibilityNotes;
import com.fusionx.lending.product.service.ResidencyEligibilityNotesService;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)*/
public class ResidencyEligibilityNotesControllerTest {
	/*
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ResidencyEligibilityNotesService residencyEligibilityNotesService;
	
	int notesSize=0;
	int id = 0;
	int noteId = 0;
	String status=null;
	
	@Before
	public void init() {
		List<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesService.findAll();
		notesSize = residencyEligibilityNotes.size();
        if(notesSize != 0) {
            for (ResidencyEligibilityNotes residencyEligibilityNotesRec : residencyEligibilityNotes) {
            	noteId = residencyEligibilityNotesRec.getId().intValue();
            	id = residencyEligibilityNotesRec.getResidencyEligi().getId().intValue();
            	status = residencyEligibilityNotesRec.getStatus();
                break;
            }
        }
	}
	
	@Test
    public void getResidencyEligibilityNotesTest() throws Exception {
        if(notesSize != 0) {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/all")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityNotesByIdTest() throws Exception {
        if(notesSize != 0) {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+noteId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+noteId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityNotesByResidencyEligibilityIdTest() throws Exception {
        if(notesSize != 0) {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/residency-eligibility/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	@Test
    public void getResidencyEligibilityNotesByStatusTest() throws Exception {
        if(notesSize != 0) {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/residency-eligibility-notes/"+ConstantsTest.DEFAULT_TENANT_ID+"/status/"+status)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
*/

}
