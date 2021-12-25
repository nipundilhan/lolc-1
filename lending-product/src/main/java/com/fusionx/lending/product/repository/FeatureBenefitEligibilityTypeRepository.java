package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Feature BenefitEligibility Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		      			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeatureBenefitEligibilityTypeRepository extends JpaRepository<FeatureBenefitEligibilityType, Long> {

	public List<FeatureBenefitEligibilityType> findByStatus(CommonStatus status);

	public List<FeatureBenefitEligibilityType> findByNameContaining(String name);

	public Optional<FeatureBenefitEligibilityType> findByCode(String code);

	public Optional<FeatureBenefitEligibilityType> findByCodeAndIdNotIn(String code, Long id);

}
