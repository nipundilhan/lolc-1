package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CheckListItem;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface CheckListItemHistoryService {

	/**
	 * @param tenantId
	 * @param CheckListItem
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, CheckListItem checkListItem, String historyCreatedUser);

}
