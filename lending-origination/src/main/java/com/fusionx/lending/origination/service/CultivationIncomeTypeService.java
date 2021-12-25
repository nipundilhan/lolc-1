package com.fusionx.lending.origination.service;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;

@Service
public interface CultivationIncomeTypeService {

	/**
	 * 
	 * Find all CultivationIncomeType
	 * 
	 * @author Dilki
	 * @return SON array of all CultivationIncomeType
	 * 
	 */
	public List<CultivationIncomeType> getAll();

	/**
	 * 
	 * Find CultivationIncomeType by ID
	 * 
	 * @author Dilki
	 * @return JSON array of CultivationIncomeType
	 * 
	 */
	public Optional<CultivationIncomeType> getById(Long id);

	/**
	 * 
	 * Find CultivationIncomeType by code
	 * 
	 * @author Dilki
	 * @return JSON array of CultivationIncomeType
	 * 
	 */
	public Optional<CultivationIncomeType> getByCode(String code);

	/**
	 * 
	 * Find CultivationIncomeType by name
	 * 
	 * @author Dilki
	 * @return JSON array of CultivationIncomeType
	 * 
	 */
	public List<CultivationIncomeType> getByName(String name);

	/**
	 * 
	 * Find CultivationIncomeType by status
	 * 
	 * @author Dilki
	 * @return JSON array of CultivationIncomeType
	 * 
	 */
	public List<CultivationIncomeType> getByStatus(String status);

	/**
	 * 
	 * Insert CultivationIncomeType
	 * 
	 * @author Dilki
	 * @param CommonAddResource
	 * @return Successfully saved
	 * 
	 */
	public CultivationIncomeType addCultivationIncomeType(String tenantId, CommonAddResource commonAddResource);

	/**
	 * 
	 * Update CultivationIncomeType
	 * 
	 * @author Dilki
	 * @param CommonUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public CultivationIncomeType updateCultivationIncomeType(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource);
}
