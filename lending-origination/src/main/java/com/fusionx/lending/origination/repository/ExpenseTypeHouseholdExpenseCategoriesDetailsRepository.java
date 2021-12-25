package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategoriesDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ExpenseTypeHouseholdExpenseCategoriesDetailsRepository extends JpaRepository<ExpenseTypeHouseholdExpenseCategoriesDetails, Long> {

	List<ExpenseTypeHouseholdExpenseCategoriesDetails> findByExpenseTypeHouseholdExpenseCategoriesId(Long id);
	
	public Optional<ExpenseTypeHouseholdExpenseCategoriesDetails> findByIdAndStatus(Long id,  CommonStatus status);
}
