package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;

/**
 * Risk Grading Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface RiskGradingService {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<RiskGrading> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Risk Grading
	 */
	public Optional<RiskGrading> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the Risk Grading
	 */
	
	public List<RiskGrading> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Risk Grading
	 */
	public List<RiskGrading> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<RiskGrading> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);

	/**
	 * Save and validate risk gradings.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long saveAndValidateRiskGrading(String tenantId, String createdUser, RiskGradingAddResource riskGradingAddResource);
	
	/**
	 * Update and validate risk gradings.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long updateAndValidateRiskGrading(String tenantId, String createdUser, Long id,RiskGradingUpdateResource riskGradingUpdateResource);


}
