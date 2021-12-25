package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetailsHistory;

public interface FinancialStatementTemplateDetailsHistoryRepository
		extends JpaRepository<FinancialStatementTemplateDetailsHistory, Long> {

}
