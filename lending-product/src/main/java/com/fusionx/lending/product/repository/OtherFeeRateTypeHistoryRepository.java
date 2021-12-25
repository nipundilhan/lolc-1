package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OtherFeeRateTypeHistory;

/**
 * Other Fee Rate Type History Repository
 *  
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherFeeRateTypeHistoryRepository extends JpaRepository<OtherFeeRateTypeHistory, Long>{

}
