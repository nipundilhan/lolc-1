package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.RiskTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateUpdateResource;

/**
 * Risk Template Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RiskTemplateService {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<RiskTemplate> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Risk Template
	 */
	public Optional<RiskTemplate> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the Risk Template
	 */
	
	public List<RiskTemplate> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Risk Template
	 */
	public List<RiskTemplate> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<RiskTemplate> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);

	/**
	 * Save and validate risk template.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long saveAndValidateRiskTemplate(String tenantId, String createdUser, RiskTemplateAddResource riskTemplateAddResource);
	
	/**
	 * Update and validate risk template.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long updateAndValidateRiskTemplate(String tenantId, String createdUser, Long id,RiskTemplateUpdateResource riskTemplateUpdateResource);

}
