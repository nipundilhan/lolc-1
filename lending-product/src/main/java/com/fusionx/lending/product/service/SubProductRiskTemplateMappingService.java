package com.fusionx.lending.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.resources.SubProductRiskMappingUpdateResource;

/**
 * Sub Product Risk Template Mapping Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  18-10-2021 							Dilki        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SubProductRiskTemplateMappingService {

	/**
	 * 
	 * Find SubProduct by riskTemplateId
	 * 
	 * @param riskTemplateId riskTemplateId
	 * @return JSON Object List of SubProduct
	 * 
	 */
	public List<SubProduct> getByRiskTemplateId(Long riskTemplateId);

	/**
	 * 
	 * Update risk Templates Mapping Details of Sub Product
	 * 
	 * @param tenantId                            tenantId
	 * @param id                                  id
	 * @param subProductRiskMappingUpdateResource subProductRiskMappingUpdateResource
	 * @return Successfully saved
	 * 
	 */
	public Long updateRiskTempMappingDetails(String tenantId, Long id,
			SubProductRiskMappingUpdateResource subProductRiskMappingUpdateResource);

}
