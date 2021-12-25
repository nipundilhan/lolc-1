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

import com.fusionx.lending.product.domain.FeatureBenefitItemType;

@Repository
public interface FeatureBenefitItemTypeRepository extends JpaRepository<FeatureBenefitItemType, Long> {
	
	Optional<FeatureBenefitItemType> findById(Long id);
	Optional<FeatureBenefitItemType> findByCode(String code);
	Collection<FeatureBenefitItemType>  findByNameContaining(String name);
	Collection<FeatureBenefitItemType> findByStatus(String status);
	public List<FeatureBenefitItemType> findAll();
	Optional<FeatureBenefitItemType> findByIdAndNameAndStatus(Long id, String name, String status);
	
	List<FeatureBenefitItemType> findByCodeAndIdNotIn(String code, Long id);
	
}
