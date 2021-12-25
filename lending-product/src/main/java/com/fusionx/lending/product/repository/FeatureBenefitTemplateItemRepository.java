package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandard;
/**
 * Feature Benefit Template Item Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		      			Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeatureBenefitTemplateItemRepository  extends JpaRepository<FeatureBenefitTemplateItem, Long> {

	
	List<FeatureBenefitTemplateItem> findAllByFeatureBenefitTemplate(FeatureBenifitTemplate featureBenefitTemplate);
	
	List<FeatureBenefitTemplateItem> findByFeatureBenefitItem(FeatureBenefitItem featureBenefitItem);

}
