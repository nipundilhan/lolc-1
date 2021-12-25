package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.LeadInfoStaging;
import com.fusionx.lending.origination.resource.LeadInfoStagingAddRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingUpdateRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingWithAdditionalDetailsResource;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface LeadInfoStagingService {
	
	public List<LeadInfoStaging> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the LeadInfoStaging
	 */
	public Optional<LeadInfoStaging> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the LeadInfoStaging
	 */
	
	public List<LeadInfoStaging> findByStatus(String status);
	
	/**
	 * Find by lead infoe id.
	 *
	 * @param id - the lead info id
	 * @return the LeadInfoStaging
	 */
	public List<LeadInfoStaging> findByLeadId(Long id);
	
	/**
	 * Find by staging status.
	 *
	 * @param stagingStatus - the staging status
	 * @return the LeadInfoStaging
	 */
	
	public List<LeadInfoStaging> findByStagingStatus(String stagingStatus);
	
	/**
	 * Save and validate Lead Info Staging details.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param LeadInfoStagingAddRequestResource - the Lead Info Staging details add resource
	 * @return the id
	 */
	public Long saveAndValidateLeadInfoStagingDetails(String tenantId, String createdUser, LeadInfoStagingAddRequestResource leadInfoStagingAddRequestResource);
	
	/**
	 * Update and validate Lead Info Staging details.
	 *
	 * @param tenantId - the tenant id
	 * @param modifiedUser - the created user
	 * @param id - the lead info id
	 * @param LeadInfoStagingUpdateRequestResource - the Lead Info Staging details update resource
	 * @return the id
	 */
	public Long updateAndValidateLeadInfoStagingDetails(String tenantId, String modifiedUser, LeadInfoStagingUpdateRequestResource leadInfoStagingUpdateRequestResource, Long id);
	
	
	/**
	 * Get Lead Info Staging with additional details.
	 *
	 * @param tenantId
	 * @param stagingStatus
	 * @return LeadInfoStagingWithAdditionalDetailsResource
	 */
	public List<LeadInfoStagingWithAdditionalDetailsResource> findByStagingStausWithAdditionalDetails(String tenantId, String stagingStatus,String user);
	

}
