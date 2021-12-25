package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;

/**
 * Other Fee Rate Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherFeeRateTypeService {

	/**
	 * @author Senitha
	 * 
	 * Find all Other Fee Rate Type
	 * @return -JSON array of all Other Fee Rate Type
	 * 
	 * */
	List<OtherFeeRateType> getAll();
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by ID
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	public Optional<OtherFeeRateType> getById(Long id);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by code
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	public Optional<OtherFeeRateType>getByCode(String code);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by name
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	public List<OtherFeeRateType> getByName(String name);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type status
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	public List<OtherFeeRateType> getByStatus(String status);
	
	/**
	 * @author Senitha
	 * 
	 * Insert Other Fee Rate Type
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public OtherFeeRateType addOtherFeeRateType(String tenantId, AddBaseRequest addBaseRequest);
	
	/**
	 * @author Senitha
	 * 
	 * Update Other Fee Rate Type
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public OtherFeeRateType updateOtherFeeRateType(String tenantId, UpdateBaseRequest updateBaseRequest) ;
}
