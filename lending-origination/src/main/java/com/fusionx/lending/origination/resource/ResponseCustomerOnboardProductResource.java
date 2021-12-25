package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerOnboardProductResource {

	
	private String productCategoryId;
				
	private String messages;
		
	private ResponseCustomerOnboardProductValueResource value;

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public ResponseCustomerOnboardProductValueResource getValue() {
		return value;
	}

	public void setValue(ResponseCustomerOnboardProductValueResource value) {
		this.value = value;
	}

	
}
