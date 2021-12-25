package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeatureBenefitGroupTypeHistory;

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
@Repository
public interface FeatureBenefitGroupTypeHistoryRepository extends JpaRepository<FeatureBenefitGroupTypeHistory, Long>{

}
