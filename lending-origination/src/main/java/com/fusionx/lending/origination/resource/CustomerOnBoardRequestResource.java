package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerOnBoardRequestResource {
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{customerOnBoardRequestExpireDate.pattern}")
	private String customerOnBoardRequestExpireDate;
	
	@NotBlank(message = "{customerOnBoardRequestStatus.not-null}")
	@Pattern(regexp = "^$|CREATED|INPROGRESS|CANCELLED",message="{customerOnBoardRequestStatus.pattern}")
    private String customerOnBoardRequestStatus;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{customerOnBoardCustomerId.pattern}")
	private String customerOnBoardCustomerId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{customerOnBoardPendingCustomerId.pattern}")
	private String customerOnBoardPendingCustomerId;

	public String getCustomerOnBoardRequestExpireDate() {
		return customerOnBoardRequestExpireDate;
	}

	public void setCustomerOnBoardRequestExpireDate(String customerOnBoardRequestExpireDate) {
		this.customerOnBoardRequestExpireDate = customerOnBoardRequestExpireDate;
	}

	public String getCustomerOnBoardRequestStatus() {
		return customerOnBoardRequestStatus;
	}

	public void setCustomerOnBoardRequestStatus(String customerOnBoardRequestStatus) {
		this.customerOnBoardRequestStatus = customerOnBoardRequestStatus;
	}

	public String getCustomerOnBoardCustomerId() {
		return customerOnBoardCustomerId;
	}

	public void setCustomerOnBoardCustomerId(String customerOnBoardCustomerId) {
		this.customerOnBoardCustomerId = customerOnBoardCustomerId;
	}

	public String getCustomerOnBoardPendingCustomerId() {
		return customerOnBoardPendingCustomerId;
	}

	public void setCustomerOnBoardPendingCustomerId(String customerOnBoardPendingCustomerId) {
		this.customerOnBoardPendingCustomerId = customerOnBoardPendingCustomerId;
	}
	
	
}
