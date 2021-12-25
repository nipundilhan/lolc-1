package com.fusionx.lending.product.resources;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Tc Structure Payment Schedule Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-10-2021   			 	 FXL-1139      Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TcStructurePaymentScheduleResource {
	
	private int period;
	
	private BigDecimal installment;

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

}
