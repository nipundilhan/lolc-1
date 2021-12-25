package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
/**
 * Feature Benefit Template Item Pending Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		      			Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeatureBenefitTemplateItemPendingRepository    extends JpaRepository<FeatureBenefitTemplateItemPending, Long> {
	
	public List<FeatureBenefitTemplateItemPending> findAllByFeatureBenifitTemplatePending(FeatureBenifitTemplatePending featureBenifitTemplatePending);

	public List<FeatureBenefitTemplateItemPending> findAllByFeatureBenifitTemplatePendingAndFeatureBenefitTemplateItem(FeatureBenifitTemplatePending featureBenifitTemplatePending
			,FeatureBenefitTemplateItem featureBenefitTemplateItem);
}