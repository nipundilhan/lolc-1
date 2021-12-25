package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationIncomeType;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface CultivationIncomeTypeHistoryService {

	/**
	 * @param tenantId
	 * @param CultivationIncomeType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, CultivationIncomeType cultivationIncomeType, String historyCreatedUser);

}
