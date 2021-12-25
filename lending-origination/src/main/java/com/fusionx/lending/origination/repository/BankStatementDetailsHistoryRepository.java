package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BankStatementDetailsHistory;

@Repository
public interface BankStatementDetailsHistoryRepository extends JpaRepository<BankStatementDetailsHistory, Long> {

}
