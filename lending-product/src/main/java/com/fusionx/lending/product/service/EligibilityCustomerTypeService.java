package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityCustomerType;
import com.fusionx.lending.product.domain.EligibilityCustomerTypePending;
import com.fusionx.lending.product.resources.EligibilityCustomerTypeAddResource;
import com.fusionx.lending.product.resources.EligibilityCustomerTypeUpdateResource;

/**
 * EligibilityCustomerTypeService
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   29-07-2021    FXL_365			   	FXL-56		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityCustomerTypeService {
	
	/**
	 * 
	 * Find EligibilityCustomerType by Id
	 * 
	 * @author Piyumi
	 * @return -JSON Object of EligibilityCustomerType
	 * 
	 */
	 Optional<EligibilityCustomerType> getById(String tenantId,Long id);

	/**
	 * 
	 * Insert EligibilityCustomerType
	 * 
	 * @author Piyumi
	 * @param - eligibilityCustomerTypeAddResource
	 * @return - Successfully saved
	 * 
	 */
	  Long addEligibilityCustomerType(String tenantId, 
			EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource);

	/**
	 * 
	 * Update EligibilityCustomerType
	 * 
	 * @author Piyumi
	 * @param - eligibilityCustomerTypeUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	 Long updateEligibilityCustomerType(String tenantId, 
			EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource);

	/**
	 * 
	 * Find Pending EligibilityCustomerType by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of EligibilityCustomerType Pending
	 * 
	 */
	 Optional<EligibilityCustomerTypePending> getByPendingId(String tenantId,Long pendingId);
	
	/**
	 * 
	 * Find EligibilityCustomerType by EligibilityId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all EligibilityCustomerType
	 * 
	 */
	 List<EligibilityCustomerType> getByEligibilityId(String tenantId,Long eligibilityId);
	
	/**
	 * 
	 * Find EligibilityCustomerType by CustomerTypeId
	 * 
	 * @author Piyumi
	 * @return -JSON array of EligibilityCustomerType
	 * 
	 */
	 List<EligibilityCustomerType> getByCustomerTypeId(String tenantId,Long customerTypeId);
	
	/**
	 * 
	 * Find EligibilityCustomerType by status
	 * 
	 * @author Piyumi
	 * @return -JSON array of EligibilityCustomerType
	 * 
	 */
	 List<EligibilityCustomerType> getByStatus(String tenantId, String status);
	
	
	/**
	 * Search EligibilityCustomerTypePending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	 Page<EligibilityCustomerTypePending> searchEligibilityCustomerTypePending(String tenantId, String searchq, String status, String approveStatus, Pageable pageable);

}
