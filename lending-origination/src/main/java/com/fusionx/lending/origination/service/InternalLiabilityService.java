package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.InternalLiability;
import com.fusionx.lending.origination.resource.InternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.InternalLiabilityUpdateResource;

/**
 * Internal Liability Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2021   				 FXL-413	NipunDilhan        Created
 ********************************************************************************************************
 */

@Service
public interface InternalLiabilityService {
	/**
	 *  create internalliability
	 * @param tenantId
	 * @param id -customer id
	 * @param List<InternalLiabilityAddResource>
	 * @return Customer
	 */
	public Customer saveInternalLiabilityList(String tenantId,Long customerId ,List<InternalLiabilityAddResource> internalLiabilityAddResourceList);

	/**
	 *  update internalliability
	 * @param tenantId
	 * @param id -customer id
	 * @param List<InternalLiabilityUpdateResource>
	 * @return Customer
	 */
	public Customer updateInternalLiabilityList(Long customerId,List<InternalLiabilityUpdateResource> internalLiabilityUpdateResourceList);
	
	/**
	 * find by customer id
	 * @param id-customer id
	 * @return  List<InternalLiability> 
	 */
	public List<InternalLiability> findByCustomerId(Long customerId);
}
