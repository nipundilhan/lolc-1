package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.IncomeExpenseTax;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface IncomeExpenseTaxRepository extends JpaRepository<IncomeExpenseTax, Long> {

	public List<IncomeExpenseTax> findByBusinessIncomeExpenseId(Long businessIncomeExpenseId);
	
	public List<IncomeExpenseTax> findByBusinessIncomeExpenseIdAndStatus(Long businessIncomeExpenseId, CommonStatus status);

	public Optional<IncomeExpenseTax> findByBusinessIncomeExpenseIdAndTaxTypeCodeAndStatusAndIdNotIn(Long businessIncomeExpenseId,
			String taxTypeCode, CommonStatus status, Long id);

	public Optional<IncomeExpenseTax> findByBusinessIncomeExpenseIdAndTaxTypeCodeAndStatus(Long businessIncomeExpenseId, String taxTypeCode,
			CommonStatus status);
}
