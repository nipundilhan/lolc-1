package com.fusionx.lending.product.resources;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Structured Loan Calculation Response Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-10-2021   			 	 FXL-993      Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StructuredLoanCalculationResponseResource {
			
			
	private int totalPeriod;
	private int fixedInstallmentPeriod;
	private BigDecimal netInstallment;
	private BigDecimal chargesPerInstallment;
	private BigDecimal taxesPerInstallment;
	private BigDecimal totalGrossInstallment;
	private BigDecimal totalDownpayment;
	private BigDecimal prepaidInstallments;
	private BigDecimal totalTaxes;
	private BigDecimal totalCharges;
	private BigDecimal loanCalculationValue;
	private BigDecimal totalInterest;
	private BigDecimal totalReceivableWithoutChargesAndTaxes;
	private BigDecimal totalReceivableWithChargesAndTaxes;
	private BigDecimal netFixedInstallmentWithoutFactoredValues;
	private BigDecimal factoredValuesInInstallment;	
	private BigDecimal irr;
	private List<TcStructurePaymentScheduleResponse> repaymentSchedule;

	public BigDecimal getTotalGrossInstallment() {
		return totalGrossInstallment;
	}
	public void setTotalGrossInstallment(BigDecimal totalGrossInstallment) {
		this.totalGrossInstallment = totalGrossInstallment;
	}
	public BigDecimal getTotalDownpayment() {
		return totalDownpayment;
	}
	public void setTotalDownpayment(BigDecimal totalDownpayment) {
		this.totalDownpayment = totalDownpayment;
	}
	public BigDecimal getPrepaidInstallments() {
		return prepaidInstallments;
	}
	public void setPrepaidInstallments(BigDecimal prepaidInstallments) {
		this.prepaidInstallments = prepaidInstallments;
	}
	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}
	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}
	public BigDecimal getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}
	public BigDecimal getLoanCalculationValue() {
		return loanCalculationValue;
	}
	public void setLoanCalculationValue(BigDecimal loanCalculationValue) {
		this.loanCalculationValue = loanCalculationValue;
	}
	public BigDecimal getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}
	public BigDecimal getTotalReceivableWithoutChargesAndTaxes() {
		return totalReceivableWithoutChargesAndTaxes;
	}
	public void setTotalReceivableWithoutChargesAndTaxes(BigDecimal totalReceivableWithoutChargesAndTaxes) {
		this.totalReceivableWithoutChargesAndTaxes = totalReceivableWithoutChargesAndTaxes;
	}
	public BigDecimal getTotalReceivableWithChargesAndTaxes() {
		return totalReceivableWithChargesAndTaxes;
	}
	public void setTotalReceivableWithChargesAndTaxes(BigDecimal totalReceivableWithChargesAndTaxes) {
		this.totalReceivableWithChargesAndTaxes = totalReceivableWithChargesAndTaxes;
	}
	public BigDecimal getNetFixedInstallmentWithoutFactoredValues() {
		return netFixedInstallmentWithoutFactoredValues;
	}
	public void setNetFixedInstallmentWithoutFactoredValues(BigDecimal netFixedInstallmentWithoutFactoredValues) {
		this.netFixedInstallmentWithoutFactoredValues = netFixedInstallmentWithoutFactoredValues;
	}
	public BigDecimal getFactoredValuesInInstallment() {
		return factoredValuesInInstallment;
	}
	public void setFactoredValuesInInstallment(BigDecimal factoredValuesInInstallment) {
		this.factoredValuesInInstallment = factoredValuesInInstallment;
	}
	public BigDecimal getIrr() {
		return irr;
	}
	public void setIrr(BigDecimal irr) {
		this.irr = irr;
	}
	public int getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(int totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	public int getFixedInstallmentPeriod() {
		return fixedInstallmentPeriod;
	}
	public void setFixedInstallmentPeriod(int fixedInstallmentPeriod) {
		this.fixedInstallmentPeriod = fixedInstallmentPeriod;
	}
	public BigDecimal getChargesPerInstallment() {
		return chargesPerInstallment;
	}
	public void setChargesPerInstallment(BigDecimal chargesPerInstallment) {
		this.chargesPerInstallment = chargesPerInstallment;
	}
	public BigDecimal getNetInstallment() {
		return netInstallment;
	}
	public void setNetInstallment(BigDecimal netInstallment) {
		this.netInstallment = netInstallment;
	}
	public BigDecimal getTaxesPerInstallment() {
		return taxesPerInstallment;
	}
	public void setTaxesPerInstallment(BigDecimal taxesPerInstallment) {
		this.taxesPerInstallment = taxesPerInstallment;
	}
	public List<TcStructurePaymentScheduleResponse> getRepaymentSchedule() {
		return repaymentSchedule;
	}
	public void setRepaymentSchedule(List<TcStructurePaymentScheduleResponse> repaymentSchedule) {
		this.repaymentSchedule = repaymentSchedule;
	}

}
