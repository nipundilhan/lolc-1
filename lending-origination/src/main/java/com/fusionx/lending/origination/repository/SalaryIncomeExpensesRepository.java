package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;
import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface SalaryIncomeExpensesRepository  extends JpaRepository<SalaryIncomeExpenses, Long>{
	
	List<SalaryIncomeExpenses> findAllBySalaryIncomeDetailsAndSalaryIncomeType(SalaryIncomeDetails salaryIncomeDetails,SalaryIncomeType salaryIncomeType);

	List<SalaryIncomeExpenses> findAllBySalaryIncomeDetailsAndExpenseType(SalaryIncomeDetails salaryIncomeDetails,ExpenseType expenseType);

	List<SalaryIncomeExpenses> findBySalaryIncomeDetailsIdAndStatus(Long salaryIncomeDetailsId, CommonStatus status);

}
