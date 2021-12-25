package com.fusionx.lending.origination.resource;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Income Expense Summary Response
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   21-09-2021   FXL-115  	 FXL-786       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class IncomeExpenseSummaryResponse {
	
	private BigDecimal businessIncome;
	
	private BigDecimal businessExpenses;
	
	private BigDecimal taxes;
	
	private BigDecimal netBusinessIncome;
	
	private BigDecimal salaryIncome;
	
	private BigDecimal salaryExpenses;
	
	private BigDecimal netSalaryIncome;
	
	private BigDecimal cultivationIncome;
	
	private BigDecimal cultivationExpenses;
	
	private BigDecimal netCultivationIncome;
	
	private BigDecimal otherIncome;
	
	private BigDecimal otherExpenses;
	
	private BigDecimal netOtherIncome;
	
	private BigDecimal totalGrossIncome;
	
	private BigDecimal totalHouseHoldExpenses;
	
	private BigDecimal totalFinancialCommitment;
	
	private BigDecimal totalNetIncome;
	
	private BigDecimal fourtyPercentNetIncome;

	public BigDecimal getBusinessIncome() {
		return businessIncome;
	}

	public void setBusinessIncome(BigDecimal businessIncome) {
		this.businessIncome = businessIncome;
	}

	public BigDecimal getBusinessExpenses() {
		return businessExpenses;
	}

	public void setBusinessExpenses(BigDecimal businessExpenses) {
		this.businessExpenses = businessExpenses;
	}

	public BigDecimal getTaxes() {
		return taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public BigDecimal getNetBusinessIncome() {
		return netBusinessIncome;
	}

	public void setNetBusinessIncome(BigDecimal netBusinessIncome) {
		this.netBusinessIncome = netBusinessIncome;
	}

	public BigDecimal getSalaryIncome() {
		return salaryIncome;
	}

	public void setSalaryIncome(BigDecimal salaryIncome) {
		this.salaryIncome = salaryIncome;
	}

	public BigDecimal getSalaryExpenses() {
		return salaryExpenses;
	}

	public void setSalaryExpenses(BigDecimal salaryExpenses) {
		this.salaryExpenses = salaryExpenses;
	}

	public BigDecimal getNetSalaryIncome() {
		return netSalaryIncome;
	}

	public void setNetSalaryIncome(BigDecimal netSalaryIncome) {
		this.netSalaryIncome = netSalaryIncome;
	}

	public BigDecimal getCultivationIncome() {
		return cultivationIncome;
	}

	public void setCultivationIncome(BigDecimal cultivationIncome) {
		this.cultivationIncome = cultivationIncome;
	}

	public BigDecimal getCultivationExpenses() {
		return cultivationExpenses;
	}

	public void setCultivationExpenses(BigDecimal cultivationExpenses) {
		this.cultivationExpenses = cultivationExpenses;
	}

	public BigDecimal getNetCultivationIncome() {
		return netCultivationIncome;
	}

	public void setNetCultivationIncome(BigDecimal netCultivationIncome) {
		this.netCultivationIncome = netCultivationIncome;
	}

	public BigDecimal getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	public BigDecimal getOtherExpenses() {
		return otherExpenses;
	}

	public void setOtherExpenses(BigDecimal otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	public BigDecimal getNetOtherIncome() {
		return netOtherIncome;
	}

	public void setNetOtherIncome(BigDecimal netOtherIncome) {
		this.netOtherIncome = netOtherIncome;
	}

	public BigDecimal getTotalHouseHoldExpenses() {
		return totalHouseHoldExpenses;
	}

	public void setTotalHouseHoldExpenses(BigDecimal totalHouseHoldExpenses) {
		this.totalHouseHoldExpenses = totalHouseHoldExpenses;
	}

	public BigDecimal getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(BigDecimal totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}

	public BigDecimal getTotalFinancialCommitment() {
		return totalFinancialCommitment;
	}

	public void setTotalFinancialCommitment(BigDecimal totalFinancialCommitment) {
		this.totalFinancialCommitment = totalFinancialCommitment;
	}

	public BigDecimal getTotalNetIncome() {
		return totalNetIncome;
	}

	public void setTotalNetIncome(BigDecimal totalNetIncome) {
		this.totalNetIncome = totalNetIncome;
	}

	public BigDecimal getFourtyPercentNetIncome() {
		return fourtyPercentNetIncome;
	}

	public void setFourtyPercentNetIncome(BigDecimal fourtyPercentNetIncome) {
		this.fourtyPercentNetIncome = fourtyPercentNetIncome;
	}

}
