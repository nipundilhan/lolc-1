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
public class RentalCalculationpTreqRequestResource {
	
	private String treqEv;
	private String treqEqpCost;
	private String treqInsTyp;
	private String treqInsRate;
	
	public String getTreqEv() {
		return treqEv;
	}
	public void setTreqEv(String treqEv) {
		this.treqEv = treqEv;
	}
	public String getTreqEqpCost() {
		return treqEqpCost;
	}
	public void setTreqEqpCost(String treqEqpCost) {
		this.treqEqpCost = treqEqpCost;
	}
	public String getTreqInsTyp() {
		return treqInsTyp;
	}
	public void setTreqInsTyp(String treqInsTyp) {
		this.treqInsTyp = treqInsTyp;
	}
	public String getTreqInsRate() {
		return treqInsRate;
	}
	public void setTreqInsRate(String treqInsRate) {
		this.treqInsRate = treqInsRate;
	}
	@Override
	public String toString() {
		return "{treqEv=" + treqEv + ", treqEqpCost=" + treqEqpCost
				+ ", treqInsTyp=" + treqInsTyp + ", treqInsRate=" + treqInsRate + "}";
	}
	
	

}
