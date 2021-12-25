package com.fusionx.lending.origination.service;
/**
 * 	Income Source Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsAddResource;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsUpdateResource;



@Service
public interface IncomeSourceDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Income Source Details Object
	 */
	Optional<IncomeSourceDetails> findById(Long id);
	

	List<IncomeSourceDetails> findAll();

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Income Source Details Object Array
	 */
	List<IncomeSourceDetails> findByStatus(String status);
	
	/**
	 * Find by leadId.
	 *
	 * @param leadId - the id
	 * @return the Income Source Details Object Array
	 */
	List<IncomeSourceDetails> findByLeadId(Long leadId);
	
	/**
	 * Find by customerId.
	 *
	 * @param customerId - the customerId
	 * @return the Income Source Details Object Array
	 */
	List<IncomeSourceDetails> findByCustomerId(Long customerId);
	
	/**
	 * Find by linkedPersonId.
	 *
	 * @param linkedPersonId - the linkedPersonId
	 * @return the Income Source Details Object Array
	 */
	List<IncomeSourceDetails> findByLinkedPersonId(Long linkedPersonId);
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param IncomeSourceDetailsAddResource - the income source details add resource
	 * @return the Income Source Details Object
	 */
	IncomeSourceDetails save(String tenantId, IncomeSourceDetailsAddResource incomeSourceDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the exception type id
	 * @param IncomeSourceDetailsUpdateResource - the income source details update resource
	 * @return the Income Source Details Object
	 */
	IncomeSourceDetails update(String tenantId, Long id,  IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource);

	
}
