package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.UserMappingDetails;

@Service
public interface UserMappingDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param UserMappingDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, UserMappingDetails userMappingDetails, String historyCreatedUser);

}
