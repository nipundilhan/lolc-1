package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityCustomerTypeHistory;

/**
 * EligibilityResidencyEligibility
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_365  			FXL-56			Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityCustomerTypeHistoryRepository extends JpaRepository<EligibilityCustomerTypeHistory, Long>{

}
