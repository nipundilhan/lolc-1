package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.domain.OfficerEligibilityHistory;
import com.fusionx.lending.product.repository.OfficerEligibilityHistoryRepository;
import com.fusionx.lending.product.service.OfficerEligibilityHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class OfficerEligibilityHistoryServiceImpl implements OfficerEligibilityHistoryService{

	@Autowired
	private OfficerEligibilityHistoryRepository officerEligibilityHistoryRepository;

	@Override
	public void insertOfficerEligibilityHistory(String tenantId, OfficerEligibility officerEligibility,String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        OfficerEligibilityHistory officerEligibilityHistory = new OfficerEligibilityHistory();
        
        officerEligibilityHistory.setOfficerEligibilityId(officerEligibility.getId());
        officerEligibilityHistory.setTenantId(officerEligibility.getTenantId());
        officerEligibilityHistory.setOfficerTypeId(officerEligibility.getOfficerTypeId());
        officerEligibilityHistory.setCode(officerEligibility.getCode());
        officerEligibilityHistory.setMinAmount(officerEligibility.getMinAmount());
        officerEligibilityHistory.setMaxAmount(officerEligibility.getMaxAmount());
        officerEligibilityHistory.setStatus(officerEligibility.getStatus());
        officerEligibilityHistory.setCreatedDate(officerEligibility.getCreatedDate());
        officerEligibilityHistory.setCreatedUser(officerEligibility.getCreatedUser());
        officerEligibilityHistory.setModifiedDate(officerEligibility.getModifiedDate());
        officerEligibilityHistory.setModifiedUser(officerEligibility.getModifiedUser());
        officerEligibilityHistory.setOfficerEligibilityVersion(officerEligibility.getVersion());
        officerEligibilityHistory.setHistoryCreatedDate(currentTimestamp);
        officerEligibilityHistory.setHistoryCreatedUser(historyCreatedUser);
        officerEligibilityHistory.setSyncTs(currentTimestamp);
        officerEligibilityHistory.setVersion(null);
        officerEligibilityHistoryRepository.saveAndFlush(officerEligibilityHistory);
		
	}
}
