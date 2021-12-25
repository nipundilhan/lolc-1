package com.fusionx.lending.origination.resource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddRelationshipBasicInfoRequestResource extends RelationshipBasicInfoRequestResource{

	@JsonProperty("cusCreatedUser")
	//@NotBlank(message = "{usert.not-found}")
	private String cusCreatedUser;
	

	public String getCusCreatedUser() {
		return cusCreatedUser;
	}

	public void setCusCreatedUser(String cusCreatedUser) {
		this.cusCreatedUser = cusCreatedUser;
	}
}
