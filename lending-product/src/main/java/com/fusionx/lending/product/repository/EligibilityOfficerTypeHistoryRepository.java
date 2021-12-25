package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.product.domain.EligibilityOfficerTypeHistory;

/**
 * Eligibility Officer Type History Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityOfficerTypeHistoryRepository extends JpaRepository<EligibilityOfficerTypeHistory, Long> {

}
