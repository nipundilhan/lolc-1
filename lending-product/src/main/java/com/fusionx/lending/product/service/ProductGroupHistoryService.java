package com.fusionx.lending.product.service;

/**
 * Product Group Histort Service
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

import com.fusionx.lending.product.domain.ProductGroup;


@Service
public interface ProductGroupHistoryService {
	
	/**
	 * @param tenantId
	 * @param productGroup
	 * @param historyCreatedUser
	 */
	public void insertProductGroupHistory(String tenantId, ProductGroup productGroup, String historyCreatedUser);

}
