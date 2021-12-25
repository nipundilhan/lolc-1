package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerAddressListResource {


	private String paddAddressTypeCommonListId;
	
	private String paddAddress01;
	
	private String ppaddId;

	public String getPaddAddressTypeCommonListId() {
		return paddAddressTypeCommonListId;
	}

	public void setPaddAddressTypeCommonListId(String paddAddressTypeCommonListId) {
		this.paddAddressTypeCommonListId = paddAddressTypeCommonListId;
	}

	public String getPaddAddress01() {
		return paddAddress01;
	}

	public void setPaddAddress01(String paddAddress01) {
		this.paddAddress01 = paddAddress01;
	}

	public String getPpaddId() {
		return ppaddId;
	}

	public void setPpaddId(String ppaddId) {
		this.ppaddId = ppaddId;
	}

	

}
