package com.fusionx.lending.origination.resource;
/**
 * 	Income Source Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IncomeSourceDetailsAddResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String leadId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String customerFullname;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String linkedPersonId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String linkedPersonName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "SALARY|BUSINESS|CULTIVATION|OTHER", message = "{common-income.pattern}")
	private String incomeType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "PRIMARY|SECONDARY", message = "{common-source.pattern}")
	private String sourceType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFullname() {
		return customerFullname;
	}

	public void setCustomerFullname(String customerFullname) {
		this.customerFullname = customerFullname;
	}

	public String getLinkedPersonId() {
		return linkedPersonId;
	}

	public void setLinkedPersonId(String linkedPersonId) {
		this.linkedPersonId = linkedPersonId;
	}

	public String getLinkedPersonName() {
		return linkedPersonName;
	}

	public void setLinkedPersonName(String linkedPersonName) {
		this.linkedPersonName = linkedPersonName;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
