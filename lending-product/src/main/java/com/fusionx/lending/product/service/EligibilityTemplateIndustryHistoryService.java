package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;

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
public interface EligibilityTemplateIndustryHistoryService {

	/**
	 * @param tenantId
	 * @param eligibilityTemplateIndustry
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, EligibilityTemplateIndustry eligibilityTemplateIndustry,
			String historyCreatedUser);

}
