package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargeDetailsPending;
import com.fusionx.lending.product.domain.FeeChargePending;

/**
 * Fee Charge Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-07-2021      		      			Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeDetailsPendingRepository extends JpaRepository<FeeChargeDetailsPending, Long>{

	List<FeeChargeDetailsPending> findAllByFeeChargePending(FeeChargePending feeChargePending);
}
