package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.RepaymentFrequency;

/**
 * Repayment Frequency History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021                            Dilki        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RepaymentFrequencyHistoryService {

	/**
	 * @param tenantId
	 * @param Repayment          Frequency History
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, RepaymentFrequency repaymentFrequency, String historyCreatedUser);

}
