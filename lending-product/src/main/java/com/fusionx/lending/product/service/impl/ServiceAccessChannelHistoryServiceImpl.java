package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.BrandHistory;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.domain.ServiceAccessChannelHistory;
import com.fusionx.lending.product.repository.BrandHistoryRepository;
import com.fusionx.lending.product.repository.ServiceAccessChannelHistoryRepository;
import com.fusionx.lending.product.service.ServiceAccessChannelHistoryService;

/**
 * Service Access Channel History Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021             				NipunD        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class ServiceAccessChannelHistoryServiceImpl implements ServiceAccessChannelHistoryService {

	
	@Autowired
	private  ServiceAccessChannelHistoryRepository  serviceAccessChannelHistoryRepository;

	//@Override
	public void saveHistory(String tenantId,
			ServiceAccessChannel serviceAccessChannel, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        ServiceAccessChannelHistory serviceAccessChannelHistory = new ServiceAccessChannelHistory();
        
        serviceAccessChannelHistory.setTenantId(tenantId);
        serviceAccessChannelHistory.setServiceAccessChannelId(serviceAccessChannel.getId());
        serviceAccessChannelHistory.setCode(serviceAccessChannel.getCode());
        serviceAccessChannelHistory.setName(serviceAccessChannel.getName());
        serviceAccessChannelHistory.setDescription(serviceAccessChannel.getDescription());
        serviceAccessChannelHistory.setStatus(serviceAccessChannel.getStatus());
        serviceAccessChannelHistory.setCreatedDate(serviceAccessChannel.getCreatedDate());
        serviceAccessChannelHistory.setCreatedUser(serviceAccessChannel.getCreatedUser());
        serviceAccessChannelHistory.setModifiedDate(serviceAccessChannel.getModifiedDate());
        serviceAccessChannelHistory.setModifiedUser(serviceAccessChannel.getModifiedUser());
        serviceAccessChannelHistory.setVersion(serviceAccessChannel.getVersion());
        serviceAccessChannelHistory.setHistoryCreatedUser(historyCreatedUser);
        serviceAccessChannelHistory.setHistoryCreatedDate(currentTimestamp);
        serviceAccessChannelHistory.setSyncTs(currentTimestamp);
		
        serviceAccessChannelHistoryRepository.saveAndFlush(serviceAccessChannelHistory);
	}
}
