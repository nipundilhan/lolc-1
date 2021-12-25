package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.CheckListItemHistory;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
public interface CheckListItemHistoryRepository extends JpaRepository<CheckListItemHistory, Long> {

}
