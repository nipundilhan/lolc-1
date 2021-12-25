package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.product.domain.EligibilityCurrencyHistory;

/**
 * Eligibility Currency History Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityCurrencyHistoryRepository extends JpaRepository<EligibilityCurrencyHistory, Long> {

}
