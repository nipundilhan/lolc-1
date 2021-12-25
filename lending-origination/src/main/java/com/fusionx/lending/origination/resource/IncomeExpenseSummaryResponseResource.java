package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
public class IncomeExpenseSummaryResponseResource {
	
	private String totalGrossIncome;
	
	private String totalDeductions;
	
	private String totalNetIncome;
	
	private String percentage;
	
	private String calculatedPercentageAmount;
	
	private SalaryIncomeSummaryResponseResource salaryIncomeExpenseSummary;
	
	private OtherIncomeSummaryResponseResource otherIncomeExpenseSummary;
	
	private GuarantorCultivationIncomeSummaryResponseResource guarantorCultivationIncomeSummary;
	
	private FinancialCommitmentSummaryResponseResource financialCommitmentSummary;
	
	private GuarantorBusinessIncomeSummaryResponseResource guarantorBusinessIncomeSummary;
	
	private CustomerBusinessIncomeSummaryResponseResource customerBusinessIncomeSummary;
	
	private HouseHoldExpenseSummaryResponseResource houseHoldExpenseSummary;
	
	private CustomerCultivationIncomeSummaryResponseResource customerCultivationIncomeSummary;

	
	
	public String getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(String totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}
	

	public String getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(String totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public String getTotalNetIncome() {
		return totalNetIncome;
	}

	public void setTotalNetIncome(String totalNetIncome) {
		this.totalNetIncome = totalNetIncome;
	}
	
	

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getCalculatedPercentageAmount() {
		return calculatedPercentageAmount;
	}

	public void setCalculatedPercentageAmount(String calculatedPercentageAmount) {
		this.calculatedPercentageAmount = calculatedPercentageAmount;
	}

	public SalaryIncomeSummaryResponseResource getSalaryIncomeExpenseSummary() {
		return salaryIncomeExpenseSummary;
	}

	public void setSalaryIncomeExpenseSummary(SalaryIncomeSummaryResponseResource salaryIncomeExpenseSummary) {
		this.salaryIncomeExpenseSummary = salaryIncomeExpenseSummary;
	}

	public OtherIncomeSummaryResponseResource getOtherIncomeExpenseSummary() {
		return otherIncomeExpenseSummary;
	}

	public void setOtherIncomeExpenseSummary(OtherIncomeSummaryResponseResource otherIncomeExpenseSummary) {
		this.otherIncomeExpenseSummary = otherIncomeExpenseSummary;
	}
	

	

	public GuarantorCultivationIncomeSummaryResponseResource getGuarantorCultivationIncomeSummary() {
		return guarantorCultivationIncomeSummary;
	}

	public void setGuarantorCultivationIncomeSummary(
			GuarantorCultivationIncomeSummaryResponseResource guarantorCultivationIncomeSummary) {
		this.guarantorCultivationIncomeSummary = guarantorCultivationIncomeSummary;
	}

	public FinancialCommitmentSummaryResponseResource getFinancialCommitmentSummary() {
		return financialCommitmentSummary;
	}

	public void setFinancialCommitmentSummary(FinancialCommitmentSummaryResponseResource financialCommitmentSummary) {
		this.financialCommitmentSummary = financialCommitmentSummary;
	}

	public GuarantorBusinessIncomeSummaryResponseResource getGuarantorBusinessIncomeSummary() {
		return guarantorBusinessIncomeSummary;
	}

	public void setGuarantorBusinessIncomeSummary(
			GuarantorBusinessIncomeSummaryResponseResource guarantorBusinessIncomeSummary) {
		this.guarantorBusinessIncomeSummary = guarantorBusinessIncomeSummary;
	}

	public CustomerBusinessIncomeSummaryResponseResource getCustomerBusinessIncomeSummary() {
		return customerBusinessIncomeSummary;
	}

	public void setCustomerBusinessIncomeSummary(
			CustomerBusinessIncomeSummaryResponseResource customerBusinessIncomeSummary) {
		this.customerBusinessIncomeSummary = customerBusinessIncomeSummary;
	}

	public HouseHoldExpenseSummaryResponseResource getHouseHoldExpenseSummary() {
		return houseHoldExpenseSummary;
	}

	public void setHouseHoldExpenseSummary(HouseHoldExpenseSummaryResponseResource houseHoldExpenseSummary) {
		this.houseHoldExpenseSummary = houseHoldExpenseSummary;
	}

	public CustomerCultivationIncomeSummaryResponseResource getCustomerCultivationIncomeSummary() {
		return customerCultivationIncomeSummary;
	}

	public void setCustomerCultivationIncomeSummary(
			CustomerCultivationIncomeSummaryResponseResource customerCultivationIncomeSummary) {
		this.customerCultivationIncomeSummary = customerCultivationIncomeSummary;
	}

	
	
	

	
}
