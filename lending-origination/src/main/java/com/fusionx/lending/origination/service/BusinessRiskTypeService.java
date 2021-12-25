package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;

@Service
public interface BusinessRiskTypeService {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<BusinessRiskType> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessType
	 */
	public Optional<BusinessRiskType> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the BusinessType
	 */
	
	public List<BusinessRiskType> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the BusinessType
	 */
	public List<BusinessRiskType> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<BusinessRiskType> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param businessTypeAddResource - the business type add resource
	 * @return the id
	 */
	public Long saveAndValidateBusinessRiskType(String tenantId, String createdUser, CommonAddResource commonAddResource);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param businessTypeUpdateResource - the business type update resource
	 * @return the id
	 */
	public Long updateAndValidateBusinessRiskType(String tenantId, String createdUser, Long id,CommonUpdateResource commonUpdateResource);


}
