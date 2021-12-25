package com.fusionx.lending.product.repository;

import java.util.Collection;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitGroupType;

@Repository
public interface FeatureBenefitGroupTypeRepository extends JpaRepository<FeatureBenefitGroupType, Long> {
	
	Optional<FeatureBenefitGroupType> findById(Long id);
	Optional<FeatureBenefitGroupType> findByCode(String code);
	Collection<FeatureBenefitGroupType> findByNameContaining(String name);
	Collection<FeatureBenefitGroupType> findByStatus(String status);
	public List<FeatureBenefitGroupType> findAll();

}
