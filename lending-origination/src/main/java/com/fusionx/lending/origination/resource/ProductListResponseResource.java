package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductListResponseResource {
	
	private String responseCode;
	
	private List<ProductListResponseObjectResponseResource> responseObject;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public List<ProductListResponseObjectResponseResource> getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(List<ProductListResponseObjectResponseResource> responseObject) {
		this.responseObject = responseObject;
	}
	
	
	

}
