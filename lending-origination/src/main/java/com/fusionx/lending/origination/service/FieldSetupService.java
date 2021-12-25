package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.resource.ApprovalLevelAddResource;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;

/**
 * Credit risk parameter template field setup service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FieldSetupService {
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<FieldSetup> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Field Setup
	 */
	public Optional<FieldSetup> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the Risk Grading
	 */
	
	public List<FieldSetup> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Risk Grading
	 */
	public List<FieldSetup> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<FieldSetup> findByCode(String code);
	
	/**
	 * Save and validate field setup.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long save(String tenantId, String createdUser, FieldSetupAddResource fieldSetupAddResource);

	/**
	 * Update and validate field setup.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Long update(String tenantId, String createdUser, Long id,FieldSetupUpdateResource fieldSetupUpdateResource);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
}
