package com.fusionx.lending.origination.repository;

import java.util.List;
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

import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface RiskRatingLevelsRepository extends JpaRepository<RiskRatingLevels, Long> {

	public List<RiskRatingLevels> findByStatus(CommonStatus status);

	public List<RiskRatingLevels> findByRiskTypeId(Long riskType);
}
