package com.fusionx.lending.product.service;

/**
 * Interest Template History Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTemplate;


@Service
public interface InterestTemplateHistoryService {
	
	/**
	 * @param tenantId
	 * @param interestTemplate
	 * @param historyCreatedUser
	 */
	public void insertinterestTemplateHistory(String tenantId, InterestTemplate interestTemplate, String historyCreatedUser);

}
