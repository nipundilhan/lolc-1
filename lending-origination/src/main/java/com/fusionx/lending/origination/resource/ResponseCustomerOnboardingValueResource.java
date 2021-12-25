package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerOnboardingValueResource {

	private String onBoardReqId;

	private String onBoardReqVersion;

	public String getOnBoardReqId() {
		return onBoardReqId;
	}

	public void setOnBoardReqId(String onBoardReqId) {
		this.onBoardReqId = onBoardReqId;
	}

	public String getOnBoardReqVersion() {
		return onBoardReqVersion;
	}

	public void setOnBoardReqVersion(String onBoardReqVersion) {
		this.onBoardReqVersion = onBoardReqVersion;
	}

	
    
}
