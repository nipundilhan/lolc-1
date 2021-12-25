package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.resource.FacilityOtherProductsResource;
import com.fusionx.lending.origination.resource.NewFacilityAddResource;
import com.fusionx.lending.origination.resource.NewFacilityUpdateResource;
/**
 * New Facility Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface NewFacilityService {
	
	/**
	 * Save Facility
	 *
	 * @param tenantId
	 * @param leadId
	 * @param NewFacilityAddResource
	 * @return Long
	 */
	public Long saveFacility(String tenantId, Long leadId, NewFacilityAddResource newFacilityAddResource);

	/**
	 * Update Facility
	 *
	 * @param tenantId
	 * @param leadId
	 * @param NewFacilityUpdateResource
	 * @return Long
	 */
	public Long updateFacility(String tenantId,NewFacilityUpdateResource newFacilityUpdateResource, Long leadId);

	/**
	 * Get All LeadInfo
	 *
	 * @return LeadInfo List
	 */
	public List<LeadInfo> getAll();

	/**
	 * Get LeadInfo By Status
	 *
	 * @param tenantId
	 * @param status
	 * @return LeadInfo List
	 */
	public List<LeadInfo> getByStatus(String status);

	/**
	 * Get LeadInfo By id
	 *
	 * @param tenantId
	 * @param id
	 * @return LeadInfo object
	 */
	public Optional<LeadInfo> getById(Long id);
	
	/**
	 * save OtherProducts By otherProductsResource
	 *
	 * @param tenantId
	 * @param FacilityOtherProductsResource
	 * @return LeadInfo object
	 */
	public void saveOtherProducts(String tenantId, List<FacilityOtherProductsResource> otherProducts, LeadInfo leadInfo);

}
