package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;

/**
 * Interest Rate Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface InterestRateTypeService {

	/**
	 * @author Senitha
	 * 
	 * Find all Interest Rate Type
	 * @return -JSON array of all Interest Rate Type
	 * 
	 * */
	public List<InterestRateType> getAll();
	
	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by ID
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	public Optional<InterestRateType> getById(Long id);
	
	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by code
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	public Optional<InterestRateType>getByCode(String code);
	
	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by name
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	public List<InterestRateType> getByName(String name);
	
	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by status
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	public List<InterestRateType> getByStatus(String status);
	
	/**
	 * @author Senitha
	 * 
	 * Insert Interest Rate Type
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public InterestRateType addInterestRateType(String tenantId, AddBaseRequest addBaseRequest);
	
	/**
	 * @author Senitha
	 * 
	 * Update Interest Rate Type
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	public InterestRateType updateInterestRateType(String tenantId, UpdateBaseRequest updateBaseRequest) ;

}
