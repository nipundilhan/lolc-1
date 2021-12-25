package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.domain.FeatureBenefitEligibilityTypeHistory;
import com.fusionx.lending.product.repository.FeatureBenefitEligibilityTypeHistoryRepository;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeHistoryService;

/**
 * Feature Benefit Eligibility Type Service History Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021             				 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenefitEligibilityTypeHistoryServiceImpl implements FeatureBenefitEligibilityTypeHistoryService {
	
	@Autowired
	private FeatureBenefitEligibilityTypeHistoryRepository repository;

	@Override
	public void saveHistory(String tenantId,
			FeatureBenefitEligibilityType featureBenefitEligibilityType, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        FeatureBenefitEligibilityTypeHistory featureBenefitEligibilityTypeHistory = new FeatureBenefitEligibilityTypeHistory();
        
        featureBenefitEligibilityTypeHistory.setTenantId(tenantId);
        featureBenefitEligibilityTypeHistory.setFeatureBenefitEligiTypeId(featureBenefitEligibilityType.getId());
        featureBenefitEligibilityTypeHistory.setCode(featureBenefitEligibilityType.getCode());
        featureBenefitEligibilityTypeHistory.setName(featureBenefitEligibilityType.getName());
        featureBenefitEligibilityTypeHistory.setDescription(featureBenefitEligibilityType.getDescription());
        featureBenefitEligibilityTypeHistory.setStatus(featureBenefitEligibilityType.getStatus());
        featureBenefitEligibilityTypeHistory.setCreatedDate(featureBenefitEligibilityType.getCreatedDate());
        featureBenefitEligibilityTypeHistory.setCreatedUser(featureBenefitEligibilityType.getCreatedUser());
        featureBenefitEligibilityTypeHistory.setModifiedDate(featureBenefitEligibilityType.getModifiedDate());
        featureBenefitEligibilityTypeHistory.setModifiedUser(featureBenefitEligibilityType.getModifiedUser());
        featureBenefitEligibilityTypeHistory.setVersion(featureBenefitEligibilityType.getVersion());
        featureBenefitEligibilityTypeHistory.setHistoryCreatedUser(historyCreatedUser);
        featureBenefitEligibilityTypeHistory.setHistoryCreatedDate(currentTimestamp);
        featureBenefitEligibilityTypeHistory.setSyncTs(currentTimestamp);
		
        repository.saveAndFlush(featureBenefitEligibilityTypeHistory);
	}

}
