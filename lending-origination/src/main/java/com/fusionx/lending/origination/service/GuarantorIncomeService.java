package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.GuarantorIncomeRequestResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface GuarantorIncomeService {
	
	/**
	 * Save and Update Guarantor Income 
	 *
	 * @param tenantId
	 * @param guarantorId
	 * @param GuarantorIncomeRequestResource
	 */
	public void saveAndUpdateGuarantorIncomeExpense(String tenantId, Long guarantorId, GuarantorIncomeRequestResource guarantorIncomeRequestResource);
	
	public IncomeExpenseSummaryResponseResource incomeExpenseSummary(Long customerId);

}
