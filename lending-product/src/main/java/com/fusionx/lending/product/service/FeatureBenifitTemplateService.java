package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;

/**
 * Feature Benifit Template Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-07-2021             				         Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FeatureBenifitTemplateService {
	
	
	/**
	 * 
	 * Find all FeatureBenifitTemplate 
	 * @return -JSON array of all FeatureBenifitTemplate 
	 * 
	 * */
	public List<FeatureBenifitTemplate> getAll();
	
	/**
	 * 
	 * Find FeatureBenifitTemplate  by ID

	 * @return -JSON array of FeatureBenifitTemplate 
	 * 
	 * */
	public Optional<FeatureBenifitTemplate> getById(Long id);
	
	/**
	 * 
	 * Find FeatureBenifitTemplate  by code

	 * @return -JSON array of FeatureBenifitTemplate 
	 * 
	 * */
	public Optional<FeatureBenifitTemplate> getByCode(String code);
	
	/**
	 * 
	 * Find FeatureBenifitTemplate  by name

	 * @return -JSON array of FeatureBenifitTemplate 
	 * 
	 * */
	public List<FeatureBenifitTemplate> getByName(String name);
	
	/**
	 * 
	 * Find FeatureBenifitTemplate  by status

	 * @return -JSON array of FeatureBenifitTemplate 
	 * 
	 * */
	public List<FeatureBenifitTemplate> getByStatus(String status);
	

	public FeatureBenifitTemplate addFeatureBenifitTemplate(String tenantId,
			CommonAddResource commonAddResource);
	
	public FeatureBenifitTemplatePending updateFeatureBenifitTemplate(String tenantId, Long id,CommonUpdateResource commonUpdateResource);
	
	public Optional<FeatureBenifitTemplatePending> getByPendingId(Long pendingId);
	
	
	
	/**
	 * 
	 * Search FeatureBenifitTemplate Pending
	 * @author Sanatha
	 * @param searchq
	 * @param status
	 * @param approveStatus
	 * @param pageable
	 * @return -JSON Pageable FeatureBenifitTemplatePending
	 * 
	 * */
	public Page<FeatureBenifitTemplatePending> searchFeatureBenifitTemplatePending(String searchq, String status, String approveStatus, Pageable pageable);
	
	
}
