package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.SalesAccessChannel;

/**
 * Sales Access Channel service
 * @author 	Piyumi
 * @Dat     07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SalesAccessChannelHistoryService {
	
	/**
	 * @param tenantId
	 * @param salesAccessChannel
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, SalesAccessChannel salesAccessChannel, String historyCreatedUser);

}
