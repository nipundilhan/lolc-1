package com.fusionx.lending.product.repository;

import java.util.Collection;
/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitEligibility;

@Repository
public interface FeatureBenefitEligibilityRepository extends JpaRepository<FeatureBenefitEligibility, Long> {
	
	Collection<FeatureBenefitEligibility> findByStatus(String status);
	Collection<FeatureBenefitEligibility> findByNameContaining(String name);
	Collection<FeatureBenefitEligibility> findByFeatureBenefitEligiTypeCode(String code);
	Collection<FeatureBenefitEligibility> findByCode(String code);
	public Boolean existsByFeatureBenefitEligiTypeId(Long featureBenefitEligiTypeId);
	public Boolean existsByFeatureBenefitEligiTypeIdAndIdNotIn(Long featureBenefitEligiTypeId,Long id);
	
	public Boolean existsByCode(String code);
	
	public Boolean existsByCodeAndIdNotIn(String code,Long id);
}
