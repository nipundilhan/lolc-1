package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerOnboardProductValueResource {

	private String onBoardProductId;

	private String onBoardProductVersion;

	public String getOnBoardProductId() {
		return onBoardProductId;
	}

	public void setOnBoardProductId(String onBoardProductId) {
		this.onBoardProductId = onBoardProductId;
	}

	public String getOnBoardProductVersion() {
		return onBoardProductVersion;
	}

	public void setOnBoardProductVersion(String onBoardProductVersion) {
		this.onBoardProductVersion = onBoardProductVersion;
	}

	
	
    
}
