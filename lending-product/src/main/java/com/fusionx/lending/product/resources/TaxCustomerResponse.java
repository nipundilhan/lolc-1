package com.fusionx.lending.product.resources;

import java.math.BigDecimal;
import java.util.Date;

public class TaxCustomerResponse {
	
	private String customerCategory;
	private Long customerSubType;
	private Long customerResidentTypeId;
	private String declarationType;
	private Date birthDay;
	
	public String getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public Long getCustomerResidentTypeId() {
		return customerResidentTypeId;
	}
	public void setCustomerResidentTypeId(Long customerResidentTypeId) {
		this.customerResidentTypeId = customerResidentTypeId;
	}
	public Long getCustomerSubType() {
		return customerSubType;
	}
	public void setCustomerSubType(Long customerSubType) {
		this.customerSubType = customerSubType;
	}
	public String getDeclarationType() {
		return declarationType;
	}
	public void setDeclarationType(String declarationType) {
		this.declarationType = declarationType;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	


	
	

	
	

}
