package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityOther;
import com.fusionx.lending.product.domain.EligibilityOtherPending;
import com.fusionx.lending.product.resources.EligibilityOtherAddResource;
import com.fusionx.lending.product.resources.EligibilityOtherUpdateResource;

/**
 * Eligibility Other Service 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityOtherService {
	
	/**
	 * 
	 * Find EligibilityOther by id
	 * 
	 * @author Piyumi
	 * @return -JSON Object of EligibilityOther
	 * 
	 */
	 Optional<EligibilityOther> getById(Long id);

	/**
	 * 
	 * Insert EligibilityOther
	 * 
	 * @author Piyumi
	 * @param - eligibilityOtherAddResource
	 * @return - Success message
	 * 
	 */
	  Long addEligibilityOther(String tenantId, Long eligibilityId,
			EligibilityOtherAddResource eligibilityOtherAddResource);

	/**
	 * 
	 * Update EligibilityOther
	 * 
	 * @author Piyumi
	 * @param - eligibilityOtherUpdateResource
	 * @return - Success message
	 * 
	 */
	 Long updateEligibilityOther(String tenantId, Long eligibilityId,
			EligibilityOtherUpdateResource eligibilityOtherUpdateResource);

	/**
	 * 
	 * Find Pending EligibilityOther by id
	 * 
	 * @author Piyumi
	 * @return -JSON object of EligibilityOther Pending
	 * 
	 */
	 Optional<EligibilityOtherPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find EligibilityOther by eligibilityId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all EligibilityOther
	 * 
	 */
	 List<EligibilityOther> getByEligibilityId(Long eligibilityId);
	
	
	/**
	 * Search EligibilityOtherPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	 Page<EligibilityOtherPending> searchEligibilityOtherPending(String searchq, String status, String approveStatus, Pageable pageable);

	 public List<EligibilityOther> findAll();
}
