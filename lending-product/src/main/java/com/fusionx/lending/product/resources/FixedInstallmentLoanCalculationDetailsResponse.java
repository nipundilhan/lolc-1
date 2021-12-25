package com.fusionx.lending.product.resources;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.enums.ApplicationFrequencyEnum;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FixedInstallmentLoanCalculationDetailsResponse {
	
	
	BigDecimal fixedInstallment;
	
	BigDecimal chargesCollectThroughOutPeriod;
	
	BigDecimal taxesCollectThroughOutPeriod ;
	
	BigDecimal charge;
	
	BigDecimal chargesToBeCollectAtDownPayment ;
	
	BigDecimal totalGrossInstallment ;
	
	BigDecimal totalDownPayment ;
	
	BigDecimal prePaidInstallment ;
	
	BigDecimal totalTaxes;

	BigDecimal totalCharges;	

	BigDecimal loanCalculationValue;
	
	BigDecimal totalInterest ;
	
	BigDecimal totalReceivableWithCapitalAndInterest ;
	
	BigDecimal totalReceivableWithChargesAndTaxes ;
	
	BigDecimal netFixedInstallmentWithOutFactoredValues ;
	
	BigDecimal factoredValueInstallmet;
	
	BigDecimal loanFactor ;
	
	BigDecimal chargeFactor ;
	
	BigDecimal totalFactor ;
	
	BigDecimal irr ;

	@JsonIgnore
	List<TaxDetailsResponse> taxDetailsList;
	
	
	
	public List<TaxDetailsResponse> getTaxDetailsList() {
		return taxDetailsList;
	}

	public void setTaxDetailsList(List<TaxDetailsResponse> taxDetailsList) {
		this.taxDetailsList = taxDetailsList;
	}

	public BigDecimal getFixedInstallment() {
		return fixedInstallment;
	}

	public void setFixedInstallment(BigDecimal fixedInstallment) {
		this.fixedInstallment = fixedInstallment;
	}

	public BigDecimal getChargesCollectThroughOutPeriod() {
		return chargesCollectThroughOutPeriod;
	}

	public void setChargesCollectThroughOutPeriod(BigDecimal chargesCollectThroughOutPeriod) {
		this.chargesCollectThroughOutPeriod = chargesCollectThroughOutPeriod;
	}

	public BigDecimal getTaxesCollectThroughOutPeriod() {
		return taxesCollectThroughOutPeriod;
	}

	public void setTaxesCollectThroughOutPeriod(BigDecimal taxesCollectThroughOutPeriod) {
		this.taxesCollectThroughOutPeriod = taxesCollectThroughOutPeriod;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public BigDecimal getChargesToBeCollectAtDownPayment() {
		return chargesToBeCollectAtDownPayment;
	}

	public void setChargesToBeCollectAtDownPayment(BigDecimal chargesToBeCollectAtDownPayment) {
		this.chargesToBeCollectAtDownPayment = chargesToBeCollectAtDownPayment;
	}

	public BigDecimal getTotalGrossInstallment() {
		return totalGrossInstallment;
	}

	public void setTotalGrossInstallment(BigDecimal totalGrossInstallment) {
		this.totalGrossInstallment = totalGrossInstallment;
	}

	public BigDecimal getTotalDownPayment() {
		return totalDownPayment;
	}

	public void setTotalDownPayment(BigDecimal totalDownPayment) {
		this.totalDownPayment = totalDownPayment;
	}

	public BigDecimal getPrePaidInstallment() {
		return prePaidInstallment;
	}

	public void setPrePaidInstallment(BigDecimal prePaidInstallment) {
		this.prePaidInstallment = prePaidInstallment;
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

	public BigDecimal getTotalReceivableWithCapitalAndInterest() {
		return totalReceivableWithCapitalAndInterest;
	}

	public void setTotalReceivableWithCapitalAndInterest(BigDecimal totalReceivableWithCapitalAndInterest) {
		this.totalReceivableWithCapitalAndInterest = totalReceivableWithCapitalAndInterest;
	}

	public BigDecimal getTotalReceivableWithChargesAndTaxes() {
		return totalReceivableWithChargesAndTaxes;
	}

	public void setTotalReceivableWithChargesAndTaxes(BigDecimal totalReceivableWithChargesAndTaxes) {
		this.totalReceivableWithChargesAndTaxes = totalReceivableWithChargesAndTaxes;
	}

	public BigDecimal getNetFixedInstallmentWithOutFactoredValues() {
		return netFixedInstallmentWithOutFactoredValues;
	}

	public void setNetFixedInstallmentWithOutFactoredValues(BigDecimal netFixedInstallmentWithOutFactoredValues) {
		this.netFixedInstallmentWithOutFactoredValues = netFixedInstallmentWithOutFactoredValues;
	}

	public BigDecimal getFactoredValueInstallmet() {
		return factoredValueInstallmet;
	}

	public void setFactoredValueInstallmet(BigDecimal factoredValueInstallmet) {
		this.factoredValueInstallmet = factoredValueInstallmet;
	}

	public BigDecimal getLoanFactor() {
		return loanFactor;
	}

	public void setLoanFactor(BigDecimal loanFactor) {
		this.loanFactor = loanFactor;
	}

	public BigDecimal getChargeFactor() {
		return chargeFactor;
	}

	public void setChargeFactor(BigDecimal chargeFactor) {
		this.chargeFactor = chargeFactor;
	}

	public BigDecimal getTotalFactor() {
		return totalFactor;
	}

	public void setTotalFactor(BigDecimal totalFactor) {
		this.totalFactor = totalFactor;
	}

	public BigDecimal getIrr() {
		return irr;
	}

	public void setIrr(BigDecimal irr) {
		this.irr = irr;
	}
	
	








	



}
