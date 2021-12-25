package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.repository.BankStatementDetailsHistoryRepository;
import com.fusionx.lending.origination.service.BankStatementDetailsHistoryService;
import com.fusionx.lending.origination.domain.BankStatementDetailsHistory;

@Component
@Transactional(rollbackFor = Exception.class)
public class BankStatementDetailsHistoryServiceImpl implements BankStatementDetailsHistoryService {

	@Autowired
	private BankStatementDetailsHistoryRepository bankStatementDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, BankStatementDetails bankStatementDetails, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		BankStatementDetailsHistory bankStatementDetailsHistory = new BankStatementDetailsHistory();

		bankStatementDetailsHistory.setTenantId(tenantId);
		bankStatementDetailsHistory.setBankStatementDetailsId(bankStatementDetails.getId());
		bankStatementDetailsHistory.setBankId(bankStatementDetails.getBankId());
		bankStatementDetailsHistory.setBankName(bankStatementDetails.getBankName());
		bankStatementDetailsHistory.setAccountTypeId(bankStatementDetails.getAccountTypeId());
		bankStatementDetailsHistory.setAccountTypeName(bankStatementDetails.getAccountTypeName());
		bankStatementDetailsHistory.setAccountNumber(bankStatementDetails.getAccountNumber());
		bankStatementDetailsHistory.setPeriodFrom(bankStatementDetails.getPeriodFrom());
		bankStatementDetailsHistory.setPeriodTo(bankStatementDetails.getPeriodTo());
		bankStatementDetailsHistory.setCurrencyId(bankStatementDetails.getCurrencyId());
		bankStatementDetailsHistory.setCurrencyName(bankStatementDetails.getCurrencyName());
		bankStatementDetailsHistory.setOpeningBalance(bankStatementDetails.getOpeningBalance());
		bankStatementDetailsHistory.setCloseBalance(bankStatementDetails.getCloseBalance());
		bankStatementDetailsHistory.setMoneyIn(bankStatementDetails.getMoneyIn());
		bankStatementDetailsHistory.setMoneyOut(bankStatementDetails.getMoneyOut());
		bankStatementDetailsHistory.setStatus(bankStatementDetails.getStatus());
		bankStatementDetailsHistory.setCreatedDate(bankStatementDetails.getCreatedDate());
		bankStatementDetailsHistory.setCreatedUser(bankStatementDetails.getCreatedUser());
		bankStatementDetailsHistory.setModifiedDate(bankStatementDetails.getModifiedDate());
		bankStatementDetailsHistory.setModifiedUser(bankStatementDetails.getModifiedUser());
		bankStatementDetailsHistory.setVersion(bankStatementDetails.getVersion());
		bankStatementDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
		bankStatementDetailsHistory.setHistoryCreatedDate(currentTimestamp);
		bankStatementDetailsHistory.setSyncTs(currentTimestamp);

		bankStatementDetailsHistoryRepository.saveAndFlush(bankStatementDetailsHistory);
	}

}
