package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.SalaryIncomeType;
/**
 * Salary Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-08-2021   FXL-338 		 FXL-532 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface SalaryIncomeTypeHistoryService {

	/**
	 * @param tenantId
	 * @param SalaryIncomeType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, SalaryIncomeType salaryIncomeType, String historyCreatedUser);

}
