package com.fusionx.lending.origination.repository;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CheckListTemplateDetailsPending;

@Repository
public interface CheckListTemplateDetailsPendingRepository
		extends JpaRepository<CheckListTemplateDetailsPending, Long> {

}
