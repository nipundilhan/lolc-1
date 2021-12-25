package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalGroupUserMappingDetails;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsUpdateResource;

@Service
public interface ApprovalGroupUserMappingDetailsService {

	/**
	 * 
	 * Find all UserMappingDetails
	 * 
	 * @author Dilki
	 * @return JSON array of all UserMappingDetails
	 * 
	 */
	public List<ApprovalGroupUserMappingDetails> getAll();

	/**
	 * 
	 * Find UserMappingDetails by ID
	 * 
	 * @author Dilki
	 * @return JSON array of UserMappingDetails
	 * 
	 */
	public Optional<ApprovalGroupUserMappingDetails> getById(Long id);

	/**
	 * 
	 * Find UserMappingDetails by approval category id
	 * 
	 * @author Dilki
	 * @return JSON array of UserMappingDetails
	 * 
	 */
	public List<ApprovalGroupUserMappingDetails> getByApprovalCategoryId(Long approvalCategoryId);

	/**
	 * 
	 * Find UserMappingDetails by status
	 * 
	 * @author Dilki
	 * @return JSON array of UserMappingDetails
	 * 
	 */
	public List<ApprovalGroupUserMappingDetails> getByStatus(String status);

	/**
	 * 
	 * Insert UserMappingDetails
	 * 
	 * @author Dilki
	 * @param UserMappingDetailsAddResource
	 * @return Successfully saved
	 * 
	 */
	public ApprovalGroupUserMappingDetails addUserMappingDetails(String tenantId,
			ApprovalGroupUserMappingDetailsAddResource userMappingDetailsAddResource);

	/**
	 * 
	 * Update UserMappingDetails
	 * 
	 * @author Dilki
	 * @param UserMappingDetailsUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public ApprovalGroupUserMappingDetails updateUserMappingDetails(String tenantId, Long id,
			ApprovalGroupUserMappingDetailsUpdateResource userMappingDetailsUpdateResource);
}
