package com.fusionx.lending.origination.service;


import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponse;

/**
 * 	Income Expense Summary Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   21-09-2021   FXL-115  	 FXL-786       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Service
public interface IncomeExpenseSummaryService {
	
	/**
	 * Find by customerId.
	 * 
	 * @param customerId - the customerId
	 * @return the IncomeExpenseSummaryResponse
	 */
	IncomeExpenseSummaryResponse findByCustomerId(Long customerId);
	
	/**
	 * Find by leadId.
	 * 
	 * @param leadId - the leadId
	 * @return the IncomeExpenseSummaryResponse
	 */
	IncomeExpenseSummaryResponse findByLeadId(Long leadId);

}
