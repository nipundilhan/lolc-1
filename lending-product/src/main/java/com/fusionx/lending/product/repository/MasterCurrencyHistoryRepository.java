package com.fusionx.lending.product.repository;
/**
 * Master Def Currency Eligibility History Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.MasterCurrencyHistory;


@Repository
public interface MasterCurrencyHistoryRepository extends JpaRepository<MasterCurrencyHistory, Long>{

}
