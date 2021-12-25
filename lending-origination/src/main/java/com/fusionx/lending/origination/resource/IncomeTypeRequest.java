package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IncomeTypeRequest {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String businessTypename;
	
	@NotBlank(message = "{code.not-null}")
	@Size(max = 4, min = 4, message = "{common.code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String code;
	
	@NotBlank(message = "{name.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@Size(max = 350, message = "{common-description.size}")
	private String description;

	private String tenantId;

	@NotBlank(message = "{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{value.pattern}")
	private String status;


	public IncomeTypeRequest() {
		super();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessTypename() {
		return businessTypename;
	}

	public void setBusinessTypename(String businessTypename) {
		this.businessTypename = businessTypename;
	}

}
