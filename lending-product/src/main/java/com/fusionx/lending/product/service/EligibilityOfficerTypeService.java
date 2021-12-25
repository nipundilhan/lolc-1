package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityOfficerType;
import com.fusionx.lending.product.domain.EligibilityOfficerTypePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeAddResource;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeUpdateResource;

/**
 * Eligibility Officer Type Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityOfficerTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<EligibilityOfficerType> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the optional
	 */
	public Optional<EligibilityOfficerType> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the list
	 */
	public List<EligibilityOfficerType> findByStatus(String status);
	
	/**
	 * Find by eligibility id.
	 *
	 * @param eligibilityId - the eligibility id
	 * @return the list
	 */
	public List<EligibilityOfficerType> findByEligibilityId(Long eligibilityId);
	
	/**
	 * Find by officer type id.
	 *
	 * @param officerTypeId - the officer type id
	 * @return the list
	 */
	public List<EligibilityOfficerType> findByOfficerTypeId(Long officerTypeId);
	
	/**
	 * Save and validate eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param eligibilityOfficerTypeAddResource - the eligibility officer type add resource
	 * @return the long
	 */
	public Long saveAndValidateEligibilityOfficerType(String tenantId, String createdUser, EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource);

	/**
	 * Update and validate eligibility officer type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param eligibilityOfficerTypeUpdateResource - the eligibility officer type update resource
	 * @return the long
	 */
	public Long updateAndValidateEligibilityOfficerType(String tenantId, String createdUser, Long id, EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource);
	
	
	/**
	 * Approve reject.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param workflowStatus - the workflow status
	 * @return the boolean
	 */
	public Boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	
	/**
	 * Gets the by pending id.
	 *
	 * @param pendingId - the pending id
	 * @return the by pending id
	 */
	public Optional<EligibilityOfficerTypePending> getByPendingId(Long pendingId);
	
	
	/**
	 * Search eligibility officer type.
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<EligibilityOfficerTypePending> searchEligibilityOfficerType(String searchq, String status, String approveStatus, Pageable pageable);

}
