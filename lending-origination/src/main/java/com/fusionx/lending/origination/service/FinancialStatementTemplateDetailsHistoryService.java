package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;

@Service
public interface FinancialStatementTemplateDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param FinancialStatementTemplateDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FinancialStatementTemplateDetails financialStatementDetails,
			String historyCreatedUser);

}
