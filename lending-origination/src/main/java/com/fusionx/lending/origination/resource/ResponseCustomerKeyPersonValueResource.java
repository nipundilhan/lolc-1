package com.fusionx.lending.origination.resource;
/**
 * ResponseCustomerKeyPersonValueResource
 * 
 ********************************************************************************************************
 * ### 		Date 			Story Point 	Task No 	Author 		Description
 * -------------------------------------------------------------------------------------------------------
 * 1 		04-08-2021 		FXL-381			FXL-424		Piyumi	 	Created
 * 
 ********************************************************************************************************
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerKeyPersonValueResource {

	private String penCustomerId;

	private String pculpId;

	public String getPenCustomerId() {
		return penCustomerId;
	}

	public void setPenCustomerId(String penCustomerId) {
		this.penCustomerId = penCustomerId;
	}

	public String getPculpId() {
		return pculpId;
	}

	public void setPculpId(String pculpId) {
		this.pculpId = pculpId;
	}
    
}
