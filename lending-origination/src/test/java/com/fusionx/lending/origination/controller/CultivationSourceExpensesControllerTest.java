package com.fusionx.lending.origination.controller;

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

import com.fusionx.lending.origination.ConstantsTest;
import com.fusionx.lending.origination.domain.CultivationIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.repository.CultivationIncomeExpensesRepository;

/**
 * Cultivation Source Expenses Controller Test
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021             	 FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CultivationSourceExpensesControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	private CultivationIncomeExpensesRepository cultivationIncomeExpensesRepository;
	
	@Autowired
	public void setCultivationIncomeExpensesRepository(
			CultivationIncomeExpensesRepository cultivationIncomeExpensesRepository) {
		this.cultivationIncomeExpensesRepository = cultivationIncomeExpensesRepository;
	}
	
	int size=0;
	int id = 0;
	CommonStatus status= CommonStatus.ACTIVE;
	Long cultivationIncomeDetailsId = 1l;

	@Before
	public void init() {
		List<CultivationIncomeExpenses> cultivationIncomeExpenses = cultivationIncomeExpensesRepository.findAll();
        size = cultivationIncomeExpenses.size();
        if(size != 0) {
            for (CultivationIncomeExpenses cultivationIncomeExpense : cultivationIncomeExpenses) {
            	id = cultivationIncomeExpense.getId().intValue();
            	status = cultivationIncomeExpense.getStatus();
            	cultivationIncomeDetailsId = cultivationIncomeExpense.getCultivationIncomeDetails().getId();
                break;
            }
        }
	}
	
	
	@Test
    public void getCultivationIncomeDetailsByIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-source-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-source-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/"+id)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }
	
	
	//@Test
    public void getCultivationSourceIncomeDetailsByCultivationIncomeDetailsIdTest() throws Exception {
        if(size != 0) {
            mockMvc.perform(get("/cultivation-source-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/cultivation-income-details/"+cultivationIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
        }else {
            mockMvc.perform(get("/cultivation-source-expenses/"+ConstantsTest.DEFAULT_TENANT_ID+"/cultivation-income-details/"+cultivationIncomeDetailsId)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNoContent());
        }
    }


}
