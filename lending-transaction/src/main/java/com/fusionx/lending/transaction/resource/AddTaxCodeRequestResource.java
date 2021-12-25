package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddTaxCodeRequestResource extends TaxCodeAddResource {

	@JsonIgnore
	private String taxCodeCreatedUser;

	public String getTaxCodeCreatedUser() {
		return taxCodeCreatedUser;
	}

	public void setTaxCodeCreatedUser(String taxCodeCreatedUser) {
		this.taxCodeCreatedUser = taxCodeCreatedUser;
	}

}
