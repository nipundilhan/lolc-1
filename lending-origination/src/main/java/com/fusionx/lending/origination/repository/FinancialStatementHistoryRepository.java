package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.FinancialStatementTemplateHistory;

public interface FinancialStatementHistoryRepository extends JpaRepository<FinancialStatementTemplateHistory, Long> {

}
