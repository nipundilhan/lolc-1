package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
import com.fusionx.lending.origination.domain.RiskAuthoritiesHistory;
import com.fusionx.lending.origination.repository.RiskAuthoritiesHistoryRepository;
import com.fusionx.lending.origination.service.RiskAuthoritiesHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class RiskAuthoritiesHistoryServiceImpl implements RiskAuthoritiesHistoryService {

	@Autowired
	private RiskAuthoritiesHistoryRepository riskAuthoritiesHistoryRepository;

	@Override
	public void saveHistory(String tenantId, RiskAuthorities riskAuthorities, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		RiskAuthoritiesHistory riskAuthoritiesHistory = new RiskAuthoritiesHistory();

		riskAuthoritiesHistory.setTenantId(tenantId);
		riskAuthoritiesHistory.setRiskAuthoritiesId(riskAuthorities.getId());
		riskAuthoritiesHistory.setCode(riskAuthorities.getCode());
		riskAuthoritiesHistory.setName(riskAuthorities.getName());
		riskAuthoritiesHistory.setDescription(riskAuthorities.getDescription());
		riskAuthoritiesHistory.setStatus(riskAuthorities.getStatus());
		riskAuthoritiesHistory.setCreatedDate(riskAuthorities.getCreatedDate());
		riskAuthoritiesHistory.setCreatedUser(riskAuthorities.getCreatedUser());
		riskAuthoritiesHistory.setModifiedDate(riskAuthorities.getModifiedDate());
		riskAuthoritiesHistory.setModifiedUser(riskAuthorities.getModifiedUser());
		riskAuthoritiesHistory.setVersion(riskAuthorities.getVersion());
		riskAuthoritiesHistory.setHistoryCreatedUser(historyCreatedUser);
		riskAuthoritiesHistory.setHistoryCreatedDate(currentTimestamp);
		riskAuthoritiesHistory.setSyncTs(currentTimestamp);

		riskAuthoritiesHistoryRepository.saveAndFlush(riskAuthoritiesHistory);
	}

}
