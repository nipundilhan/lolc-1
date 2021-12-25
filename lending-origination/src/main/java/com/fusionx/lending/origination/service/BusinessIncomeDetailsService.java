package com.fusionx.lending.origination.service;
/**
 * 	Business Income Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessIncomeDetails;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsUpdateResource;



@Service
public interface BusinessIncomeDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Business Income Details Object
	 */
	Optional<BusinessIncomeDetails> findById(String tenantId ,Long id);
	

	List<BusinessIncomeDetails> findAll(String tenantId );

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Business Income Details Object Array
	 */
	List<BusinessIncomeDetails> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessIncomeDetailsAddResource - the business income details add resource
	 * @return the Business Income Details Object
	 */
	BusinessIncomeDetails save(String tenantId, BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business income details id
	 * @param BusinessIncomeDetailsUpdateResource - the business income details update resource
	 * @return the Business Income Details Object
	 */
	BusinessIncomeDetails update(String tenantId, Long id,  BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource);

	/**
	 * Find by income source details id.
	 *
	 * @param incomeSourceDetailsId - the income source details id
	 * @return the Business Income Details Object Array
	 */
	List<BusinessIncomeDetails> findByIncomeSourceDetailsId(String tenantId , Long incomeSourceDetailsId);
}
