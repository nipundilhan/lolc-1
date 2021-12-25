package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeatureBenefEligiHistory;
import com.fusionx.lending.product.domain.FeatureBenefitEligibility;
import com.fusionx.lending.product.repository.FeatureBenefEligiHistoryRepository;
import com.fusionx.lending.product.service.FeatureBenefEligiHistoryService;

/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class FeatureBenefEligiHistoryServiceImpl implements FeatureBenefEligiHistoryService {

	@Autowired
	private FeatureBenefEligiHistoryRepository repo;

	@Override
	public FeatureBenefEligiHistory addFeatureBenefEligiHistory(String tenantId,
			FeatureBenefitEligibility featureBenefitEligibility, String cUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		FeatureBenefEligiHistory newFeatureBenefEligiHistory = new FeatureBenefEligiHistory();
	
	   /*----------------------------------------------------------------------------------------------------------------*/
		newFeatureBenefEligiHistory.setFeatureBenefitEligiId(featureBenefitEligibility.getId());
		newFeatureBenefEligiHistory.setAmount(featureBenefitEligibility.getAmount());
		newFeatureBenefEligiHistory.setDescription(featureBenefitEligibility.getDescription());
		newFeatureBenefEligiHistory.setIndicator(featureBenefitEligibility.getIndicator());
		newFeatureBenefEligiHistory.setName(featureBenefitEligibility.getName());
		
		 if (featureBenefitEligibility.getFeatureBenefitEligiType()!=null) {
			 newFeatureBenefEligiHistory.setTypeId(featureBenefitEligibility.getFeatureBenefitEligiType().getId());
			 }

		newFeatureBenefEligiHistory.setPeriodId(featureBenefitEligibility.getPeriodId());
		newFeatureBenefEligiHistory.setTexual(featureBenefitEligibility.getTexual());

		newFeatureBenefEligiHistory.setCreatedDate(featureBenefitEligibility.getCreatedDate());
		newFeatureBenefEligiHistory.setCreatedUser(featureBenefitEligibility.getCreatedUser());
		newFeatureBenefEligiHistory.setStatus(featureBenefitEligibility.getStatus());
		newFeatureBenefEligiHistory.setModifiedDate(featureBenefitEligibility.getModifiedDate());
		newFeatureBenefEligiHistory.setModifiedUser(featureBenefitEligibility.getModifiedUser());
		newFeatureBenefEligiHistory.setFeatureBenefitEligiVer(featureBenefitEligibility.getVersion());
		newFeatureBenefEligiHistory.setTenantId(featureBenefitEligibility.getTenantId());
		newFeatureBenefEligiHistory.setHcreatedDate(currentTimestamp);
		newFeatureBenefEligiHistory.setHcreatedUser(cUser);
		newFeatureBenefEligiHistory.setSyncTs(currentTimestamp);
		
		newFeatureBenefEligiHistory = repo.saveAndFlush(newFeatureBenefEligiHistory);		
	
		return newFeatureBenefEligiHistory;
	}

	
  
}
