package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.resource.ApprovalCategoryAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryUpdateResource;

/**
 * Approval Category Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ApprovalCategoryService {

	/**
	 * Find all.
	 *
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<ApprovalCategory> findAll(Pageable pageable);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessType
	 */
	public Optional<ApprovalCategory> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the ApprovalCategory
	 */
	
	public List<ApprovalCategory> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the ApprovalCategory
	 */
	public List<ApprovalCategory> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<ApprovalCategory> findByCode(String code);
	
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
	public Long saveAndValidateApprovalCategory(String tenantId, String createdUser, ApprovalCategoryAddResource ApprovalCategoryAddResource);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param ApprovalCategoryUpdateResource - the business type update resource
	 * @return the id
	 */
	public Long updateAndValidateApprovalCategory(String tenantId, String createdUser, Long id, ApprovalCategoryUpdateResource ApprovalCategoryUpdateResource);

	/**
	 * Search business type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ApprovalCategory> searchApprovalCategory(String searchq, String name, String code, Pageable pageable);
	
	public List<ApprovalCategory> findAll();
}
