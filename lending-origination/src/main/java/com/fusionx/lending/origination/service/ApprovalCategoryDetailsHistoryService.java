package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;

@Service
public interface ApprovalCategoryDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param ApprovalCategoryDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, ApprovalCategoryDetails approvalCategoryDetails, String historyCreatedUser);

}
