package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Approval Category Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryLevels;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsUpdateResource;

@Service
public interface ApprovalCategoryLevelsService {

	/**
	 * 
	 * Find all ApprovalCategoryLevels
	 * 
	 * @author Dilki
	 * @return JSON array of all ApprovalCategoryLevels
	 * 
	 */
	public List<ApprovalCategoryLevels> getAll();

	/**
	 * 
	 * Find ApprovalCategoryLevels by ID
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevels
	 * 
	 */
	public Optional<ApprovalCategoryLevels> getById(Long id);

	/**
	 * 
	 * Find ApprovalCategoryLevels by code
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevels
	 * 
	 */
	public Optional<ApprovalCategoryLevels> getByCode(String code);

	/**
	 * 
	 * Find ApprovalCategoryLevels by name
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevels
	 * 
	 */
	public List<ApprovalCategoryLevels> getByName(String name);

	/**
	 * 
	 * Find ApprovalCategoryLevels by status
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevels
	 * 
	 */
	public List<ApprovalCategoryLevels> getByStatus(String status);

	/**
	 * 
	 * Insert ApprovalCategoryLevels
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryLevelsAddResource
	 * @return Successfully saved
	 * 
	 */
	public ApprovalCategoryLevels addApprovalCategoryLevels(String tenantId,
			ApprovalCategoryLevelsAddResource commonAddResource);

	/**
	 * 
	 * Update ApprovalCategoryLevels
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryLevelsUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public ApprovalCategoryLevels updateApprovalCategoryLevels(String tenantId, Long id,
			ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource);
}
