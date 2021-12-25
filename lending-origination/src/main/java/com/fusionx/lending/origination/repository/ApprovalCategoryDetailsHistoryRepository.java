package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryDetailsHistory;

public interface ApprovalCategoryDetailsHistoryRepository extends JpaRepository<ApprovalCategoryDetailsHistory, Long> {

}
