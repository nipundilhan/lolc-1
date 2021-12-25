package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddCustomerOnBoardProductRequestResource extends CustomerOnBoardProductRequestResource{
	//@NotBlank(message = "{usert.not-found}")
    private String productCreatedUser;

	public String getProductCreatedUser() {
		return productCreatedUser;
	}

	public void setProductCreatedUser(String productCreatedUser) {
		this.productCreatedUser = productCreatedUser;
	}

}
