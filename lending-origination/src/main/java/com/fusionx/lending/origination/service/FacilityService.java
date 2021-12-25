package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FacilityParameter;
import com.fusionx.lending.origination.resource.FacilityCalculationRequestResource;

@Service
public interface FacilityService {

	/**
	 * Gets the Facility Calculation Detail by leadId
	 *
	 * @param leadId - the leadId
	 * @return the FacilityParameter
	 */
	public FacilityParameter findFacilityCalculationDetailByLeadId(Long leadId);
	
	/**
	 * Save and validate facility
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param FacilityCalculationRequestResource
	 * @return the boolean
	 */
	public Long saveFacility(String tenantId, String createdUser, Long leadId, FacilityCalculationRequestResource facilityCalculationRequestResource);
	
	/**
	 * Save and validate facility
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param FacilityCalculationRequestResource
	 * @return the boolean
	 */
	public Long updateFacility(String tenantId, String createdUser, Long leadId, FacilityCalculationRequestResource facilityCalculationRequestResource);
}
