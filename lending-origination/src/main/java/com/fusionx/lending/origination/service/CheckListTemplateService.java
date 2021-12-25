package com.fusionx.lending.origination.service;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.domain.CheckListTemplatePending;
import com.fusionx.lending.origination.enums.WorkflowStatus;
import com.fusionx.lending.origination.resource.CheckListTemplateAddResource;
import com.fusionx.lending.origination.resource.CheckListTemplateUpdateResource;

@Service
public interface CheckListTemplateService {

	/**
	 * 
	 * Find all CheckListTemplate
	 * 
	 * @author Dilki
	 * @return JSON array of all CheckListTemplate
	 * 
	 */
	public List<CheckListTemplate> getAll();

	/**
	 * 
	 * Find CheckListTemplate by ID
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListTemplate
	 * 
	 */
	public Optional<CheckListTemplate> getById(Long id);

	/**
	 * 
	 * Find CheckListTemplate by code
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListTemplate
	 * 
	 */
	public Optional<CheckListTemplate> getByCode(String code);

	/**
	 * 
	 * Find CheckListTemplate by name
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListTemplate
	 * 
	 */
	public List<CheckListTemplate> getByName(String name);

	/**
	 * 
	 * Find CheckListTemplate by status
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListTemplate
	 * 
	 */
	public List<CheckListTemplate> getByStatus(String status);

	/**
	 * 
	 * Insert CheckListTemplate
	 * 
	 * @author Dilki
	 * @param CheckListTemplateAddResource
	 * @return Successfully saved
	 * 
	 */
	public CheckListTemplate addCheckListTemplate(String tenantId,
			CheckListTemplateAddResource checkListTemplateAddResource);

	/**
	 * 
	 * Update CheckListTemplate
	 * 
	 * @author Dilki
	 * @param CheckListTemplateUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public CheckListTemplate updateCheckListTemplate(String tenantId, Long id,
			CheckListTemplateUpdateResource checkListTemplateUpdateResource);

	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);

	public Optional<CheckListTemplatePending> getByPendingId(Long pendingId);
}
