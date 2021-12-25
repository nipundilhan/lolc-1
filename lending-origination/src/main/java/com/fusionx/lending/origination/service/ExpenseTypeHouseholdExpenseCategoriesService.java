package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExpenseTypeHouseholdExpenseCategories;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesAddResource;
import com.fusionx.lending.origination.resource.ExpenseTypeHouseholdExpenseCategoriesUpdateResource;

@Service
public interface ExpenseTypeHouseholdExpenseCategoriesService {
	
	
	public List<ExpenseTypeHouseholdExpenseCategories> getAll();
	
	
	public Optional<ExpenseTypeHouseholdExpenseCategories> getById(Long id);
	
	
	
	public List<ExpenseTypeHouseholdExpenseCategories> getByStatus(String status);
	
	
	
	//public ExpenseTypeHouseholdExpenseCategories addExpenseTypeHouseholdExpenseCategories(String tenantId , ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource);
	public void addExpenseTypeHouseholdExpenseCategories(String tenantId , ExpenseTypeHouseholdExpenseCategoriesAddResource expenseTypeHouseholdExpenseCategoriesAddResource);

	
	//public ExpenseTypeHouseholdExpenseCategories updateExpenseTypeHouseholdExpenseCategories(String tenantId, Long id, ExpenseTypeHouseholdExpenseCategoriesUpdateResource expenseTypeHouseholdExpenseCategoriesUpdateResource);
	public void updateExpenseTypeHouseholdExpenseCategories(String tenantId,ExpenseTypeHouseholdExpenseCategoriesUpdateResource expenseTypeHouseholdExpenseCategoriesUpdateResource);

}
