package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherDetails;
import com.fusionx.lending.origination.resource.OtherDetailsAddRequestResource;
import com.fusionx.lending.origination.resource.OtherDetailsUpdateRequestResource;

/**
 * Other Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface OtherDetailsService {
	
	public List<OtherDetails> findAll(String tenantId);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the OtherDetails
	 */
	public Optional<OtherDetails> findById(String tenantId, Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the OtherDetails
	 */
	
	public List<OtherDetails> findByStatus(String tenantId, String status);
	
	/**
	 * Find by lead infoe id.
	 *
	 * @param id - the lead info id
	 * @return the OtherDetails
	 */
	public Optional<OtherDetails> findByLeadId(String tenantId, Long id);
	
	/**
	 * Save and validate Other details.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param OtherDetailsAddRequestResource - the other details add resource
	 * @return the id
	 */
	public Long saveAndValidateOtherDetails(String tenantId, String createdUser, OtherDetailsAddRequestResource otherDetailsAddRequestResource);
	
	/**
	 * Update and validate Other details.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the lead info id
	 * @param OtherDetailsUpdateRequestResource - the other details update resource
	 * @return the id
	 */
	public Long updateAndValidateOtherDetails(String tenantId, String createdUser, OtherDetailsUpdateRequestResource otherDetailsUpdateRequestResource, Long id);
	

}
