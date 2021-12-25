package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityHistory;

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
public interface FeatureBenefitTemplateItemEligibilityHistoryRepository   extends JpaRepository<FeatureBenefitTemplateItemEligibilityHistory, Long> {

}
