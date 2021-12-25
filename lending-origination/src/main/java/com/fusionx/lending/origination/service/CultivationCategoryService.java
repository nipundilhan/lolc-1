package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;

/**
 * Cultivation Category Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface CultivationCategoryService {

	/**
	 * 
	 * Find all Cultivation Category
	 * @author MenukaJ
	 * @return -JSON array of all Cultivation Category
	 * 
	 * */
	public List<CultivationCategory> getAll();
	
	/**
	 * 
	 * Find Cultivation Category by ID
	 * @author MenukaJ
	 * @return -JSON array of Cultivation Category
	 * 
	 * */
	public Optional<CultivationCategory> getById(Long id);
	
	/**
	 * 
	 * Find Cultivation Category by code
	 * @author MenukaJ
	 * @return -JSON array of Cultivation Category
	 * 
	 * */
	public Optional<CultivationCategory>getByCode(String code);
	
	/**
	 * 
	 * Find Cultivation Category by name
	 * @author MenukaJ
	 * @return -JSON array of Cultivation Category
	 * 
	 * */
	public List<CultivationCategory> getByName(String name);
	
	/**
	 * 
	 * Find Cultivation Category by status
	 * @author MenukaJ
	 * @return -JSON array of Cultivation Category
	 * 
	 * */
	public List<CultivationCategory> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Cultivation Category
	 * @author MenukaJ
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public CultivationCategory addCultivationCategory(String tenantId , CommonAddResource commonAddResource);

	/**
	 * 
	 * Update Cultivation Category
	 * @author MenukaJ
	 * @param  - CommonUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public CultivationCategory updateCultivationCategory(String tenantId, CommonUpdateResource commonUpdateResource);
}
