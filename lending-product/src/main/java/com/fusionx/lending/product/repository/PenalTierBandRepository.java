package com.fusionx.lending.product.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Credit Interest Service - Tier Band Repository
 * @author 	Amal
 * @Date    21-08-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   21-08-2019   FX-1508      FX-1600    Amal      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.PenalTierBand;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface PenalTierBandRepository extends JpaRepository<PenalTierBand, Long> {
	
	List<PenalTierBand> findByPenalTierBandSetAndStatus(PenalTierBandSet interestTierBandSet , CommonStatus status);

	Optional<PenalTierBand> findByCode(String code);

	Optional<PenalTierBand> findByPenalTierBandSetIdAndId(Long penalTierBandSetId, Long id);

	List<PenalTierBand> findByPenalTierBandSet(PenalTierBandSet penalTierBandSet);
	
	List<PenalTierBand> findByPenalTierBandSetAndEffectiveDate(PenalTierBandSet penalTierBandSet,Date date);
	
}



