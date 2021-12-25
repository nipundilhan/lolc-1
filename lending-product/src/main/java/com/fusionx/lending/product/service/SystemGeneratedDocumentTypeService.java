package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.SystemGeneratedDocumentType;
import com.fusionx.lending.product.domain.SystemGeneratedDocumentTypeDetails;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeAddResource;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeUpdateResource;
/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface SystemGeneratedDocumentTypeService {

	/**
	 * 
	 * Find all System Generated Document Type
	 * 
	 * @author Dilki
	 * @return JSON array of all Expence Type Cultivation Category
	 * 
	 */
	public List<SystemGeneratedDocumentType> getAll();

	/**
	 * 
	 * Find System Generated Document Type by ID
	 * 
	 * @author Dilki
	 * @return JSON array of System Generated Document Type
	 * 
	 */
	public Optional<SystemGeneratedDocumentType> getById(Long id);

	/**
	 * 
	 * Find System Generated Document Type by status
	 * 
	 * @author Dilki
	 * @return JSON array of System Generated Document Type
	 * 
	 */
	public List<SystemGeneratedDocumentType> getByStatus(String status);

	/**
	 * 
	 * Find System Generated Document Type by code
	 * 
	 * @author Dilki
	 * @return JSON array of System Generated Document Type
	 * 
	 */
	public Optional<SystemGeneratedDocumentType> getByCode(String code);

	/**
	 * 
	 * Insert System Generated Document Type
	 * 
	 * @author Dilki
	 * @param SystemGeneratedDocumentTypeAddResource
	 * @return Successfully saved
	 * 
	 */
	public SystemGeneratedDocumentType addSystemGeneratedDocumentType(String tenantId,
			SystemGeneratedDocumentTypeAddResource documentCheckListAddResource);

	/**
	 * 
	 * Update System Generated Document Type
	 * 
	 * @author Dilki
	 * @param SystemGeneratedDocumentTypeUpdateResource
	 * @return Successfully saved
	 * 
	 */
	public SystemGeneratedDocumentType updateSystemGeneratedDocumentType(String tenantId, Long id,
			SystemGeneratedDocumentTypeUpdateResource documentCheckListUpdateResource);

	public List<SystemGeneratedDocumentType> findByProductName(String name);

	public List<SystemGeneratedDocumentType> findBySubProductName(String name);

	public List<SystemGeneratedDocumentType> findByName(String name);

	Optional<SystemGeneratedDocumentTypeDetails> findById(Long id);
}
