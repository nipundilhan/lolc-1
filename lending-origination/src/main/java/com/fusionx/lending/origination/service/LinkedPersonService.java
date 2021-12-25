package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.resource.LinkedPersonResource;

/**
 * LinkedPersonService
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   30-07-2021    FXL_381			   	FXL-417		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface LinkedPersonService {
	
	/**
	 * save Linked Person
	 *
	 * @param tenantId - the tenant id
	 * @param List<LinkedPersonResource>  - linkedPersonResource
	 */
	public void saveLinkedPerson(String tenantId, Customer customer,List<LinkedPersonResource> linkedPersonResource );

}
