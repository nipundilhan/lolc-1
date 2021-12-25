package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Rental calculation (TC) Detail Service.
 * @author Sanatha
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   25-MAR-2021   		      FX-6064    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationpSnrvRequestResource {
	
	private String snrvFac;
	private String snrvCap;
	private String snrvExpDate;
	private String snrvRem1;
	private String snrvRem2;
	private String snrvIntRt;
	private String snrvPInt;
	private String snrvDtPay;
	private String snrvGPrd;
	private String snrvSpRt;
	
	public String getSnrvFac() {
		return snrvFac;
	}
	public void setSnrvFac(String snrvFac) {
		this.snrvFac = snrvFac;
	}
	public String getSnrvCap() {
		return snrvCap;
	}
	public void setSnrvCap(String snrvCap) {
		this.snrvCap = snrvCap;
	}
	public String getSnrvExpDate() {
		return snrvExpDate;
	}
	public void setSnrvExpDate(String snrvExpDate) {
		this.snrvExpDate = snrvExpDate;
	}
	public String getSnrvRem1() {
		return snrvRem1;
	}
	public void setSnrvRem1(String snrvRem1) {
		this.snrvRem1 = snrvRem1;
	}
	public String getSnrvRem2() {
		return snrvRem2;
	}
	public void setSnrvRem2(String snrvRem2) {
		this.snrvRem2 = snrvRem2;
	}
	public String getSnrvIntRt() {
		return snrvIntRt;
	}
	public void setSnrvIntRt(String snrvIntRt) {
		this.snrvIntRt = snrvIntRt;
	}
	public String getSnrvPInt() {
		return snrvPInt;
	}
	public void setSnrvPInt(String snrvPInt) {
		this.snrvPInt = snrvPInt;
	}
	public String getSnrvDtPay() {
		return snrvDtPay;
	}
	public void setSnrvDtPay(String snrvDtPay) {
		this.snrvDtPay = snrvDtPay;
	}
	public String getSnrvGPrd() {
		return snrvGPrd;
	}
	public void setSnrvGPrd(String snrvGPrd) {
		this.snrvGPrd = snrvGPrd;
	}
	public String getSnrvSpRt() {
		return snrvSpRt;
	}
	public void setSnrvSpRt(String snrvSpRt) {
		this.snrvSpRt = snrvSpRt;
	}
	@Override
	public String toString() {
		return "{'snrvFac'='" + snrvFac + "', 'snrvCap'='" + snrvCap + "', 'snrvExpDate'='"
				+ snrvExpDate + "', 'snrvRem1'='" + snrvRem1 + "', 'snrvRem2'='" + snrvRem2 + "', 'snrvIntRt'='" + snrvIntRt
				+ ", 'snrvPInt'=" + snrvPInt + ", 'snrvDtPay'='" + snrvDtPay + "', 'snrvGPrd'='" + snrvGPrd + "', 'snrvSpRt'='"
				+ snrvSpRt + "'}";
	}
	
	

}
