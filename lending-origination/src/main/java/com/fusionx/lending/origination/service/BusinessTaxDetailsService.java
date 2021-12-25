package com.fusionx.lending.origination.service;
/**
 * 	Business Tax Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessTaxDetails;
import com.fusionx.lending.origination.resource.BusinessTaxDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessTaxTypeResource;



@Service
public interface BusinessTaxDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Business Tax Details Object
	 */
	Optional<BusinessTaxDetails> findById(Long id);
	

	List<BusinessTaxDetails> findAll();

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Business Tax Details Object Array
	 */
	List<BusinessTaxDetails> findByStatus( String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessTaxDetailsAddResource - the business tax details add resource
	 * @return Boolean
	 */
	Boolean save(String tenantId, BusinessTaxDetailsAddResource businessTaxDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business tax details id
	 * @param BusinessTaxTypeResource - the business tax type resource
	 * @return the Business Tax Details Object
	 */
	BusinessTaxDetails update(String tenantId, Long id,  BusinessTaxTypeResource businessTaxTypeResource);

	/**
	 * Find by business income details id
	 *
	 * @param businessIncomeDetailsId -  the business income details id
	 * @return the Business Tax Details Object Array
	 */
	List<BusinessTaxDetails> findByBusinessIncomeDetailsId( Long businessIncomeDetailsId);
}
