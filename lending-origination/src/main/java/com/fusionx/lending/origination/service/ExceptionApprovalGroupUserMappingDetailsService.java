package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.resource.UserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.UserMappingDetailsUpdateResource;

@Service
public interface ExceptionApprovalGroupUserMappingDetailsService {

	/**
	 * 
	 * Find all UserMappingDetails
	 * 
	 * @author Dilki
	 * @return -JSON array of all UserMappingDetails
	 * 
	 */
	public List<UserMappingDetails> getAll();

	/**
	 * 
	 * Find UserMappingDetails by ID
	 * 
	 * @author Dilki
	 * @return -JSON array of UserMappingDetails
	 * 
	 */
	public Optional<UserMappingDetails> getById(Long id);

	/**
	 * 
	 * Find UserMappingDetails by approval category id
	 * 
	 * @author Dilki
	 * @return -JSON array of UserMappingDetails
	 * 
	 */
	public List<UserMappingDetails> getByApprovalCategoryId(Long approvalCategoryId);

	/**
	 * 
	 * Find UserMappingDetails by status
	 * 
	 * @author Dilki
	 * @return -JSON array of UserMappingDetails
	 * 
	 */
	public List<UserMappingDetails> getByStatus(String status);

	/**
	 * 
	 * Insert UserMappingDetails
	 * 
	 * @author Dilki
	 * @param - UserMappingDetailsAddResource
	 * @return - Successfully saved
	 * 
	 */
	public UserMappingDetails addUserMappingDetails(String tenantId,
			UserMappingDetailsAddResource userMappingDetailsAddResource);

	/**
	 * 
	 * Update UserMappingDetails
	 * 
	 * @author Dilki
	 * @param - UserMappingDetailsUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public UserMappingDetails updateUserMappingDetails(String tenantId, Long id,
			UserMappingDetailsUpdateResource userMappingDetailsUpdateResource);
}
