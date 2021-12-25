package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class NetWorthLiabilityResponseResource {
	
	private String typeOfLiability;
	private String companyId;
	private String companyName;
	private String typeOfFacilityId;
	private String typeOfFacilityName;
	private String facilityAmount;
	private String rental;
	private String outstandingAmount;
	private String comment;
	private String status;
	
	public String getTypeOfLiability() {
		return typeOfLiability;
	}
	public void setTypeOfLiability(String typeOfLiability) {
		this.typeOfLiability = typeOfLiability;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTypeOfFacilityId() {
		return typeOfFacilityId;
	}
	public void setTypeOfFacilityId(String typeOfFacilityId) {
		this.typeOfFacilityId = typeOfFacilityId;
	}
	public String getTypeOfFacilityName() {
		return typeOfFacilityName;
	}
	public void setTypeOfFacilityName(String typeOfFacilityName) {
		this.typeOfFacilityName = typeOfFacilityName;
	}
	public String getFacilityAmount() {
		return facilityAmount;
	}
	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}
	public String getRental() {
		return rental;
	}
	public void setRental(String rental) {
		this.rental = rental;
	}
	public String getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
