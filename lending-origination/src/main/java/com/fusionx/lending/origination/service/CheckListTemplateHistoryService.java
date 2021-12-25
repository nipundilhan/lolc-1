package com.fusionx.lending.origination.service;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CheckListTemplate;

@Service
public interface CheckListTemplateHistoryService {

	/**
	 * @param tenantId
	 * @param CheckListTemplate
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, CheckListTemplate checkListTemplate, String historyCreatedUser);

}
