package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.SalaryIncomeExpenses;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeExpenseDetailResponse {
	
	List<SalaryIncomeExpenses> incomeList;
	
	List<SalaryIncomeExpenses> expenseList;

	public List<SalaryIncomeExpenses> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<SalaryIncomeExpenses> incomeList) {
		this.incomeList = incomeList;
	}

	public List<SalaryIncomeExpenses> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<SalaryIncomeExpenses> expenseList) {
		this.expenseList = expenseList;
	}
	
	
	
}
