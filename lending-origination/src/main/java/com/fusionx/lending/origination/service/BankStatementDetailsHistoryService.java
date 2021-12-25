package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BankStatementDetails;

@Service
public interface BankStatementDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param BankStatementDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, BankStatementDetails bankStatementDetails, String historyCreatedUser);

}
