package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddTaxEventDetailsRequestResource extends TaxEventDetailsAddResource {

	@JsonIgnore
	private String taxEventCreatedUser;

	public String getTaxEventCreatedUser() {
		return taxEventCreatedUser;
	}

	public void setTaxEventCreatedUser(String taxEventCreatedUser) {
		this.taxEventCreatedUser = taxEventCreatedUser;
	}

}
