package com.fusionx.lending.product.resources;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Basic Tc Calculator
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-10-2021   FXL-78        FXL-983    Dilki        Created
 *    2	  05-10-2021   FXL-78        FXL-985    Dilki        Created
 *    3   07-10-2021   FXL-78        FXL-1008   Dilki        Created
 *    4	  08-10-2021   FXL-78        FXL-984    Dilki        Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BasicTcCalculatorResource {

	private BigDecimal loanAmount;

	private BigDecimal installment;

	private BigDecimal annualInterestRate;

	private BigDecimal term;
	
	private int frequency;
	
	private BigDecimal irr; 

	private BigDecimal noOfPrePayments;

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(BigDecimal annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public BigDecimal getIrr() {
		return irr;
	}

	public void setIrr(BigDecimal irr) {
		this.irr = irr;
	}

	public BigDecimal getNoOfPrePayments() {
		return noOfPrePayments;
	}

	public void setNoOfPrePayments(BigDecimal noOfPrePayments) {
		this.noOfPrePayments = noOfPrePayments;
	} 

}
