package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;
import com.fusionx.lending.product.domain.EligibilityTemplateIndustryPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityTemplateIndustryAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateIndustryUpdateResource;

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
public interface EligibilityTemplateIndustryService {

	/**
	 * 
	 * Find all EligibilityTemplateIndustry
	 * 
	 * @author Dilki
	 * @return -JSON array of all EligibilityTemplateIndustry
	 * 
	 */
	public List<EligibilityTemplateIndustry> getAll();

	/**
	 * 
	 * Find EligibilityTemplateIndustry by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateIndustry
	 * 
	 */
	public Optional<EligibilityTemplateIndustry> getById(Long id);

	/**
	 * 
	 * Find EligibilityTemplateIndustry by status
	 * 
	 * @author Dilki
	 * @return -JSON array of EligibilityTemplateIndustry
	 * 
	 */
	public List<EligibilityTemplateIndustry> getByStatus(String status);
	
	/**
	 * 
	 * Insert EligibilityTemplateIndustry
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateIndustryAddResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateIndustry addEligibilityTemplateIndustry(String tenantId,
			EligibilityTemplateIndustryAddResource eligibilityTemplateIndustryAddResource);

	/**
	 * 
	 * Update EligibilityTemplateIndustry
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateIndustryUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateIndustry updateEligibilityTemplateIndustry(String tenantId, Long id,
			EligibilityTemplateIndustryUpdateResource eligibilityTemplateIndustryUpdateResource);

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
	 * Find Pending EligibilityTemplateIndustry by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateIndustry Pending
	 * 
	 */
	public Optional<EligibilityTemplateIndustryPending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search EligibilityTemplateIndustry Pending
	 * 
	 * @author Dilki
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable EligibilityTemplateIndustryPending
	 * 
	 */
	public Page<EligibilityTemplateIndustryPending> searchEligibilityTemplateIndustryPending(String searchq,
			String status, String approveStatus, Pageable pageable);
}
