package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeatureBenefitGroupType;
import com.fusionx.lending.product.domain.FeatureBenefitGroupTypeHistory;
import com.fusionx.lending.product.repository.FeatureBenefitGroupTypeHistoryRepository;
import com.fusionx.lending.product.service.FeatureBenefitGroupTypeHistoryService;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenefitGroupTypeHistoryServiceImpl implements FeatureBenefitGroupTypeHistoryService{
	
	@Autowired
	private FeatureBenefitGroupTypeHistoryRepository repository;

	@Override
	public void saveHistory(String tenantId,FeatureBenefitGroupType featureBenefitGroupType, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        FeatureBenefitGroupTypeHistory featureBenefitGroupTypeHistory = new FeatureBenefitGroupTypeHistory();
        
        featureBenefitGroupTypeHistory.setTenantId(tenantId);
        featureBenefitGroupTypeHistory.setFeatureBenefitGroupTypeId(featureBenefitGroupType.getId());
        featureBenefitGroupTypeHistory.setCode(featureBenefitGroupType.getCode());
        featureBenefitGroupTypeHistory.setName(featureBenefitGroupType.getName());
        featureBenefitGroupTypeHistory.setDescription(featureBenefitGroupType.getDescription());
        featureBenefitGroupTypeHistory.setStatus(featureBenefitGroupType.getStatus());
        featureBenefitGroupTypeHistory.setCreatedDate(featureBenefitGroupType.getCreatedDate());
        featureBenefitGroupTypeHistory.setCreatedUser(featureBenefitGroupType.getCreatedUser());
        featureBenefitGroupTypeHistory.setModifiedDate(featureBenefitGroupType.getModifiedDate());
        featureBenefitGroupTypeHistory.setModifiedUser(featureBenefitGroupType.getModifiedUser());
        featureBenefitGroupTypeHistory.setFeatureBenefitGroupTypeVersion(featureBenefitGroupType.getVersion());
        featureBenefitGroupTypeHistory.setHistoryCreatedUser(historyCreatedUser);
        featureBenefitGroupTypeHistory.setHistoryCreatedDate(currentTimestamp);
        featureBenefitGroupTypeHistory.setSyncTs(currentTimestamp);
		
        repository.saveAndFlush(featureBenefitGroupTypeHistory);
	}

}
