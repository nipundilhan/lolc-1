package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ReferenceDetailsContactInfoAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;

	@NotBlank(message = "{common.not-null}")
	private String cntpCode;

	@NotBlank(message = "{common.not-null}")
	private String contactNo;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String cntpStatus;

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCntpCode() {
		return cntpCode;
	}

	public void setCntpCode(String cntpCode) {
		this.cntpCode = cntpCode;
	}

	public String getCntpStatus() {
		return cntpStatus;
	}

	public void setCntpStatus(String cntpStatus) {
		this.cntpStatus = cntpStatus;
	}

}
