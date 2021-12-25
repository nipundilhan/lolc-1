package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fusionx.lending.origination.domain.ApprovalGroup;
import com.fusionx.lending.origination.resource.ApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUpdateResource;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ApprovalGroupService {

	/**
	 * 
	 * Find all ApprovalGroup
	 * 
	 * @author Dilki
	 * @return JSON array of all ApprovalGroup
	 * 
	 */
	public List<ApprovalGroup> getAll();

	/**
	 * 
	 * Find ApprovalGroup by ID
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalGroup
	 * 
	 */
	public Optional<ApprovalGroup> getById(Long id);

	/**
	 * 
	 * Find ApprovalGroup by code
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalGroup
	 * 
	 */
	public Optional<ApprovalGroup> getByCode(String code);

	/**
	 * 
	 * Find ApprovalGroup by name
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalGroup
	 * 
	 */
	public List<ApprovalGroup> getByName(String name);

	/**
	 * 
	 * Find ApprovalGroup by status
	 * 
	 * @author Dilki
	 * @return JSON array of ApprovalGroup
	 * 
	 */
	public List<ApprovalGroup> getByStatus(String status);

	/**
	 * 
	 * Insert ApprovalGroup
	 * 
	 * @author Dilki
	 * @param ApprovalGroupAddResource
	 * @return Successfully saved
	 * 
	 */
	public ApprovalGroup addApprovalGroup(String tenantId, ApprovalGroupAddResource approvalGroupAddResource);

	/**
	 * 
	 * Update ApprovalGroup
	 * 
	 * @author Dilki
	 * @param ApprovalGroupUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public ApprovalGroup updateApprovalGroup(String tenantId, Long id,
			ApprovalGroupUpdateResource approvalGroupUpdateResource);
}
