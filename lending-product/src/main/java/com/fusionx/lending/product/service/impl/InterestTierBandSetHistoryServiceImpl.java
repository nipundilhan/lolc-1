package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.InterestTierBandSetHistory;
import com.fusionx.lending.product.repository.InterestTierBandSetHistoryRepository;
import com.fusionx.lending.product.service.InterestTierBandSetHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class InterestTierBandSetHistoryServiceImpl implements InterestTierBandSetHistoryService {
	@Autowired
 	private  InterestTierBandSetHistoryRepository interestTierBandSetHistoryRepository;
	
 	@Autowired
 	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, InterestTierBandSet interestTierBandSet, String historyCreatedUser) {
		
			 InterestTierBandSetHistory interestTierBandSetHistory = new InterestTierBandSetHistory();
			 interestTierBandSetHistory.setTenantId(tenantId);
			 interestTierBandSetHistory.setInterestTemplateId(interestTierBandSet.getInterestTemplate().getId());
			 interestTierBandSetHistory.setTierBandMethodId(interestTierBandSet.getTierBandMethod().getId());
			 interestTierBandSetHistory.setCalculationMethodId(interestTierBandSet.getCalculationMethod().getId());
			 interestTierBandSetHistory.setCode(interestTierBandSet.getCode());
			 interestTierBandSetHistory.setNote(interestTierBandSet.getNote());
			 interestTierBandSetHistory.setApprovedDate(interestTierBandSet.getApprovedDate());
			 interestTierBandSetHistory.setApprovedUser(interestTierBandSet.getApprovedUser());
	         interestTierBandSetHistory.setStatus(interestTierBandSet.getStatus());
	         interestTierBandSetHistory.setCreatedDate(interestTierBandSet.getCreatedDate());
	         interestTierBandSetHistory.setCreatedUser(interestTierBandSet.getCreatedUser());
	         interestTierBandSetHistory.setModifiedDate(interestTierBandSet.getModifiedDate());
	         interestTierBandSetHistory.setModifiedUser(interestTierBandSet.getModifiedUser());
	         interestTierBandSetHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	         interestTierBandSetHistory.setHistoryCreatedUser(historyCreatedUser);
	         interestTierBandSetHistory.setVersion(interestTierBandSet.getVersion());
	         interestTierBandSetHistory.setSyncTs(validationService.getCreateOrModifyDate());      
	         interestTierBandSetHistoryRepository.save(interestTierBandSetHistory);
	        	
		
	}
 }