package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AllocationTemplateAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String accountStatusId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String accountStatusName;

	public String getAccountStatusName() {
		return accountStatusName;
	}

	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(String accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

}
