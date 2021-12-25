package com.fusionx.lending.product.service;
/**
 * Master Def Currency Eligibility History Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterCurrency;


@Service
public interface MasterCurrencyHistoryService {
	
	/**
	 * @param tenantId
	 * @param MasterCurrency
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, MasterCurrency masterCurrency, String historyCreatedUser);


}
