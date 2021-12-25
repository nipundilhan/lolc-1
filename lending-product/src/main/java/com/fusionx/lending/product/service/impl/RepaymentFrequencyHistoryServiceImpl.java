package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.domain.RepaymentFrequencyHistory;
import com.fusionx.lending.product.repository.RepaymentFrequencyHistoryRepository;
import com.fusionx.lending.product.service.RepaymentFrequencyHistoryService;

/**
 * Repayment Frequency History Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021             				Dilki        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class RepaymentFrequencyHistoryServiceImpl implements RepaymentFrequencyHistoryService {

	@Autowired
	private RepaymentFrequencyHistoryRepository repaymentFrequencyHistoryRepository;

	@Override
	public void saveHistory(String tenantId, RepaymentFrequency repaymentFrequency, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		RepaymentFrequencyHistory repaymentFrequencyHistory = new RepaymentFrequencyHistory();

		repaymentFrequencyHistory.setTenantId(tenantId);
		repaymentFrequencyHistory.setRepaymentFrequencyId(repaymentFrequency.getId());
		repaymentFrequencyHistory.setCode(repaymentFrequency.getCode());
		repaymentFrequencyHistory.setName(repaymentFrequency.getName());
		repaymentFrequencyHistory.setDescription(repaymentFrequency.getDescription());
		repaymentFrequencyHistory.setStatus(repaymentFrequency.getStatus());
		repaymentFrequencyHistory.setCreatedDate(repaymentFrequency.getCreatedDate());
		repaymentFrequencyHistory.setCreatedUser(repaymentFrequency.getCreatedUser());
		repaymentFrequencyHistory.setModifiedDate(repaymentFrequency.getModifiedDate());
		repaymentFrequencyHistory.setModifiedUser(repaymentFrequency.getModifiedUser());
		repaymentFrequencyHistory.setVersion(repaymentFrequency.getVersion());
		repaymentFrequencyHistory.setHistoryCreatedUser(historyCreatedUser);
		repaymentFrequencyHistory.setHistoryCreatedDate(currentTimestamp);
		repaymentFrequencyHistory.setSyncTs(currentTimestamp);

		repaymentFrequencyHistoryRepository.saveAndFlush(repaymentFrequencyHistory);
	}

}
