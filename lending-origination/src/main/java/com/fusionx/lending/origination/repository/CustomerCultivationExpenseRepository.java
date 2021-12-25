package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CustomerCultivationExpense;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface CustomerCultivationExpenseRepository extends JpaRepository<CustomerCultivationExpense, Long> {

	public List<CustomerCultivationExpense> findByCustomerCultivationIncomeId(Long customerCultivationIncomeId);
	
	public List<CustomerCultivationExpense> findByCustomerCultivationIncomeIdAndStatus(Long customerCultivationIncomeId, CommonStatus status);

	public Optional<CustomerCultivationExpense> findByCustomerCultivationIncomeIdAndExpenseTypeIdAndStatusAndIdNotIn(
			Long customerCultivationIncomeId, Long expenseTypeId, CommonStatus status, Long id);

	public Optional<CustomerCultivationExpense> findByCustomerCultivationIncomeIdAndExpenseTypeIdAndStatus(Long customerCultivationIncomeId,
			Long expenseTypeId, CommonStatus status);
}
