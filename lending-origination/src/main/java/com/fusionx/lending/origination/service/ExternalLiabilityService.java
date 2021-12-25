package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.resource.ExternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.ExternalLiabilityUpdateResource;

/**
 * External Liability Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2021   				FXL-414 	NipunDilhan        Created
 ********************************************************************************************************
 */

@Service
public interface ExternalLiabilityService {
	
	/**
	 *  save ExternalLiability
	 * @param tenantId
	 * @param id -customer id
	 * @param ExternalLiabilityAddResource
	 * @return Customer
	 */
	ExternalLiability addExternalLiability(String tenantId,Long customerId ,ExternalLiabilityAddResource externalLiabilitiesAddResource);

	/**
	 * find by customer id
	 * @param id-customer id
	 * @return  List<ExternalLiability> 
	 */
	public List<ExternalLiability> findByCustomerId(Long customerId);
	
	/**
	 *  update ExternalLiability
	 * @param tenantId
	 * @param id -customer id
	 * @param ExternalLiabilityUpdateResource
	 * @return Customer
	 */
	public ExternalLiability updateExternalLiability(String tenantId,Long externalLiabilityId, ExternalLiabilityUpdateResource externalLiabilityUpdateResource);
}
