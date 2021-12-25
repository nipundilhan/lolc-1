package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.CustomerIncomeExpenseRequestResource;
import com.fusionx.lending.origination.resource.IncomeExpenseSummaryResponseResource;
/**
 * Customer Income Expense Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   13-05-2020      		     			MenukaJ       Created
 *    
 ********************************************************************************************************
 */
@Service
public interface CustomerIncomeExpenseService {

	
	/**
	 * Save Customer Income Expenses
	 *
	 * @param tenantId
	 * @param customerId
	 * @param CustomerIncomeExpenseRequestResource
	 */
	public void saveCustomerIncomeExpense(String tenantId, Long customerId, CustomerIncomeExpenseRequestResource customerIncomeExpenseRequestResource);
	
	//public SalaryIncomeSummaryResponseResource salaryIncomeSummaryResponseResource(Long customerId);
	
	public IncomeExpenseSummaryResponseResource incomeExpenseSummary(Long customerId);

}
