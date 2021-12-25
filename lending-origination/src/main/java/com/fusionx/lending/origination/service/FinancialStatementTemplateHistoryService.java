package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatementTemplate;

@Service
public interface FinancialStatementTemplateHistoryService {

	/**
	 * @param tenantId
	 * @param FinancialStatement
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FinancialStatementTemplate FinancialStatement, String historyCreatedUser);

}
