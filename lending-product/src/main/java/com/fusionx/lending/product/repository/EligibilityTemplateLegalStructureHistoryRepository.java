package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructureHistory;

/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Repository
public interface EligibilityTemplateLegalStructureHistoryRepository
		extends JpaRepository<EligibilityTemplateLegalStructureHistory, Long> {

}
