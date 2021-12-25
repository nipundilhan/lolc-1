package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Approval Category LevelDetails
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryLevelDetails;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsUpdateResource;

@Service
public interface ApprovalCategoryLevelDetailsService {

	/**
	 * 
	 * Find all ApprovalCategoryLevelDetails
	 * 
	 * @author Dilki
	 * @return JSON array of all ApprovalCategoryLevelDetails
	 * 
	 */
	public List<ApprovalCategoryLevelDetails> getAll();

	/**
	 * 
	 * Find ApprovalCategoryLevelDetails by ID
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevelDetails
	 * 
	 */
	public Optional<ApprovalCategoryLevelDetails> getById(Long id);

	/**
	 * 
	 * Find ApprovalCategoryLevelDetails by ApprovalCategoryDetailsId
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevelDetails
	 * 
	 */
	public List<ApprovalCategoryLevelDetails> getByApprovalCategoryDetailsId(Long approvalCategoryDetailsId);

	/**
	 * 
	 * Find ApprovalCategoryLevelDetails by code
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevelDetails
	 * 
	 */
	public Optional<ApprovalCategoryLevelDetails> getByCode(String code);

	/**
	 * 
	 * Find ApprovalCategoryLevelDetails by name
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevelDetails
	 * 
	 */
	public List<ApprovalCategoryLevelDetails> getByName(String name);

	/**
	 * 
	 * Find ApprovalCategoryLevelDetails by status
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryLevelDetails
	 * 
	 */
	public List<ApprovalCategoryLevelDetails> getByStatus(String status);

	/**
	 * 
	 * Insert ApprovalCategoryLevelDetails
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryLevelDetailsAddResource
	 * @return Successfully saved
	 * 
	 */
	public ApprovalCategoryLevelDetails addApprovalCategoryLevelDetails(String tenantId,
			ApprovalCategoryLevelDetailsAddResource commonAddResource);

	/**
	 * 
	 * Update ApprovalCategoryLevelDetails
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryLevelDetailsUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public ApprovalCategoryLevelDetails updateApprovalCategoryLevelDetails(String tenantId, Long id,
			ApprovalCategoryLevelDetailsUpdateResource approvalCategoryLevelDetailsUpdateResource);
}
