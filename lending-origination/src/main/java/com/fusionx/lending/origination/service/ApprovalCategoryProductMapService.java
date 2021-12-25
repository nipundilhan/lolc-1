package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapAddResource;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapUpdateResource;

/**
 * Approval Category Map with Products Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ApprovalCategoryProductMapService {

	/**
	 * Find all.
	 *
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ApprovalCategoryProductMapping> findAll(Pageable pageable);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessType
	 */
	public Optional<ApprovalCategoryProductMapping> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the ApprovalCategory
	 */
	
	public List<ApprovalCategoryProductMapping> findByStatus(String status);

	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	//public Optional<ApprovalCategoryProductMapping> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param ApprovalCategoryAddResource - the business type add resource
	 * @return the id
	 */
	public void saveAndValidateApprovalCatProductMap(String tenantId, String createdUser, ApprovalCatProductMapAddResource approvalCatProductMapAddResource);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param ApprovalCategoryUpdateResource - the business type update resource
	 * @return the id
	 */
	public Long updateAndValidateApprovalCatProductMap(String tenantId, String createdUser, Long id, ApprovalCatProductMapUpdateResource approvalCatProductMapUpdateResource);

	/**
	 * Search business type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ApprovalCategoryProductMapping> searchByApprovalCategory(String searchq, String name, String code, Pageable pageable);
	
	public List<ApprovalCategoryProductMapping> findAll();
}
