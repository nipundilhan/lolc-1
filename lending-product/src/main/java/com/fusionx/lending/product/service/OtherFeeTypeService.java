package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.resources.OtherFeeTypeAddResource;
import com.fusionx.lending.product.resources.OtherFeeTypeUpdateResource;

/**
 * Other Fee Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherFeeTypeService {

	/**
	 * @author Senitha
	 * 
	 * Find all Other Fee Type
	 * @return -JSON array of all Other Fee Type
	 * 
	 * */
	public List<OtherFeeType> getAll(String tenantId);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Type by ID
	 * @return -JSON array of Other Fee Type
	 * 
	 * */
	public Optional<OtherFeeType> getById (String tenantId, Long id);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Type by code
	 * @return -JSON array of Other Fee Type
	 * 
	 * */
	public Optional<OtherFeeType>getByCode (String tenantId, String code);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Type by name
	 * @return -JSON array of Other Fee Type
	 * 
	 * */
	public List<OtherFeeType> getByName (String tenantId, String name);
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Type by status
	 * @return -JSON array of Other Fee Type
	 * 
	 * */
	public List<OtherFeeType> getByStatus (String tenantId, String status);
	
	/**
	 * @author Senitha
	 * 
	 * Insert Other Fee Type
	 * @param  - AddRecoveryFeeTypeResource
	 * @return - Successfully saved
	 * 
	 * */
	public OtherFeeType addOtherFeeType(String tenantId , OtherFeeTypeAddResource otherFeeTypeAddResource);

	/**
	 * @author Senitha
	 * 
	 * Update Other Fee Type
	 * @param  - UpdateRecoveryFeeTypeResource
	 * @return - Successfully saved
	 * 
	 * */
	public OtherFeeType updateOtherFeeType(String tenantId, OtherFeeTypeUpdateResource otherFeeTypeUpdateResource);
	
}
