package com.fusionx.lending.origination.resource;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddCustomerOnBoardRequestResource extends CustomerOnBoardRequestResource{
	//@NotBlank(message = "{usert.not-found}")
    private String customerOnBoardRequestCreatedUser;

	public String getCustomerOnBoardRequestCreatedUser() {
		return customerOnBoardRequestCreatedUser;
	}

	public void setCustomerOnBoardRequestCreatedUser(String customerOnBoardRequestCreatedUser) {
		this.customerOnBoardRequestCreatedUser = customerOnBoardRequestCreatedUser;
	}
}
