package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.resources.FeeChargeDetailsAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailsUpdateResource;
import com.fusionx.lending.product.resources.PenalInterestTypeAddResource;
import com.fusionx.lending.product.resources.PenalInterestTypeUpdateResource;

/**
 * Penal Interest Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-08-2020                            Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface PenalInterestTypeService {

	/**
	 * 
	 * Find all PenalInterestType
	 * @author Dilhan
	 * @return -JSON array of all PenalInterestType
	 * 
	 * */
	public List<PenalInterestType> getAll();
	
	/**
	 * 
	 * Find PenalInterestType  by ID
	 * @author Dilhan
	 * @return -JSON array of PenalInterestType
	 * 
	 * */
	public Optional<PenalInterestType> getById(Long id);
	
	/**
	 * 
	 * Find PenalInterestType by code
	 * @author Dilhan
	 * @return -JSON array of PenalInterestType
	 * 
	 * */
	public Optional<PenalInterestType>getByCode(String code);
	
	/**
	 * 
	 * Find PenalInterestType by status
	 * @author Dilhan
	 * @return -JSON array of PenalInterestType 
	 * 
	 * */
	public List<PenalInterestType> getByStatus(String status);
	/**
	 * 
	 * Find PenalInterestType by name
	 * @author Dilhan
	 * @return -JSON array of PenalInterestType
	 * 
	 * */
	public List<PenalInterestType> getByName(String name);
	
	
	/**
	 * 
	 * Insert PenalInterestType
	 * @author Dilhan
	 * @param  - PenalInterestTypeAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public PenalInterestType addPenalInterestType(String tenantId , PenalInterestTypeAddResource penalInterestTypeAddResource);

	/**
	 * 
	 * Update PenalInterestType
	 * @author Dilhan
	 * @param  - PenalInterestTypeUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public PenalInterestType updatePenalInterestType(String tenantId, Long id, PenalInterestTypeUpdateResource penalInterestTypeUpdateResource);

}
