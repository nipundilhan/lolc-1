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
public class RentalCalculationpTrtxRequestResource {

	private String trtxCalMethod;
	private String trtxTrx;
	private String trtxAmt;
	private String trtxAddcrit;
	
	public String getTrtxCalMethod() {
		return trtxCalMethod;
	}
	public void setTrtxCalMethod(String trtxCalMethod) {
		this.trtxCalMethod = trtxCalMethod;
	}
	public String getTrtxTrx() {
		return trtxTrx;
	}
	public void setTrtxTrx(String trtxTrx) {
		this.trtxTrx = trtxTrx;
	}
	public String getTrtxAmt() {
		return trtxAmt;
	}
	public void setTrtxAmt(String trtxAmt) {
		this.trtxAmt = trtxAmt;
	}
	public String getTrtxAddcrit() {
		return trtxAddcrit;
	}
	public void setTrtxAddcrit(String trtxAddcrit) {
		this.trtxAddcrit = trtxAddcrit;
	}
	@Override
	public String toString() {
		return "{'trtxCalMethod'='" + trtxCalMethod + "', 'trtxTrx'='" + trtxTrx
				+ "', 'trtxAmt'='" + trtxAmt + "', 'trtxAddcrit'='" + trtxAddcrit + "'}";
	}
	
	
	
}


	
