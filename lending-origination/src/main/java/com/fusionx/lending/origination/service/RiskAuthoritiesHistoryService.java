package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;
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
import com.fusionx.lending.origination.domain.RiskAuthorities;

@Service
public interface RiskAuthoritiesHistoryService {

	/**
	 * @param tenantId
	 * @param RiskAuthorities
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, RiskAuthorities riskAuthorities, String historyCreatedUser);

}
