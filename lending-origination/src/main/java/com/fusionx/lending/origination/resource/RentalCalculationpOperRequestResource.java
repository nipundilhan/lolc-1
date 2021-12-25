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
public class RentalCalculationpOperRequestResource {
	
	private String operSeq;
	private String operMon1;
	private String operAmt1;
	private String operMon2;
	private String operAmt2;
	private String operMon3;
	private String operAmt3;
	private String operMon4;
	private String operAmt4;
	private String operMon5;
	private String operAmt5;
	
	public String getOperSeq() {
		return operSeq;
	}
	public void setOperSeq(String operSeq) {
		this.operSeq = operSeq;
	}
	public String getOperMon1() {
		return operMon1;
	}
	public void setOperMon1(String operMon1) {
		this.operMon1 = operMon1;
	}
	public String getOperAmt1() {
		return operAmt1;
	}
	public void setOperAmt1(String operAmt1) {
		this.operAmt1 = operAmt1;
	}
	public String getOperMon2() {
		return operMon2;
	}
	public void setOperMon2(String operMon2) {
		this.operMon2 = operMon2;
	}
	public String getOperAmt2() {
		return operAmt2;
	}
	public void setOperAmt2(String operAmt2) {
		this.operAmt2 = operAmt2;
	}
	public String getOperMon3() {
		return operMon3;
	}
	public void setOperMon3(String operMon3) {
		this.operMon3 = operMon3;
	}
	public String getOperAmt3() {
		return operAmt3;
	}
	public void setOperAmt3(String operAmt3) {
		this.operAmt3 = operAmt3;
	}
	public String getOperMon4() {
		return operMon4;
	}
	public void setOperMon4(String operMon4) {
		this.operMon4 = operMon4;
	}
	public String getOperAmt4() {
		return operAmt4;
	}
	public void setOperAmt4(String operAmt4) {
		this.operAmt4 = operAmt4;
	}
	public String getOperMon5() {
		return operMon5;
	}
	public void setOperMon5(String operMon5) {
		this.operMon5 = operMon5;
	}
	public String getOperAmt5() {
		return operAmt5;
	}
	public void setOperAmt5(String operAmt5) {
		this.operAmt5 = operAmt5;
	}
	@Override
	public String toString() {
		return "{operSeq=" + operSeq + ", operMon1=" + operMon1 + ", operAmt1="
				+ operAmt1 + ", operMon2=" + operMon2 + ", operAmt2=" + operAmt2 + ", operMon3=" + operMon3
				+ ", operAmt3=" + operAmt3 + ", operMon4=" + operMon4 + ", operAmt4=" + operAmt4 + ", operMon5="
				+ operMon5 + ", operAmt5=" + operAmt5 + "}";
	}
	
	

}
