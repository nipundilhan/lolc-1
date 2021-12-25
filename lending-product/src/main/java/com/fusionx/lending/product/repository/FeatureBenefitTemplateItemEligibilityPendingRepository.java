package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;

/**
 * Feature Benefit Template Item Eligibility pending Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		      			Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeatureBenefitTemplateItemEligibilityPendingRepository   extends JpaRepository<FeatureBenefitTemplateItemEligibilityPending, Long> {

	
	List<FeatureBenefitTemplateItemEligibilityPending> findAllByFeatureBenefitTemplatePending(FeatureBenifitTemplatePending featureBenefitTemplatePending);
	
	List<FeatureBenefitTemplateItemEligibilityPending> findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItemEligibility
		(FeatureBenifitTemplatePending featureBenefitTemplatePending ,FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility );

	List<FeatureBenefitTemplateItemEligibilityPending> findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItem
			(FeatureBenifitTemplatePending featureBenefitTemplatePending ,FeatureBenefitTemplateItem featureBenefitTemplateItem );

}
