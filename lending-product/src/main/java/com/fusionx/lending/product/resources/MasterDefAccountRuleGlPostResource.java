package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefAccountRuleGlPostResource {


	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String customerWise;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String accountWise;
	
	
	public String getCustomerWise() {
		return customerWise;
	}

	public void setCustomerWise(String customerWise) {
		this.customerWise = customerWise;
	}

	public String getAccountWise() {
		return accountWise;
	}

	public void setAccountWise(String accountWise) {
		this.accountWise = accountWise;
	}
}
