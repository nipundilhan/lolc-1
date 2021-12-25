package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OfficerEligibilityHistory;

/**
* Officer Eligibility History Repository
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   09-06-2020   			   	        	Thamokshi    Created
*    
********************************************************************************************************
*/

@Repository
public interface OfficerEligibilityHistoryRepository extends JpaRepository<OfficerEligibilityHistory, Long>{

}
