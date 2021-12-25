package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructure;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructurePending;
import com.fusionx.lending.product.enums.WorkflowStatus; 
import com.fusionx.lending.product.resources.EligibilityTemplateLegalStructureAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateLegalStructureUpdateResource;

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
public interface EligibilityTemplateLegalStructureService {

	/**
	 * 
	 * Find all EligibilityTemplateLegalStructure
	 * 
	 * @author Dilki
	 * @return -JSON array of all EligibilityTemplateLegalStructure
	 * 
	 */
	public List<EligibilityTemplateLegalStructure> getAll();

	/**
	 * 
	 * Find EligibilityTemplateLegalStructure by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateLegalStructure
	 * 
	 */
	public Optional<EligibilityTemplateLegalStructure> getById(Long id);

	/**
	 * 
	 * Find EligibilityTemplateLegalStructure by status
	 * 
	 * @author Dilki
	 * @return -JSON array of EligibilityTemplateLegalStructure
	 * 
	 */
	public List<EligibilityTemplateLegalStructure> getByStatus(String status);
	
	/**
	 * 
	 * Insert EligibilityTemplateLegalStructure
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateLegalStructureAddResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateLegalStructure addEligibilityTemplateLegalStructure(String tenantId,
			EligibilityTemplateLegalStructureAddResource eligibilityTemplateLegalStructureAddResource);

	/**
	 * 
	 * Update EligibilityTemplateLegalStructure
	 * 
	 * @author Dilki
	 * @param - EligibilityTemplateLegalStructureUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public EligibilityTemplateLegalStructure updateEligibilityTemplateLegalStructure(String tenantId, Long id,
			EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateLegalStructureUpdateResource);

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
	 * Find Pending EligibilityTemplateLegalStructure by ID
	 * 
	 * @author Dilki
	 * @return -JSON object of EligibilityTemplateLegalStructure Pending
	 * 
	 */
	public Optional<EligibilityTemplateLegalStructurePending> getByPendingId(Long pendingId);

	/**
	 * 
	 * Search EligibilityTemplateLegalStructure Pending
	 * 
	 * @author Dilki
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable EligibilityTemplateLegalStructurePending
	 * 
	 */
	public Page<EligibilityTemplateLegalStructurePending> searchEligibilityTemplateLegalStructurePending(String searchq,
			String status, String approveStatus, Pageable pageable);
}
