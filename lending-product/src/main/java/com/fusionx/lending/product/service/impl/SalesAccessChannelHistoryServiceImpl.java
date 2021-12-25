package com.fusionx.lending.product.service.impl;

/**
 * Sales Access Channel service
 * @author 	Piyumi
 * @Dat     07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.domain.SalesAccessChannelHistory;
import com.fusionx.lending.product.repository.SalesAccessChannelHistoryRepository;
import com.fusionx.lending.product.service.SalesAccessChannelHistoryService;

@Component
@Transactional(rollbackFor=Exception.class)
public class SalesAccessChannelHistoryServiceImpl implements SalesAccessChannelHistoryService {

	@Autowired
	private SalesAccessChannelHistoryRepository salesAccessChannelHistoryRepository;
	
	@Override
	public void saveHistory(String tenantId, SalesAccessChannel salesAccessChannel,
			String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        SalesAccessChannelHistory salesAccessChannelHistory = new SalesAccessChannelHistory();
        
        salesAccessChannelHistory.setSalesAccessChannelId(salesAccessChannel.getId());
        salesAccessChannelHistory.setCode(salesAccessChannel.getCode());
        salesAccessChannelHistory.setName(salesAccessChannel.getName());
        salesAccessChannelHistory.setTenantId(salesAccessChannel.getTenantId());
        salesAccessChannelHistory.setStatus(salesAccessChannel.getStatus());
        salesAccessChannelHistory.setCreatedDate(salesAccessChannel.getCreatedDate());
        salesAccessChannelHistory.setCreatedUser(salesAccessChannel.getCreatedUser());
        salesAccessChannelHistory.setModifiedDate(salesAccessChannel.getModifiedDate());
        salesAccessChannelHistory.setModifiedUser(salesAccessChannel.getModifiedUser());
        salesAccessChannelHistory.setHistoryCreatedDate(currentTimestamp);
        salesAccessChannelHistory.setHistoryCreatedUser(historyCreatedUser);
        salesAccessChannelHistory.setSyncTs(currentTimestamp);
        salesAccessChannelHistoryRepository.saveAndFlush(salesAccessChannelHistory);

	}

}
