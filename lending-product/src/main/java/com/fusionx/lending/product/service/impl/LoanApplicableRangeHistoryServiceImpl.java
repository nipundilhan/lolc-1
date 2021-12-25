package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.domain.LoanApplicableRangeHistory;
import com.fusionx.lending.product.repository.LoanApplicableRangeHistoryRepository;
import com.fusionx.lending.product.service.LoanApplicableRangeHistoryService;

/**
 * Loan Applicable Range History Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021             				 Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class LoanApplicableRangeHistoryServiceImpl implements LoanApplicableRangeHistoryService{
	
	@Autowired
	private LoanApplicableRangeHistoryRepository loanApplicableRangeHistoryyRepository;

	@Override
	public void saveHistory(String tenantId, LoanApplicableRange loanApplicableRange, String historyCreatedUser) {
		
		Calendar calendar = Calendar.getInstance();
	    java.util.Date now = calendar.getTime();
	    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
	    
	    LoanApplicableRangeHistory loanApplicableRangeHistory = new LoanApplicableRangeHistory();
	    loanApplicableRangeHistory.setTenantId(tenantId);
	    loanApplicableRangeHistory.setLoanApplicableRangeId(loanApplicableRange.getId());
	    loanApplicableRangeHistory.setCode(loanApplicableRange.getCode());
	    loanApplicableRangeHistory.setName(loanApplicableRange.getName());
	    loanApplicableRangeHistory.setStatus(loanApplicableRange.getStatus());
	    loanApplicableRangeHistory.setCreatedDate(loanApplicableRange.getCreatedDate());
	    loanApplicableRangeHistory.setCreatedUser(loanApplicableRange.getCreatedUser());
	    loanApplicableRangeHistory.setModifiedDate(loanApplicableRange.getModifiedDate());
	    loanApplicableRangeHistory.setModifiedUser(loanApplicableRange.getModifiedUser());
	    loanApplicableRangeHistory.setVersion(loanApplicableRange.getVersion());
	    loanApplicableRangeHistory.setMaximumAmount(loanApplicableRange.getMaximumAmount());
	    loanApplicableRangeHistory.setMinimumAmount(loanApplicableRange.getMinimumAmount());
	    loanApplicableRangeHistory.setDefaultAmount(loanApplicableRange.getdefaultAmount());
	    loanApplicableRangeHistory.setMaximumRate(loanApplicableRange.getMaximumRate());
		loanApplicableRangeHistory.setMinimumRate(loanApplicableRange.getMaximumRate());
		loanApplicableRangeHistory.setDefaultRate(loanApplicableRange.getDefaultRate());
		loanApplicableRangeHistory.setHistoryCreatedUser(historyCreatedUser);
		loanApplicableRangeHistory.setHistoryCreatedDate(currentTimestamp);
		loanApplicableRangeHistory.setSyncTs(currentTimestamp);
		
		loanApplicableRangeHistoryyRepository.saveAndFlush(loanApplicableRangeHistory);
	}

}

