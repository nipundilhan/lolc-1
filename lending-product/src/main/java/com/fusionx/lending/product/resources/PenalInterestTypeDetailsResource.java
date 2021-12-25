package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalInterestTypeDetailsResource {

	private String id;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String subTransTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String subTransTypeDescription;
	
	@NotBlank(message = "{code.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String subTransTypeCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubTransTypeId() {
		return subTransTypeId;
	}

	public void setSubTransTypeId(String subTransTypeId) {
		this.subTransTypeId = subTransTypeId;
	}

	public String getSubTransTypeDescription() {
		return subTransTypeDescription;
	}

	public void setSubTransTypeDescription(String subTransTypeDescription) {
		this.subTransTypeDescription = subTransTypeDescription;
	}

	public String getSubTransTypeCode() {
		return subTransTypeCode;
	}

	public void setSubTransTypeCode(String subTransTypeCode) {
		this.subTransTypeCode = subTransTypeCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
