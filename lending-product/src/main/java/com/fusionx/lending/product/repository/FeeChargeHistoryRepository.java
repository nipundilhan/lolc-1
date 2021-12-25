package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeHistory;

/**
 * Fee Charge  Pending Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		      			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeHistoryRepository extends JpaRepository<FeeChargeHistory, Long> {


}
