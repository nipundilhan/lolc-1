package com.fusionx.lending.origination.resource;
/**
 *AddKeyPersonBasicInfoRequestResource
 * 
 ********************************************************************************************************
 * ###     Date            Story Point    Task No     Author     Description
 * -------------------------------------------------------------------------------------------------------
 * 1       04-08-2021      FXL-381        FXL-424     Piyumi      Created
 * 
 ********************************************************************************************************
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddKeyPersonBasicInfoRequestResource extends KeyPersonBasicInfoRequestResource{

	@JsonProperty("cusCreatedUser")
	private String cusCreatedUser;
	

	public String getCusCreatedUser() {
		return cusCreatedUser;
	}

	public void setCusCreatedUser(String cusCreatedUser) {
		this.cusCreatedUser = cusCreatedUser;
	}
}
