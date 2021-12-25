package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BankStatementDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface BankStatementDetailsRepository extends JpaRepository<BankStatementDetails, Long> {

	public List<BankStatementDetails> findByStatus(CommonStatus status);

	public List<BankStatementDetails> findByCustomerId(Long customerId);

	public Page<BankStatementDetails> findAll(Pageable pageable);

	public Optional<BankStatementDetails> findById(Long id);

}
