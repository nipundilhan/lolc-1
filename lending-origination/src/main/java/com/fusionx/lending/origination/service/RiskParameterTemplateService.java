package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.resource.RiskParameterTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateUpdateResource;

/**
 * Risk parameter template Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RiskParameterTemplateService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<RiskParameterTemplate> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Risk parameter template
	 */
	public Optional<RiskParameterTemplate> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the Risk Grading
	 */
	
	public List<RiskParameterTemplate> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Risk parameter template
	 */
	public List<RiskParameterTemplate> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<RiskParameterTemplate> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);

	/**
	 * Save and validate Risk parameter template
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long saveAndValidateRiskParameterTemplate(String tenantId, String createdUser, RiskParameterTemplateAddResource riskParameterTemplateAddResource);
	
	/**
	 * Update and validate Risk parameter template
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long updateAndValidateRiskParameterTemplate(String tenantId, String createdUser, Long id,RiskParameterTemplateUpdateResource riskParameterTemplateUpdateResource);

}
