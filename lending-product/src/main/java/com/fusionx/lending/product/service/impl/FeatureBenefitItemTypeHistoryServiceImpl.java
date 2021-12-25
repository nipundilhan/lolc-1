package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeatureBenefitItemType;
import com.fusionx.lending.product.domain.FeatureBenefitItemTypeHistory;
import com.fusionx.lending.product.repository.FeatureBenefitItemTypeHistoryRepository;
import com.fusionx.lending.product.service.FeatureBenefitItemTypeHistoryService;

/**
 * Feature Benefit Item Type Service - Feature Benefit Item Type Domain entity
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
public class FeatureBenefitItemTypeHistoryServiceImpl implements FeatureBenefitItemTypeHistoryService{
	
	@Autowired
	private FeatureBenefitItemTypeHistoryRepository repository;

	@Override
	public void saveHistory(String tenantId,FeatureBenefitItemType featureBenefitItemType, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        FeatureBenefitItemTypeHistory featureBenefitItemTypeHistory = new FeatureBenefitItemTypeHistory();
        
        featureBenefitItemTypeHistory.setTenantId(tenantId);
        featureBenefitItemTypeHistory.setFeatureBenefitItemTypeId(featureBenefitItemType.getId());
        featureBenefitItemTypeHistory.setCode(featureBenefitItemType.getCode());
        featureBenefitItemTypeHistory.setName(featureBenefitItemType.getName());
        featureBenefitItemTypeHistory.setDescription(featureBenefitItemType.getDescription());
        featureBenefitItemTypeHistory.setStatus(featureBenefitItemType.getStatus());
        featureBenefitItemTypeHistory.setCreatedDate(featureBenefitItemType.getCreatedDate());
        featureBenefitItemTypeHistory.setCreatedUser(featureBenefitItemType.getCreatedUser());
        featureBenefitItemTypeHistory.setModifiedDate(featureBenefitItemType.getModifiedDate());
        featureBenefitItemTypeHistory.setModifiedUser(featureBenefitItemType.getModifiedUser());
        featureBenefitItemTypeHistory.setFeatureBenefitItemTypeVersion(featureBenefitItemType.getVersion());
        featureBenefitItemTypeHistory.setHistoryCreatedUser(historyCreatedUser);
        featureBenefitItemTypeHistory.setHistoryCreatedDate(currentTimestamp);
        featureBenefitItemTypeHistory.setSyncTs(currentTimestamp);
		
        repository.saveAndFlush(featureBenefitItemTypeHistory);
	}

}
