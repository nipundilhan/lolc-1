package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeCapHistory;

/**
 * Fee Charge Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-07-2021      		      			Dilhan      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface FeeChargeCapHistoryRepository extends JpaRepository<FeeChargeCapHistory, Long>{

}
