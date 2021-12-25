package com.fusionx.lending.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Penal Interest Pending
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  21-08-2020   	                        Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface PenalInterestPendingRepository extends JpaRepository<PenalInterestPending, Long>{

	Optional<PenalInterestPending> findByPenalInterest(PenalInterest penalInterest);
	
	Optional<PenalInterestPending> findByIdAndStatus(Long id, CommonStatus status);
}
