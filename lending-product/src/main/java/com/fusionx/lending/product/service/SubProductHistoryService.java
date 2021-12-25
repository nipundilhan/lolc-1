package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.SubProduct;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface SubProductHistoryService {
	
	/**
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param tenantId
	 * @param eligibilityHistory
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, SubProduct subProduct, String historyCreatedUser);

}
