package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityCurrency;
import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityCurrencyAddResource;
import com.fusionx.lending.product.resources.EligibilityCurrencyUpdateResource;

/**
 * Eligibility Currency Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityCurrencyService {

	
	/**
	 * Find all.
	 *
	 * @param tenantId - the tenant id
	 * @return the list
	 */
	public List<EligibilityCurrency> findAll(String tenantId);
	
	
	/**
	 * Find by id.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @return the optional
	 */
	public Optional<EligibilityCurrency> findById(String tenantId, Long id);
	
	
	/**
	 * Find by status.
	 *
	 * @param tenantId - the tenant id
	 * @param status - the status
	 * @return the list
	 */
	public List<EligibilityCurrency> findByStatus(String tenantId, String status);
	
	
	/**
	 * Find by eligibility id.
	 *
	 * @param tenantId - the tenant id
	 * @param eligibilityId - the eligibility id
	 * @return the list
	 */
	public List<EligibilityCurrency> findByEligibilityId(String tenantId, Long eligibilityId);
	
	
	/**
	 * Find by currency id.
	 *
	 * @param tenantId - the tenant id
	 * @param currencyId - the currency id
	 * @return the list
	 */
	public List<EligibilityCurrency> findByCurrencyId(String tenantId, Long currencyId);
	
	
	/**
	 * Save and validate eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param eligibilityCurrencyAddResource - the eligibility currency add resource
	 * @return the long
	 */
	public Long saveAndValidateEligibilityCurrency(String tenantId, String createdUser, EligibilityCurrencyAddResource eligibilityCurrencyAddResource);

	
	/**
	 * Update and validate eligibility currency.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param eligibilityCurrencyUpdateResource - the eligibility currency update resource
	 * @return the long
	 */
	public Long updateAndValidateEligibilityCurrency(String tenantId, String createdUser, Long id, EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource);

	
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
	public Optional<EligibilityCurrencyPending> getByPendingId(Long pendingId);
	
	
	/**
	 * Search eligibility currency.
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<EligibilityCurrencyPending> searchEligibilityCurrency(String searchq, String status, String approveStatus, Pageable pageable);

}
