package com.fusionx.lending.product.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeatureBenefitItemHistory;
import com.fusionx.lending.product.repository.FeatureBenefitItemHistoryRepository;
import com.fusionx.lending.product.service.FeatureBenefitItemHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenefitItemHistoryServiceImpl implements FeatureBenefitItemHistoryService {
	
	@Autowired
	private FeatureBenefitItemHistoryRepository featureBenefitItemHistoryRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public void insertFeatureBenefitItemHistory(String tenantId, FeatureBenefitItem featureBenefitItem, String historyCreatedUser) {
		
        
        FeatureBenefitItemHistory featureBenefitItemHistory = new FeatureBenefitItemHistory();
        
        featureBenefitItemHistory.setFeatureBenefitItemId(featureBenefitItem.getId());
        featureBenefitItemHistory.setTenantId(tenantId);
        featureBenefitItemHistory.setCode(featureBenefitItem.getCode());
        featureBenefitItemHistory.setName(featureBenefitItem.getName());
        featureBenefitItemHistory.setDescription(featureBenefitItem.getDescription());
        featureBenefitItemHistory.setFeatureBenefitItemTypeId(featureBenefitItem.getFeatureBenefitItemType().getId());
        featureBenefitItemHistory.setAmount(featureBenefitItem.getAmount());
        featureBenefitItemHistory.setTextual(featureBenefitItem.getTextual());
        featureBenefitItemHistory.setIndicator(featureBenefitItem.getIndicator());
        featureBenefitItemHistory.setStatus(featureBenefitItem.getStatus());
        featureBenefitItemHistory.setCreatedDate(featureBenefitItem.getCreatedDate());
        featureBenefitItemHistory.setCreatedUser(featureBenefitItem.getCreatedUser());
        featureBenefitItemHistory.setModifiedDate(featureBenefitItem.getModifiedDate());
        featureBenefitItemHistory.setModifiedUser(featureBenefitItem.getModifiedUser());
        featureBenefitItemHistory.setVersion(featureBenefitItem.getVersion());
        featureBenefitItemHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        featureBenefitItemHistory.setHistoryCreatedUser(historyCreatedUser!= null?historyCreatedUser:LogginAuthentcation.getInstance().getUserName());
        featureBenefitItemHistory.setSyncTs(validationService.getCreateOrModifyDate());
        
        featureBenefitItemHistoryRepository.saveAndFlush(featureBenefitItemHistory);
	}

}
