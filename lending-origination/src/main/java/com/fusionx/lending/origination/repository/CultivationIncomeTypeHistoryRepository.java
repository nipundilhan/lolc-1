package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.CultivationIncomeTypeHistory;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
public interface CultivationIncomeTypeHistoryRepository extends JpaRepository<CultivationIncomeTypeHistory, Long> {

}
