package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsUpdateResource;

@Service
public interface ApprovalCategoryDetailsService {

	/**
	 * 
	 * Find all ApprovalCategoryDetails
	 * 
	 * @author Dilki
	 * @return JSON array of all ApprovalCategoryDetails
	 * 
	 */
	public List<ApprovalCategoryDetails> getAll();

	/**
	 * 
	 * Find ApprovalCategoryDetails by ID
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryDetails
	 * 
	 */
	public Optional<ApprovalCategoryDetails> getById(Long id);

	/**
	 * 
	 * Find ApprovalCategoryDetails by code
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryDetails
	 * 
	 */
	public Optional<ApprovalCategoryDetails> getByCode(String code);

	/**
	 * 
	 * Find ApprovalCategoryDetails by name
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryDetails
	 * 
	 */
	public List<ApprovalCategoryDetails> getByName(String name);

	/**
	 * 
	 * Find ApprovalCategoryDetails by status
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalCategoryDetails
	 * 
	 */
	public List<ApprovalCategoryDetails> getByStatus(String status);

	/**
	 * 
	 * Insert ApprovalCategoryDetails
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryDetailsAddResource
	 * @return Successfully saved
	 * 
	 */
	public ApprovalCategoryDetails addApprovalCategoryDetails(String tenantId,
			ApprovalCategoryDetailsAddResource approvalCategoryDetailsAddResource);

	/**
	 * 
	 * Update ApprovalCategoryDetails
	 * 
	 * @author Dilki
	 * @param ApprovalCategoryDetailsUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public ApprovalCategoryDetails updateApprovalCategoryDetails(String tenantId, Long id,
			ApprovalCategoryDetailsUpdateResource approvalCategoryDetailsUpdateResource);
}
