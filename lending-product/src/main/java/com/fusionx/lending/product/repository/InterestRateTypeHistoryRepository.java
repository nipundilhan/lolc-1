package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestRateTypeHistory;

/**
 * Interest Rate Type History Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface InterestRateTypeHistoryRepository extends JpaRepository<InterestRateTypeHistory, Long> {

}
