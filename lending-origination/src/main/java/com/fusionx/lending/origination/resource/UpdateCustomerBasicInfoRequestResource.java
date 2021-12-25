package com.fusionx.lending.origination.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UpdateCustomerBasicInfoRequestResource extends CustomerRequestResource{

	@JsonProperty("cusModifiedUser")
	//@NotBlank(message = "{usert.not-found}")
	private String cusModifiedUser;
	
	@JsonProperty("perIndividualInfo")
	@Valid
	private CustomerIndividualInfoRequestResource perIndividualInfo;
	
	@JsonProperty("perCorporateInfo")
	@Valid
	private CustomerCorporateBasicInfoRequestResource perCorporateInfo;

	public UpdateCustomerBasicInfoRequestResource() {
		super();
	}

	public String getCusModifiedUser() {
		return cusModifiedUser;
	}

	public void setCusModifiedUser(String cusModifiedUser) {
		this.cusModifiedUser = cusModifiedUser;
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
