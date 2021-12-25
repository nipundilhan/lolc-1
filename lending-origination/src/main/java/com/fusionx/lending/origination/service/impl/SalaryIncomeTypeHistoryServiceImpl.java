package com.fusionx.lending.origination.service.impl;
/**
 * Salary Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-08-2021   FXL-338 		 FXL-532 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.domain.SalaryIncomeTypeHistory;
import com.fusionx.lending.origination.repository.SalaryIncomeTypeHistoryRepository;
import com.fusionx.lending.origination.service.SalaryIncomeTypeHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalaryIncomeTypeHistoryServiceImpl implements SalaryIncomeTypeHistoryService {

	@Autowired
	private SalaryIncomeTypeHistoryRepository salaryIncomeTypeHistoryRepository;

	@Override
	public void saveHistory(String tenantId, SalaryIncomeType salaryIncomeType, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		SalaryIncomeTypeHistory salaryIncomeTypeHistory = new SalaryIncomeTypeHistory();

		salaryIncomeTypeHistory.setTenantId(tenantId);
		salaryIncomeTypeHistory.setSalaryIncomeTypeId(salaryIncomeType.getId());
		salaryIncomeTypeHistory.setCode(salaryIncomeType.getCode());
		salaryIncomeTypeHistory.setName(salaryIncomeType.getName());
		salaryIncomeTypeHistory.setDescription(salaryIncomeType.getDescription());
		salaryIncomeTypeHistory.setStatus(salaryIncomeType.getStatus());
		salaryIncomeTypeHistory.setCreatedDate(salaryIncomeType.getCreatedDate());
		salaryIncomeTypeHistory.setCreatedUser(salaryIncomeType.getCreatedUser());
		salaryIncomeTypeHistory.setModifiedDate(salaryIncomeType.getModifiedDate());
		salaryIncomeTypeHistory.setModifiedUser(salaryIncomeType.getModifiedUser());
		salaryIncomeTypeHistory.setVersion(salaryIncomeType.getVersion());
		salaryIncomeTypeHistory.setHistoryCreatedUser(historyCreatedUser);
		salaryIncomeTypeHistory.setHistoryCreatedDate(currentTimestamp);
		salaryIncomeTypeHistory.setSyncTs(currentTimestamp);

		salaryIncomeTypeHistoryRepository.saveAndFlush(salaryIncomeTypeHistory);
	}

}
