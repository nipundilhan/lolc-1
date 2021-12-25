package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.RiskAuthoritiesHistory;

public interface RiskAuthoritiesHistoryRepository extends JpaRepository<RiskAuthoritiesHistory, Long> {

}
