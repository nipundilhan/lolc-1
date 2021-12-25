package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

/**
 * Credit Interest Service - Tier Band Set Repository
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

import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.enums.CommonStatus;


@Repository
public interface PenalTierBandSetRepository extends JpaRepository<PenalTierBandSet, Long> {
	
	List<PenalTierBandSet> findByPenalInterest(PenalInterest penalInterestTemplate);

	Optional<PenalTierBandSet> findByCode(String code);

	Optional<PenalTierBandSet> findByPenalInterestIdAndIdAndStatus(Long interestTempId, Long id, CommonStatus active);

	Optional<PenalTierBandSet> findByIdAndStatus(Long interestTierBandSetId, CommonStatus active);

	Optional<PenalTierBandSet> findByPenalInterestIdAndId(Long interestTempId, Long id);

	
}
