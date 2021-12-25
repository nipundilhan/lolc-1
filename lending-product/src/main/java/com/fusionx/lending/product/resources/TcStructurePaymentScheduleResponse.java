package com.fusionx.lending.product.resources;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Tc Structure Payment Schedule Response
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-10-2021   			 	 FXL-1139      Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TcStructurePaymentScheduleResponse {
	
	private int period;
	private BigDecimal installment;
	private BigDecimal interestPortion;
	private BigDecimal capitalPortion;
	private BigDecimal capitalBalance;
	private BigDecimal totalReceivable;
	private BigDecimal charges;
	private BigDecimal taxes;
	private BigDecimal grossInstallment;
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public BigDecimal getInstallment() {
		return installment;
	}
	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}
	public BigDecimal getInterestPortion() {
		return interestPortion;
	}
	public void setInterestPortion(BigDecimal interestPortion) {
		this.interestPortion = interestPortion;
	}
	public BigDecimal getCapitalPortion() {
		return capitalPortion;
	}
	public void setCapitalPortion(BigDecimal capitalPortion) {
		this.capitalPortion = capitalPortion;
	}
	public BigDecimal getCapitalBalance() {
		return capitalBalance;
	}
	public void setCapitalBalance(BigDecimal capitalBalance) {
		this.capitalBalance = capitalBalance;
	}
	public BigDecimal getTotalReceivable() {
		return totalReceivable;
	}
	public void setTotalReceivable(BigDecimal totalReceivable) {
		this.totalReceivable = totalReceivable;
	}
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
	public BigDecimal getGrossInstallment() {
		return grossInstallment;
	}
	public void setGrossInstallment(BigDecimal grossInstallment) {
		this.grossInstallment = grossInstallment;
	}
	public BigDecimal getTaxes() {
		return taxes;
	}
	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

}
