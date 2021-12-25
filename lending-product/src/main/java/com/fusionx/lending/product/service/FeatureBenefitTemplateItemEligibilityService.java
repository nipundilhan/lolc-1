package com.fusionx.lending.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.resources.FeatureBenefitEligibilityUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityUpdateResource;

/**
 * Feature Benefit Template Item Eligibility Service
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		         	NipunDilhan      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface FeatureBenefitTemplateItemEligibilityService {
	
	/**
	 * Save and Feature Benefit Template Item Eligibility
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitTemplateItemEligibilityAddResource - the Feature Benefit Template Item Eligibility Add Resource
	 * @return the FeatureBenefitTemplateItemEligibility
	 */
	public FeatureBenefitTemplateItemEligibility createFeatureBenefitTemplateItemEligibility(String tenantId,FeatureBenefitTemplateItemEligibilityAddResource featureBenefitTemplateItemEligibilityAddResource);

	/**
	 * direct update and Feature Benefit Template Item Eligibility
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitEligibilityUsageResource - the Feature Benefit Template Item Eligibility Update Resource
	 * @param  FeatureBenefitTemplateItem - id 
	 * @return the FeatureBenefitTemplateItemEligibility
	 */
	public FeatureBenefitTemplateItemEligibility directUpdateFeatureBenefitTemplateItemEligibility(Long id,
			FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource);
	
	/**
	 * find by Feature Benefit Template
	 *
	 * @param  FeatureBenefitTemplate - id 
	 * @return the  List of FeatureBenefitTemplateItemEligibility
	 */
	public List<FeatureBenefitTemplateItemEligibility> findByFeatureBenifitTemplateItem(Long featureBenifitTemplateItemId);

	/**
	 * update pending and Feature Benefit Template Item Eligibility
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitEligibilityUsageResource - the Feature Benefit Template Item Eligibility Update Resource
	 * @param FeatureBenefitTemplateItemEligibilityUpdateResource - Feature Benefit Template Item Eligibility Update Resource
	 * @return the FeatureBenifitTemplatePending
	 */
	public FeatureBenifitTemplatePending updateupdateFeatureBenifitTemplate(String tenantId, Long id ,  FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource);

	/**
	 * update pending and Feature Benefit Template Item Eligibility
	 *
	 * @param tenantId - the tenant id
	 * @param FeatureBenefitTemplateItemEligibilityPending - Feature Benefit Template Item Eligibility Pending Domain
	 */
	public void updateFeatureBenifitTemplateItemEligibility(FeatureBenefitTemplateItemEligibilityPending featureBenefitTemplateItemEligibilityPending , String user);
}
