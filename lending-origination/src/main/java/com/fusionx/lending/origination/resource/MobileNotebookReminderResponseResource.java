package com.fusionx.lending.origination.resource;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Mobile Notebook Reminder Response Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MobileNotebookReminderResponseResource {
	
	private String customerFullName;
	
	private String nicNo;
	
	private BigDecimal requiredLoanAmount;
	
	private Long contactNumber;
	
	private Timestamp createdDate;

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getNicNo() {
		return nicNo;
	}

	public void setNicNo(String nicNo) {
		this.nicNo = nicNo;
	}

	public BigDecimal getRequiredLoanAmount() {
		return requiredLoanAmount;
	}

	public void setRequiredLoanAmount(BigDecimal requiredLoanAmount) {
		this.requiredLoanAmount = requiredLoanAmount;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
