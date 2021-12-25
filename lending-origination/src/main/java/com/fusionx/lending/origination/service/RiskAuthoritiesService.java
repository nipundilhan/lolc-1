package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.RiskAuthorities;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;

@Service
public interface RiskAuthoritiesService {

	/**
	 * 
	 * Find all RiskAuthorities
	 * 
	 * @author Dilki
	 * @return -JSON array of all RiskAuthorities
	 * 
	 */
	public List<RiskAuthorities> getAll();

	/**
	 * 
	 * Find RiskAuthorities by ID
	 * 
	 * @author Dilki
	 * @return -JSON array of RiskAuthorities
	 * 
	 */
	public Optional<RiskAuthorities> getById(Long id);

	/**
	 * 
	 * Find RiskAuthorities by code
	 * 
	 * @author Dilki
	 * @return -JSON array of RiskAuthorities
	 * 
	 */
	public Optional<RiskAuthorities> getByCode(String code);

	/**
	 * 
	 * Find RiskAuthorities by name
	 * 
	 * @author Dilki
	 * @return -JSON array of RiskAuthorities
	 * 
	 */
	public List<RiskAuthorities> getByName(String name);

	/**
	 * 
	 * Find RiskAuthorities by status
	 * 
	 * @author Dilki
	 * @return -JSON array of RiskAuthorities
	 * 
	 */
	public List<RiskAuthorities> getByStatus(String status);

	/**
	 * 
	 * Insert RiskAuthorities
	 * 
	 * @author Dilki
	 * @param - CommonAddResource
	 * @return - Successfully saved
	 * 
	 */
	public RiskAuthorities addRiskAuthorities(String tenantId, CommonAddResource commonAddResource);

	/**
	 * 
	 * Update RiskAuthorities
	 * 
	 * @author Dilki
	 * @param - CommonUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public RiskAuthorities updateRiskAuthorities(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
