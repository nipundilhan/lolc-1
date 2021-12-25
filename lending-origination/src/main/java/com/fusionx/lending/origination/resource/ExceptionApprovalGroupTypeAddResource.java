package com.fusionx.lending.origination.resource;

/**
 * Exception Approval Group Type Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExceptionApprovalGroupTypeAddResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String exceptionApprovalGroupId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String exceptionTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String exceptionTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	

	public String getExceptionTypeId() {
		return exceptionTypeId;
	}

	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}

	public String getExceptionTypeName() {
		return exceptionTypeName;
	}

	public void setExceptionTypeName(String exceptionTypeName) {
		this.exceptionTypeName = exceptionTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExceptionApprovalGroupId() {
		return exceptionApprovalGroupId;
	}

	public void setExceptionApprovalGroupId(String exceptionApprovalGroupId) {
		this.exceptionApprovalGroupId = exceptionApprovalGroupId;
	}

}
