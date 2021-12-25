package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface FinancialStatementTemplateRepository extends JpaRepository<FinancialStatementTemplate, Long> {

	public List<FinancialStatementTemplate> findByStatus(CommonStatus status);

	public Optional<FinancialStatementTemplate> findByNameContaining(String name);

	public Optional<FinancialStatementTemplate> findByCode(String code);

}
