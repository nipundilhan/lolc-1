package com.fusionx.lending.origination.service;
/**
 * 	Business Additional Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsUpdateResource;



@Service
public interface BusinessAdditionalDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Business Additional Details Object
	 */
	Optional<BusinessAdditionalDetails> findById(String tenantId ,Long id);
	

	List<BusinessAdditionalDetails> findAll(String tenantId );

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Business Additional Details Object Array
	 */
	List<BusinessAdditionalDetails> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessAdditionalDetailsAddResource - the business additional details add resource
	 * @return the Business Additional Details Object
	 */
	BusinessAdditionalDetails save(String tenantId, BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business additional details id
	 * @param BusinessAdditionalDetailsUpdateResource - the business additional details update resource
	 * @return the Business Additional Details Object
	 */
	BusinessAdditionalDetails update(String tenantId, Long id,  BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource);


}
