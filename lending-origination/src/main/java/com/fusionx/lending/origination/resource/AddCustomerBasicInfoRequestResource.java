package com.fusionx.lending.origination.resource;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddCustomerBasicInfoRequestResource extends CustomerRequestResource{

	@JsonProperty("cusCreatedUser")
	//@NotBlank(message = "{usert.not-found}")
	private String cusCreatedUser;
	
	@JsonProperty("perIndividualInfo")
	@Valid
	private CustomerIndividualInfoRequestResource perIndividualInfo;
	
	@JsonProperty("perCorporateInfo")
	@Valid
	private CustomerCorporateBasicInfoRequestResource perCorporateInfo;

	public String getCusCreatedUser() {
		return cusCreatedUser;
	}

	public void setCusCreatedUser(String cusCreatedUser) {
		this.cusCreatedUser = cusCreatedUser;
	}

	public CustomerIndividualInfoRequestResource getPerIndividualInfo() {
		return perIndividualInfo;
	}

	public void setPerIndividualInfo(CustomerIndividualInfoRequestResource perIndividualInfo) {
		this.perIndividualInfo = perIndividualInfo;
	}

	public CustomerCorporateBasicInfoRequestResource getPerCorporateInfo() {
		return perCorporateInfo;
	}

	public void setPerCorporateInfo(CustomerCorporateBasicInfoRequestResource perCorporateInfo) {
		this.perCorporateInfo = perCorporateInfo;
	}
}
