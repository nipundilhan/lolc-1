package com.fusionx.lending.product.repository;

/**
 * Interest Tier Band History Repository 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   22-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestTierBandHistory;

@Repository
public interface InterestTierBandHistoryRepository extends JpaRepository<InterestTierBandHistory, Long>{

}
