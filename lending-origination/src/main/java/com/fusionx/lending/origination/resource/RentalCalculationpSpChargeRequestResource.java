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
public class RentalCalculationpSpChargeRequestResource {
	
	private String tcChargeCode;
	private String tcChargeType;
	private String tcChargePercentage;
	private String tc_charge_amt;
	private String tc_charge_add_meth;
	private String tc_charge_cal_point;
	private String tc_charge_split;
	
	public String getTcChargeCode() {
		return tcChargeCode;
	}
	public void setTcChargeCode(String tcChargeCode) {
		this.tcChargeCode = tcChargeCode;
	}
	public String getTcChargeType() {
		return tcChargeType;
	}
	public void setTcChargeType(String tcChargeType) {
		this.tcChargeType = tcChargeType;
	}
	public String getTcChargePercentage() {
		return tcChargePercentage;
	}
	public void setTcChargePercentage(String tcChargePercentage) {
		this.tcChargePercentage = tcChargePercentage;
	}
	public String getTc_charge_amt() {
		return tc_charge_amt;
	}
	public void setTc_charge_amt(String tc_charge_amt) {
		this.tc_charge_amt = tc_charge_amt;
	}
	public String getTc_charge_add_meth() {
		return tc_charge_add_meth;
	}
	public void setTc_charge_add_meth(String tc_charge_add_meth) {
		this.tc_charge_add_meth = tc_charge_add_meth;
	}
	public String getTc_charge_cal_point() {
		return tc_charge_cal_point;
	}
	public void setTc_charge_cal_point(String tc_charge_cal_point) {
		this.tc_charge_cal_point = tc_charge_cal_point;
	}
	public String getTc_charge_split() {
		return tc_charge_split;
	}
	public void setTc_charge_split(String tc_charge_split) {
		this.tc_charge_split = tc_charge_split;
	}
	@Override
	public String toString() {
		return "{tcChargeCode=" + tcChargeCode + ", tcChargeType="
				+ tcChargeType + ", tcChargePercentage=" + tcChargePercentage + ", tc_charge_amt=" + tc_charge_amt
				+ ", tc_charge_add_meth=" + tc_charge_add_meth + ", tc_charge_cal_point=" + tc_charge_cal_point
				+ ", tc_charge_split=" + tc_charge_split + "}";
	}
	
	

}
