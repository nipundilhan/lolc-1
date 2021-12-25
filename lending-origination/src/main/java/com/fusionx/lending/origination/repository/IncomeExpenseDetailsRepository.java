package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.IncomeExpenseDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface IncomeExpenseDetailsRepository extends JpaRepository<IncomeExpenseDetails, Long> {

	public List<IncomeExpenseDetails> findByBusinessIncomeExpenseId(Long businessIncomeExpenseId);
	
	public List<IncomeExpenseDetails> findByBusinessIncomeExpenseIdAndStatus(Long businessIncomeExpenseId, CommonStatus status);

	public Optional<IncomeExpenseDetails> findByBusinessIncomeExpenseIdAndIncomeTypeIdAndStatus(Long businessIncomeExpenseId, Long incomeTypeId,
			CommonStatus status);

	public Optional<IncomeExpenseDetails> findByBusinessIncomeExpenseIdAndExpenseTypeIdAndStatus(Long businessIncomeExpenseId, Long expenseTypeId,
			CommonStatus status);

	public Optional<IncomeExpenseDetails> findByBusinessIncomeExpenseIdAndIncomeTypeIdAndStatusAndIdNotIn(Long businessIncomeExpenseId,
			Long expenseTypeId, CommonStatus status, Long id);

	public Optional<IncomeExpenseDetails> findByBusinessIncomeExpenseIdAndExpenseTypeIdAndStatusAndIdNotIn(Long businessIncomeExpenseId,
			Long expenseTypeId, CommonStatus status, Long id);
}
