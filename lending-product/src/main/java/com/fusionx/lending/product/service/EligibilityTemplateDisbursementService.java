package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateDisbursement;
import com.fusionx.lending.product.domain.EligibilityTemplateDisbursementPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementUpdateResource;

/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Service
public interface EligibilityTemplateDisbursementService {

	/**
	 * 
	 * Find all EligibilityTemplateDisbursment
	 * 
	 * @author Dilki
	 * @return -JSON array of all EligibilityTemplateDisbursment
	 * 
	 */
	public List<EligibilityTemplateDisbursement> getAll();

	/**
	 * 
	 * Find EligibilityTemplateDisbursment by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateDisbursment
	 * 
	 */
	public Optional<EligibilityTemplateDisbursement> getById(Long id);

	/**
	 * 
	 * Find EligibilityTemplateDisbursment by status
	 * 
	 * @author Dilki
	 * @return -JSON array of EligibilityTemplateDisbursment
	 * 
	 */
	public List<EligibilityTemplateDisbursement> getByStatus(String status); 
	
	/**
	 * 
	 * Insert EligibilityTemplateDisbursment
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateDisbursmentAddResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateDisbursement addEligibilityTemplateDisbursment(String tenantId,
			EligibilityTemplateDisbursementAddResource eligibilityTemplateDisbursementAddResource);

	/**
	 * 
	 * Update EligibilityTemplateDisbursment
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateDisbursmentUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateDisbursement updateEligibilityTemplateDisbursment(String tenantId, Long id,
			EligibilityTemplateDisbursementUpdateResource eligibilityTemplateDisbursementUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * 
	 * @author Dilki
	 * @param - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	/**
	 * 
	 * Find Pending EligibilityTemplateDisbursment by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateDisbursment Pending
	 * 
	 */
	public Optional<EligibilityTemplateDisbursementPending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search EligibilityTemplateDisbursment Pending
	 * 
	 * @author Dilki
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable EligibilityTemplateDisbursmentPending
	 * 
	 */
	public Page<EligibilityTemplateDisbursementPending> searchEligibilityTemplateDisbursementPending(String searchq,
			String status, String approveStatus, Pageable pageable);
}
