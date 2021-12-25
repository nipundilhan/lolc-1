package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetailsHistory;
import com.fusionx.lending.origination.repository.FinancialStatementTemplateDetailsHistoryRepository;
import com.fusionx.lending.origination.service.FinancialStatementTemplateDetailsHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementTemplateDetailsHistoryServiceImpl implements FinancialStatementTemplateDetailsHistoryService {

	@Autowired
	private FinancialStatementTemplateDetailsHistoryRepository financialStatementDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, FinancialStatementTemplateDetails financialStatementDetails,
			String historyCreatedUser) {
		LoggerRequest.getInstance().logInfo("FinancialStatementDetails saveHistory");
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		LoggerRequest.getInstance().logInfo("FinancialStatementDetails saveHistory 01");
		FinancialStatementTemplateDetailsHistory financialStatementDetailsHistory = new FinancialStatementTemplateDetailsHistory();

		financialStatementDetailsHistory.setTenantId(tenantId);
		financialStatementDetailsHistory.setFinancialStatement(financialStatementDetails.getFinancialStatement());
		financialStatementDetailsHistory.setFinancialStatementDetailsId(financialStatementDetails.getId());
		financialStatementDetailsHistory
				.setFinancialStatementLevel(financialStatementDetails.getFinancialStatementLevels());
		financialStatementDetailsHistory.setFieldName(financialStatementDetails.getFieldName());
		financialStatementDetailsHistory.setParentId(financialStatementDetails.getParentId());
		financialStatementDetailsHistory.setTotalValueRequired(financialStatementDetails.getTotalValueRequired());
		financialStatementDetailsHistory.setFormulaEnabled(financialStatementDetails.getFormulaEnabled());
		financialStatementDetailsHistory.setFormula(financialStatementDetails.getFormula());
		financialStatementDetailsHistory
				.setAdditionalNoteRequired(financialStatementDetails.getAdditionalNoteRequired());
		financialStatementDetailsHistory.setStatus(financialStatementDetails.getStatus());
		financialStatementDetailsHistory.setCreatedDate(financialStatementDetails.getCreatedDate());
		financialStatementDetailsHistory.setCreatedUser(financialStatementDetails.getCreatedUser());
		financialStatementDetailsHistory.setModifiedDate(financialStatementDetails.getModifiedDate());
		financialStatementDetailsHistory.setModifiedUser(financialStatementDetails.getModifiedUser());
		financialStatementDetailsHistory.setVersion(financialStatementDetails.getVersion());
		financialStatementDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
		financialStatementDetailsHistory.setHistoryCreatedDate(currentTimestamp);
		financialStatementDetailsHistory.setSyncTs(currentTimestamp);
		LoggerRequest.getInstance().logInfo("FinancialStatementDetails saveHistory 02");
		financialStatementDetailsHistoryRepository.saveAndFlush(financialStatementDetailsHistory);
		LoggerRequest.getInstance().logInfo("FinancialStatementDetails saveHistory 03");

	}

}
