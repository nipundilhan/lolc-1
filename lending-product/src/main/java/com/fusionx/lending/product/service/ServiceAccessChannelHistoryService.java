package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ServiceAccessChannel;

@Service
public interface ServiceAccessChannelHistoryService {
	
	/**
	 * @param tenantId
	 * @param ServiceAccessChannel
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId,
			ServiceAccessChannel serviceAccessChannel, String historyCreatedUser);

}
