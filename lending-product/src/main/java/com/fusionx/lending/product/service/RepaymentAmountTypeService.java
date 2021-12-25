package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.RepaymentAmountType;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;



/**
 * Repayment Amount Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-06-2021     FX-6619 		FX-6669     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RepaymentAmountTypeService {

	/**
	 * 
	 * Find all Repayment  Amount Type
	 * @author RavishikaS
	 * @return -JSON array of all RepaymentAmountType
	 * 
	 * */
	public List<RepaymentAmountType> getAll();
	
	/**
	 * 
	 * Find Repayment Amount Type by ID
	 * @author RavishikaS
	 * @return -JSON array of RepaymentAmountType
	 * 
	 * */
	public Optional<RepaymentAmountType> getById(Long id);
	
	/**
	 * 
	 * Find Repayment Amount Type by code
	 * @author RavishikaS
	 * @return -JSON array of RepaymentAmountType
	 * 
	 * */
	public Optional<RepaymentAmountType>getByCode(String code);
	
	
	/**
	 * 
	 * Find Repayment Amount Type by status
	 * @author RavishikaS
	 * @return -JSON array of RepaymentAmountType
	 * 
	 * */
	public List<RepaymentAmountType> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Repayment Amount Type
	 * @author RavishikaS
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public RepaymentAmountType addRepaymentAmountType(String tenantId , CommonAddResource commonAddResource);

	/**
	 * 
	 * Update Repayment Amount Type
	 * @author RavishikaS
	 * @param  - CommonUpdateResource
	 * @return - Successfully updated
	 * 
	 * */
	public RepaymentAmountType updateRepaymentAmountType(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
