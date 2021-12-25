package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;

public class IdentificationNumberCheckRequestResource {

	@NotBlank(message = "{identificationNumber.not-null}")
	private String identificationNumber;

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	
	
}
