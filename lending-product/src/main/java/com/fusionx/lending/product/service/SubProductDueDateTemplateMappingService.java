package com.fusionx.lending.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.resources.SubProductDueDateMappingUpdateResource;

/**
 * Sub Product Due Date Template Mapping Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021    FXL-155  	 FXL-933	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SubProductDueDateTemplateMappingService {

	/**
	 * 
	 * Find SubProduct by dueDateTemplateId
	 * 
	 * @param dueDateTemplateId- dueDateTemplateId
	 * @return -JSON Object List of SubProduct
	 * 
	 */
	public List<SubProduct> getByDueDateTemplateId(Long dueDateTemplateId);


	/**
	 * 
	 * Update Due Date Templates Mapping Details of Sub Product
	 * @param tenantId - tenantId
	 * @param id - id
	 * @param subProductDueDateMappingUpdateResource - subProductDueDateMappingUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateDueDateTempMappingDetails(String tenantId, Long id,
			SubProductDueDateMappingUpdateResource subProductDueDateMappingUpdateResource);
	
	
}
