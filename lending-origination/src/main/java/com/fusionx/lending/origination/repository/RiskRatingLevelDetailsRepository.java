package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Risk Rating Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-338 		 FXL-684 	Dilki        Created
 *    
 ********************************************************************************************************
 */

import com.fusionx.lending.origination.domain.RiskRatingLevelDetails;

@Repository
public interface RiskRatingLevelDetailsRepository extends JpaRepository<RiskRatingLevelDetails, Long> {
}
