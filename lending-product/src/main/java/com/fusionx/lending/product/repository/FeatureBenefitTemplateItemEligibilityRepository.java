package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;

/**
 * Feature Benefit Template Item Eligibility Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		      			Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeatureBenefitTemplateItemEligibilityRepository   extends JpaRepository<FeatureBenefitTemplateItemEligibility, Long> {

	List<FeatureBenefitTemplateItemEligibility> findAllByFeatureBenefitTemplateItem(FeatureBenefitTemplateItem featureBenefitTemplateItem);
	
}
