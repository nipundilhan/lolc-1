package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;
import com.fusionx.lending.product.domain.EligibilityResidencyEligibilityPending;
import com.fusionx.lending.product.resources.EligibilityResidencyEligibilityAddResource;
import com.fusionx.lending.product.resources.EligibilityResidencyEligibilityUpdateResource;

/**
 * EligibilityResidencyEligibilityService
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityResidencyEligibilityService {
	
	/**
	 * 
	 * Find EligibilityResidencyEligibility by Id
	 * 
	 * @author Piyumi
	 * @return -JSON Object of EligibilityResidencyEligibility
	 * 
	 */
	public Optional<EligibilityResidencyEligibility> getById(String tenantId,Long id);

	/**
	 * 
	 * Insert EligibilityResidencyEligibility
	 * 
	 * @author Piyumi
	 * @param - EligibilityResidencyEligibilityAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  Long addEligibilityResidencyEligibility(String tenantId, 
			EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource);

	/**
	 * 
	 * Update EligibilityResidencyEligibility
	 * 
	 * @author Piyumi
	 * @param - EligibilityResidencyEligibilityUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateEligibilityResidencyEligibility(String tenantId, 
			EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource);

	/**
	 * 
	 * Find Pending EligibilityResidencyEligibility by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of EligibilityResidencyEligibility Pending
	 * 
	 */
	public Optional<EligibilityResidencyEligibilityPending> getByPendingId(String tenantId,Long pendingId);
	
	/**
	 * 
	 * Find EligibilityResidencyEligibility by EligibilityId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all EligibilityResidencyEligibility
	 * 
	 */
	public List<EligibilityResidencyEligibility> getByEligibilityId(String tenantId,Long eligibilityId);
	
	/**
	 * 
	 * Find EligibilityResidencyEligibility by ResidencyEligibilityId
	 * 
	 * @author Piyumi
	 * @return -JSON array of EligibilityResidencyEligibility
	 * 
	 */
	public List<EligibilityResidencyEligibility> getByResidencyEligibilityId(String tenantId,Long residencyEligibilityId);
	
	/**
	 * 
	 * Find EligibilityResidencyEligibility by status
	 * 
	 * @author Piyumi
	 * @return -JSON array of EligibilityResidencyEligibility
	 * 
	 */
	public List<EligibilityResidencyEligibility> getByStatus(String tenantId,String status);
	
	
	/**
	 * Search EligibilityResidencyEligibilityPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<EligibilityResidencyEligibilityPending> searchEligibilityResidencyEligibilityPending(String tenantId,String searchq, String status, String approveStatus, Pageable pageable);

}
