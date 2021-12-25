package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaiveOffTransactionTypeAddResource {

	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String waiveOffTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String transactionCodeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String transactionSubCodeId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWaiveOffTypeId() {
		return waiveOffTypeId;
	}

	public void setWaiveOffTypeId(String waiveOffTypeId) {
		this.waiveOffTypeId = waiveOffTypeId;
	}

	public String getTransactionCodeId() {
		return transactionCodeId;
	}

	public void setTransactionCodeId(String transactionCodeId) {
		this.transactionCodeId = transactionCodeId;
	}

	public String getTransactionSubCodeId() {
		return transactionSubCodeId;
	}

	public void setTransactionSubCodeId(String transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
	}
	
}
