package com.fusionx.lending.product.service.impl;
/**
 * Interest Template Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplateHistory;
import com.fusionx.lending.product.repository.InterestTemplateHistoryRepository;
import com.fusionx.lending.product.service.InterestTemplateHistoryService;

@Component
@Transactional(rollbackFor=Exception.class)
public class InterestTemplateHistoryServiceImpl implements InterestTemplateHistoryService {
	
	@Autowired
	private InterestTemplateHistoryRepository interestTemplateHistoryRepository;


	@Override
	public void insertinterestTemplateHistory(String tenantId, InterestTemplate interestTemplate, String historyCreatedUser) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        InterestTemplateHistory interestTemplateHistory = new InterestTemplateHistory();
        
        interestTemplateHistory.setTenantId(interestTemplate.getTenantId());
        interestTemplateHistory.setCode(interestTemplate.getCode());
        interestTemplateHistory.setInterestTemplateId(interestTemplate.getId());
        interestTemplateHistory.setName(interestTemplate.getName());
        interestTemplateHistory.setStatus(interestTemplate.getStatus());
        interestTemplateHistory.setCreatedDate(interestTemplate.getCreatedDate());
        interestTemplateHistory.setCreatedUser(interestTemplate.getCreatedUser());
        interestTemplateHistory.setModifiedDate(interestTemplate.getModifiedDate());
        interestTemplateHistory.setModifiedUser(interestTemplate.getModifiedUser());
        interestTemplateHistory.setHistoryCreatedDate(currentTimestamp);
        interestTemplateHistory.setHistoryCreatedUser(historyCreatedUser);
        interestTemplateHistory.setSyncTs(currentTimestamp);
        interestTemplateHistoryRepository.saveAndFlush(interestTemplateHistory);
        
	}

}
