package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FacilityContractDetailsResource {
	
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	private String contractNo;
	
	private String contractStatus;
	
	private String contractStatusDes;
	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String approvedLimit;
//	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String availableLimit;
//	
//	private String customerName;
//	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String dueBalCurrentDate;
//	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String dueBalCurrentEom;
//	
//	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
//	private String expiryDate;
	
	private String leseCode;
	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String monthlyRental;
//	
//	private String productCode;
//	
//	private String productName;
//	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String settlementValue;
//	
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
//	private String totOutstandingBal;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractStatusDes() {
		return contractStatusDes;
	}

	public void setContractStatusDes(String contractStatusDes) {
		this.contractStatusDes = contractStatusDes;
	}

	public String getLeseCode() {
		return leseCode;
	}

	public void setLeseCode(String leseCode) {
		this.leseCode = leseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
