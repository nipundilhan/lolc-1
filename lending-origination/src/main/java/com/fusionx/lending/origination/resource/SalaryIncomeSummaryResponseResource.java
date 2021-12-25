package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.SalaryIncome;

/**
 * Salary Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeSummaryResponseResource {
	
	private String totalIncome;
	
	private String deductions;
	
	private String netSalaryIncome;
	
	private List<SalaryIncome> salaryIncomeDetail;

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

	public List<SalaryIncome> getSalaryIncomeDetail() {
		return salaryIncomeDetail;
	}

	public void setSalaryIncomeDetail(List<SalaryIncome> salaryIncomeDetail) {
		this.salaryIncomeDetail = salaryIncomeDetail;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}

	public String getNetSalaryIncome() {
		return netSalaryIncome;
	}

	public void setNetSalaryIncome(String netSalaryIncome) {
		this.netSalaryIncome = netSalaryIncome;
	}
	
	

}
