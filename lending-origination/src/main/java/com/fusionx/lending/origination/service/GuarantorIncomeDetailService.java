package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.GuarantorIncomeAddResource;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface GuarantorIncomeDetailService {
	
	/**
	 * 
	 * Add Guarantor Income Details
	 * @author Sanatha
	 * @param  - GuarantorIncomeAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public GuarantorIncomeAddResource addGuarantorIncomeDetail(String tenantId , GuarantorIncomeAddResource guarantorIncomeAddResource);

}
