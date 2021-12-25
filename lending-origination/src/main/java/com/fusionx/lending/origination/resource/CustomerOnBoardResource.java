package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerOnBoardResource {
	
	private Long id;
		
	private Date customerOnBoardRequestExpireDate;
	
	private String customerOnBoardRequestStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCustomerOnBoardRequestExpireDate() {
		return customerOnBoardRequestExpireDate;
	}

	public void setCustomerOnBoardRequestExpireDate(Date customerOnBoardRequestExpireDate) {
		this.customerOnBoardRequestExpireDate = customerOnBoardRequestExpireDate;
	}

	public String getCustomerOnBoardRequestStatus() {
		return customerOnBoardRequestStatus;
	}

	public void setCustomerOnBoardRequestStatus(String customerOnBoardRequestStatus) {
		this.customerOnBoardRequestStatus = customerOnBoardRequestStatus;
	}
	
	
}
