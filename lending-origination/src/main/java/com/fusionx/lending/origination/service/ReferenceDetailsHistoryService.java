package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ReferenceDetails;

@Service
public interface ReferenceDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param ReferenceDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, ReferenceDetails referenceDetails, String historyCreatedUser);

}
