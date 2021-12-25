package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaiveOffApprovalGroupAddResource {

	@NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{common-code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
    private String code;

	@NotBlank(message = "{common.not-null}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;
	
	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|YES|NO", message = "{futureOutstAllow.pattern}")
	private String futureOutstAllow;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common-numeric.pattern}")
	private String authorityLimit;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFutureOutstAllow() {
		return futureOutstAllow;
	}

	public void setFutureOutstAllow(String futureOutstAllow) {
		this.futureOutstAllow = futureOutstAllow;
	}

	public String getAuthorityLimit() {
		return authorityLimit;
	}

	public void setAuthorityLimit(String authorityLimit) {
		this.authorityLimit = authorityLimit;
	}
	
}
