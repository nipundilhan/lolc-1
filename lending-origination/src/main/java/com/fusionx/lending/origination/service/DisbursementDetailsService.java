package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.DisbursementDetails;
import com.fusionx.lending.origination.resource.DisbursementAddRequestResource;

/**
 * Disbursement Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface DisbursementDetailsService {
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the DisbursementDetails
	 */
	
	public List<DisbursementDetails> findByStatus(String tenantId, String status);
	
	/**
	 * Find by Customer id.
	 *
	 * @param id - the Customer id
	 * @return the DisbursementDetails
	 */
	public List<DisbursementDetails> findByCustomerId(String tenantId, Long id);
	
	/**
	 * Find by Lead Info id.
	 *
	 * @param id - the Lead Info id
	 * @return the DisbursementDetails
	 */
	public List<DisbursementDetails> findByLeadInfoId(String tenantId, Long id);
	
	/**
	 * Save and validate Other details.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param createdUser - the lead info id
	 * @param DisbursementAddRequestResource - the disbursement details add resource
	 * @return the id
	 */
	public void saveAndValidateDisbursementDetails(String tenantId, String createdUser, DisbursementAddRequestResource disbursementAddRequestResource);

	public Optional<DisbursementDetails>findById(Long id);
}
