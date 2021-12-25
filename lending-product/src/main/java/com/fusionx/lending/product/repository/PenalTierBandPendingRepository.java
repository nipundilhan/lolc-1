package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestTierBandPending;
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.domain.PenalTierBandPending;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Penal Tier Band Pending
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  28-08-2020   	                        Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface PenalTierBandPendingRepository extends JpaRepository<PenalTierBandPending, Long>{

	List<PenalTierBandPending> findByPenalTierBandSetAndApproveStatus(PenalTierBandSet interestTierBandSet, CommonApproveStatus status);

	List<PenalTierBandPending> findByPenalTierBandSetPendingAndStatus(PenalTierBandSetPending penalTierBandSetPending, CommonStatus status);
}
