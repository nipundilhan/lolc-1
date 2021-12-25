package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Penal Tier Band Set Pending
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  28-08-2020   	                        Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface PenalTierBandSetPendingRepository extends JpaRepository<PenalTierBandSetPending, Long>{

	List<PenalTierBandSetPending> findByPenalInterest(PenalInterest penalInterestTemplate);
	
	Optional<PenalTierBandSetPending> findByIdAndStatus(Long id, CommonStatus status);
	
	List<PenalTierBandSetPending> findByPenalInterestPendingAndStatus(PenalInterestPending penalInterestPending, CommonStatus status);
	//List<PenalTierBandSetPending> findByPenalInterestPendingId(Long id);
}
