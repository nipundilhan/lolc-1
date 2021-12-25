package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Due Date Templates Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DueDateTemplatesAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String effectiveDate;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "START|END|CUSTOMIZED|MULTIPLE", message = "{due-date-type.pattern}")
	private String dueDateType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@Valid
	private List<DueDateTypeListResource> dueDateTypeList;

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

	public String getDueDateType() {
		return dueDateType;
	}

	public void setDueDateType(String dueDateType) {
		this.dueDateType = dueDateType;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public List<DueDateTypeListResource> getDueDateTypeList() {
		return dueDateTypeList;
	}

	public void setDueDateTypeList(List<DueDateTypeListResource> dueDateTypeList) {
		this.dueDateTypeList = dueDateTypeList;
	}

	
}
