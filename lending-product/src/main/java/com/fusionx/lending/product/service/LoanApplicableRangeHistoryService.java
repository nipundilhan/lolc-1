package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.LoanApplicableRange;

@Service
public interface LoanApplicableRangeHistoryService {
	
	public void saveHistory(String tenantId, LoanApplicableRange loanApplicableRange, String historyCreatedUser);

}
