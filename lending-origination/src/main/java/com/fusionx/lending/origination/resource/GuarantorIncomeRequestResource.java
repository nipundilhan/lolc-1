package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorIncomeRequestResource {
	
	private String guarantorId;
	
	@Valid
	List<SalaryIncomeAddRequestResource> salaryIncomes;
	
	@Valid
	List<OtherIncomeAddRequestResource> otherIncomes;
	
	@Valid
	List<BusinessIncomeExpenseRequestResource> businessIncome;
	
	@Valid
	List<GuarantorCultivationIncomeAddResource> cultivationIncome;
	
	
	
	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}

	

	public List<SalaryIncomeAddRequestResource> getSalaryIncomes() {
		return salaryIncomes;
	}

	public void setSalaryIncomes(List<SalaryIncomeAddRequestResource> salaryIncomes) {
		this.salaryIncomes = salaryIncomes;
	}

	public List<OtherIncomeAddRequestResource> getOtherIncomes() {
		return otherIncomes;
	}

	public void setOtherIncomes(List<OtherIncomeAddRequestResource> otherIncomes) {
		this.otherIncomes = otherIncomes;
	}

	public List<BusinessIncomeExpenseRequestResource> getBusinessIncome() {
		return businessIncome;
	}

	public void setBusinessIncome(List<BusinessIncomeExpenseRequestResource> businessIncome) {
		this.businessIncome = businessIncome;
	}

	public List<GuarantorCultivationIncomeAddResource> getCultivationIncome() {
		return cultivationIncome;
	}

	public void setCultivationIncome(List<GuarantorCultivationIncomeAddResource> cultivationIncome) {
		this.cultivationIncome = cultivationIncome;
	}
	

	
	
	

}
