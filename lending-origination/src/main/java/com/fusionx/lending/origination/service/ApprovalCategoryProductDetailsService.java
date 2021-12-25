package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryProductDetails;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;

@Service
public interface ApprovalCategoryProductDetailsService {

	/**
	 * 
	 * Find all ApprovalCategoryProductDetails
	 * 
	 * @author Dilki
	 * @return -JSON array of all ApprovalCategoryProductDetails
	 * 
	 */
	public List<ApprovalCategoryProductDetails> getAll();

	/**
	 * 
	 * Find ApprovalCategoryProductDetails by ID
	 * 
	 * @author Dilki
	 * @return -JSON array of ApprovalCategoryProductDetails
	 * 
	 */
	public Optional<ApprovalCategoryProductDetails> getById(Long id);

	/**
	 * 
	 * Find ApprovalCategoryProductDetails by code
	 * 
	 * @author Dilki
	 * @return -JSON array of ApprovalCategoryProductDetails
	 * 
	 */
	public Optional<ApprovalCategoryProductDetails> getByCode(String code);

	/**
	 * 
	 * Find ApprovalCategoryProductDetails by name
	 * 
	 * @author Dilki
	 * @return -JSON array of ApprovalCategoryProductDetails
	 * 
	 */
	public List<ApprovalCategoryProductDetails> getByName(String name);

	/**
	 * 
	 * Find ApprovalCategoryProductDetails by status
	 * 
	 * @author Dilki
	 * @return -JSON array of ApprovalCategoryProductDetails
	 * 
	 */
	public List<ApprovalCategoryProductDetails> getByStatus(String status);

	/**
	 * 
	 * Insert ApprovalCategoryProductDetails
	 * 
	 * @author Dilki
	 * @param - CommonAddResource
	 * @return - Successfully saved
	 * 
	 */
	public ApprovalCategoryProductDetails addApprovalCategoryProductDetails(String tenantId,
			CommonAddResource commonAddResource);

	/**
	 * 
	 * Update ApprovalCategoryProductDetails
	 * 
	 * @author Dilki
	 * @param - CommonUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public ApprovalCategoryProductDetails updateApprovalCategoryProductDetails(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource);
}
