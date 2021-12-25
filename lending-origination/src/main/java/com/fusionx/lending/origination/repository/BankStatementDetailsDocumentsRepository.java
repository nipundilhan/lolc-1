package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BankStatementDocuments;

@Repository
public interface BankStatementDetailsDocumentsRepository extends JpaRepository<BankStatementDocuments, Long> {

//	public List<BankStatementDocuments> findByStatus(String status);
//
//	public Optional<BankStatementDocuments> findByIdAndStatus(Long id, String status);

//	public List<BankStatementDocuments> findByBankStatementId(Long id);

}