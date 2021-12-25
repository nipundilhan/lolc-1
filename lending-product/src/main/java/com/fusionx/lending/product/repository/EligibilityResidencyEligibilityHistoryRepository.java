package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityResidencyEligibilityHistory;

/**
 * EligibilityResidencyEligibility
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityResidencyEligibilityHistoryRepository extends JpaRepository<EligibilityResidencyEligibilityHistory, Long>{

}
