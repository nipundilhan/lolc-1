package com.fusionx.lending.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.resources.MasterDefDueDateMappingUpdateResource;

/**
 * MasterDef Due Date Template Mapping Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021    FXL-680  	 FXL-924	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface MasterDefDueDateTemplateMappingService {

	/**
	 * 
	 * Find MasterDefinition by dueDateTemplateId
	 * 
	 * @param dueDateTemplateId- dueDateTemplateId
	 * @return -JSON Object List of MasterDefinition
	 * 
	 */
	public List<MasterDefinition> getByDueDateTemplateId(Long dueDateTemplateId);


	/**
	 * 
	 * Update Due Date Templates Mapping Details of Master Definition
	 * @param tenantId - tenantId
	 * @param id - id
	 * @param masterDefDueDateMappingUpdateResource - masterDefDueDateMappingUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateDueDateTempMappingDetails(String tenantId, Long id,
			MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource);
	
	
}
