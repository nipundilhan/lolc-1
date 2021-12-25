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
public class RentalCalculationpStruRequestResource {
	
	private String struSeq;
	private String struPrds;
	private String struRent;
	
	public String getStruSeq() {
		return struSeq;
	}
	public void setStruSeq(String struSeq) {
		this.struSeq = struSeq;
	}
	public String getStruPrds() {
		return struPrds;
	}
	public void setStruPrds(String struPrds) {
		this.struPrds = struPrds;
	}
	public String getStruRent() {
		return struRent;
	}
	public void setStruRent(String struRent) {
		this.struRent = struRent;
	}
	@Override
	public String toString() {
		return "{'struSeq'='" + struSeq + "', 'struPrds'='" + struPrds + "', 'struRent'='"
				+ struRent + "'}";
	}
	
	

}
