package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeDetailsHistory;

/**
 * Fee Charge Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-08-2021      		      			Dilhan      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface FeeChargeDetailsHistoryRepository extends JpaRepository<FeeChargeDetailsHistory, Long>{

}
