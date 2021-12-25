package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateHistory;
import com.fusionx.lending.origination.repository.FinancialStatementHistoryRepository;
import com.fusionx.lending.origination.service.FinancialStatementTemplateHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementTemplateHistoryServiceImpl implements FinancialStatementTemplateHistoryService {

	@Autowired
	private FinancialStatementHistoryRepository financialStatementHistoryRepository;

	@Override
	public void saveHistory(String tenantId, FinancialStatementTemplate financialStatement, String historyCreatedUser) {
		LoggerRequest.getInstance().logInfo("FinancialStatement saveHistory");
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		LoggerRequest.getInstance().logInfo("FinancialStatement saveHistory 01");
		FinancialStatementTemplateHistory financialStatementHistory = new FinancialStatementTemplateHistory();
		LoggerRequest.getInstance().logInfo("FinancialStatement saveHistory 02");
		financialStatementHistory.setTenantId(tenantId);
		financialStatementHistory.setFinancialStatementId(financialStatement.getId());
		financialStatementHistory.setTemplateCode(financialStatement.getCode());
		financialStatementHistory.setTemplateName(financialStatement.getName());
		financialStatementHistory.setStatementTypeId(financialStatement.getFinancialStatementType().getId());
		financialStatementHistory.setStatementType(financialStatement.getStatementType());
		financialStatementHistory.setDescription(financialStatement.getDescription());
		financialStatementHistory.setStatus(financialStatement.getStatus());
		financialStatementHistory.setCreatedDate(financialStatement.getCreatedDate());
		financialStatementHistory.setCreatedUser(financialStatement.getCreatedUser());
		financialStatementHistory.setModifiedDate(financialStatement.getModifiedDate());
		financialStatementHistory.setModifiedUser(financialStatement.getModifiedUser());
		financialStatementHistory.setVersion(financialStatement.getVersion());
		financialStatementHistory.setHistoryCreatedUser(historyCreatedUser);
		financialStatementHistory.setHistoryCreatedDate(currentTimestamp);
		financialStatementHistory.setSyncTs(currentTimestamp);
		LoggerRequest.getInstance().logInfo("FinancialStatement saveHistory 03");
		financialStatementHistoryRepository.saveAndFlush(financialStatementHistory);
		LoggerRequest.getInstance().logInfo("FinancialStatement saveHistory 04");
	}

}
