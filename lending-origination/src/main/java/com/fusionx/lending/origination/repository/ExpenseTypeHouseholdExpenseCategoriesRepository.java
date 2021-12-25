package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategories;
import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.enums.CommonStatus;


@Repository
public interface ExpenseTypeHouseholdExpenseCategoriesRepository extends JpaRepository<ExpenseTypeHouseholdExpenseCategories, Long> {

	List<ExpenseTypeHouseholdExpenseCategories> findByStatus(CommonStatus status);

	Optional<ExpenseTypeHouseholdExpenseCategories> findByHouseHoldExpenseCategoryId(Long id);
	
	Optional<ExpenseTypeHouseholdExpenseCategories> findByExpenseTypeAndHouseHoldExpenseCategory(ExpenseType expenseType, HouseHoldExpenseCategory houseHoldExpenseCategory);
	
	Optional<ExpenseTypeHouseholdExpenseCategories> findByExpenseTypeAndHouseHoldExpenseCategoryAndIdNot(ExpenseType expenseType, HouseHoldExpenseCategory houseHoldExpenseCategory, Long id);
}
