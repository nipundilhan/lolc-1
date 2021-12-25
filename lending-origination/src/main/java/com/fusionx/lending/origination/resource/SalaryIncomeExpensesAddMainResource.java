package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeExpensesAddMainResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String salaryIncomeDetailId;
	
	private List<SalaryIncomeExpensesAddResource> incomeExpenceList;
	
	public String getSalaryIncomeDetailId() {
		return salaryIncomeDetailId;
	}

	public void setSalaryIncomeDetailId(String salaryIncomeDetailId) {
		this.salaryIncomeDetailId = salaryIncomeDetailId;
	}

	public List<SalaryIncomeExpensesAddResource> getIncomeExpenceList() {
		return incomeExpenceList;
	}

	public void setIncomeExpenceList(List<SalaryIncomeExpensesAddResource> incomeExpenceList) {
		this.incomeExpenceList = incomeExpenceList;
	}

}
