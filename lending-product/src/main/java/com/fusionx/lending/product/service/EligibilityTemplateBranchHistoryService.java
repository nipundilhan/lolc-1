package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateBranch;

/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
@Service
public interface EligibilityTemplateBranchHistoryService {

	/**
	 * @param tenantId
	 * @param eligibilityTemplateBranch
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, EligibilityTemplateBranch eligibilityTemplateBranch,
			String historyCreatedUser);

}
