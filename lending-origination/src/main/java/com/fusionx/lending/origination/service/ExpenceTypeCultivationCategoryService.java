package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryAddResource;
import com.fusionx.lending.origination.resource.ExpenceTypeCultivationCategoryUpdateResource;

@Service
public interface ExpenceTypeCultivationCategoryService {
	
	/**
	 * 
	 * Find all Expence Type Cultivation Category
	 * @author MenukaJ
	 * @return -JSON array of all Expence Type Cultivation Category
	 * 
	 * */
	public List<ExpenceTypeCultivationCategory> getAll();
	
	/**
	 * 
	 * Find Expence Type Cultivation Category by ID
	 * @author MenukaJ
	 * @return -JSON array of Expence Type Cultivation Category
	 * 
	 * */
	public Optional<ExpenceTypeCultivationCategory> getById(Long id);
	
	
	/**
	 * 
	 * Find Expence Type Cultivation Category by status
	 * @author MenukaJ
	 * @return -JSON array of Expence Type Cultivation Category
	 * 
	 * */
	public List<ExpenceTypeCultivationCategory> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Expence Type Cultivation Category
	 * @author MenukaJ
	 * @param  - ExpenceTypeCultivationCategoryAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public void addExpenceTypeCultivationCategory(String tenantId , ExpenceTypeCultivationCategoryAddResource expenceTypeCultivationCategoryAddResource);

	/**
	 * 
	 * Update Expence Type Cultivation Category
	 * @author MenukaJ
	 * @param  - ExpenceTypeCultivationCategoryUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public void updateExpenceTypeCultivationCategory(String tenantId, ExpenceTypeCultivationCategoryUpdateResource expenceTypeCultivationCategoryUpdateResource);

	
	/**
	 * Gets the by calti cat code.
	 *
	 * @param cultiCatCode the culti cat code
	 * @return list of ExpenceTypeCultivationCategory
	 */
	public List<ExpenceTypeCultivationCategory> getByCaltiCatCode(String cultiCatCode);

}
