package com.fusionx.lending.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.FeatureBenefitItemUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemUpdateResource;
import com.fusionx.lending.product.resources.FeatureBenifitTemplatePendingDetailsResponse;

/**
 * Feature Benefit Template Item Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		         	NipunDilhan      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface FeatureBenefitTemplateItemService {

	
	/**
	 * Save and Feature Benefit Template Item
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitTemplateItemAddResource - the Feature Benefit Template Item Add Resource
	 * @return the FeatureBenefitTemplateItem
	 */
	public FeatureBenefitTemplateItem createFeatureBenefitTemplateItem(String tenantId,FeatureBenefitTemplateItemAddResource featureBenefitTemplateItemAddResource);

	/**
	 * update Feature Benefit Template Item approval flow
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitTemplateItemUpdateResource - the Feature Benefit Template Item Update Resource
	 * @return the FeatureBenefitTemplateItemPending
	 */
	public FeatureBenifitTemplatePending updateFeatureBenifitTemplate(String tenantId, Long id ,  FeatureBenefitTemplateItemUpdateResource featureBenefitTemplateItemUpdateResource);
	
	/**
	 * direct update Feature Benefit Template Item 
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitItemUsageResource - the Feature Benefit Item Usage Resource
	 * @return the FeatureBenefitTemplateItemPending
	 */
	public FeatureBenefitTemplateItem directUpdateFeatureBenefitTemplateItem(Long id ,  FeatureBenefitItemUsageResource featureBenefitItemUsageResource);
	
	/**
	 * find by Feature Benefit Template Id 
	 *
	 * @param feature benifit template item - id
	 * @return the FeatureBenefitTemplateItemPending
	 */
	List<FeatureBenefitTemplateItem> findByFeatureBenifitTemplateId(Long featureBenifitTemplateId);
	
	/**
	 * save Feature Benefit Template pending 
	 *
	 * @param FeatureBenifitTemplate - the Feature Benefit temmplate domain
	 * @return the FeatureBenifitTemplatePending
	 */
	public FeatureBenifitTemplatePending saveFeatureBenifitTemplatePending(FeatureBenifitTemplate featureBenifitTemplate, LendingWorkflow lendingWorkflow , CommonApproveStatus status );

	/**
	 * find by Feature Benefit Template pending 
	 *
	 * @param feature benifit template pending - id
	 * @return the FeatureBenifitTemplatePendingDetailsResponse
	 */
	public FeatureBenifitTemplatePendingDetailsResponse findDetailsByFeatureBenifitTemplatePendingId(Long id);
	
	/**
	 * Approve Feature Benefit Template pending 
	 *
	 * @param id - the Feature Benefit temmplate pending id
	 * @return the boolean value
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus ,  String usr);


}
