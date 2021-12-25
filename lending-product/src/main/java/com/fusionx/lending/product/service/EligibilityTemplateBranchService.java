package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateBranch;
import com.fusionx.lending.product.domain.EligibilityTemplateBranchPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityTemplateBranchAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateBranchUpdateResource;

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
public interface EligibilityTemplateBranchService {

	/**
	 * 
	 * Find all EligibilityTemplateBranch
	 * 
	 * @author Dilki
	 * @return JSON array of all EligibilityTemplateBranch
	 * 
	 */
	public List<EligibilityTemplateBranch> getAll();

	/**
	 * 
	 * Find EligibilityTemplateBranch by ID
	 * 
	 * @author Dilki
	 * @return JSON object of EligibilityTemplateBranch
	 * 
	 */
	public Optional<EligibilityTemplateBranch> getById(Long id);

	/**
	 * 
	 * Find EligibilityTemplateBranch by status
	 * 
	 * @author Dilki
	 * @return JSON array of EligibilityTemplateBranch
	 * 
	 */
	public List<EligibilityTemplateBranch> getByStatus(String status); 
	
	/**
	 * 
	 * Insert EligibilityTemplateBranch
	 * 
	 * @author Dilki
	 * @param EligibilityTemplateBranchAddResource
	 * @return Successfully saved
	 * 
	 */
	public EligibilityTemplateBranch addEligibilityTemplateBranch(String tenantId,
			EligibilityTemplateBranchAddResource eligibilityTemplateBranchAddResource);

	/**
	 * 
	 * Update EligibilityTemplateBranch
	 * 
	 * @author Dilki
	 * @param EligibilityTemplateBranchUpdateResource
	 * @return Successfully saved
	 * 
	 */
	public EligibilityTemplateBranch updateEligibilityTemplateBranch(String tenantId, Long id,
			EligibilityTemplateBranchUpdateResource eligibilityTemplateBranchUpdateResource);

	/**
	 * 
	 * Approve Reject
	 * 
	 * @author Dilki
	 * @param tenantId
	 * @param workflowStatus
	 * @return Successfully saved
	 * 
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	/**
	 * 
	 * Find Pending EligibilityTemplateBranch by ID
	 * 
	 * @author Dilki
	 * @return JSON object of EligibilityTemplateBranch Pending
	 * 
	 */
	public Optional<EligibilityTemplateBranchPending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search EligibilityTemplateBranch Pending
	 * 
	 * @author Dilki
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return JSON Pageable EligibilityTemplateBranchPending
	 * 
	 */
	public Page<EligibilityTemplateBranchPending> searchEligibilityTemplateBranchPending(String searchq,
			String status, String approveStatus, Pageable pageable);
}
