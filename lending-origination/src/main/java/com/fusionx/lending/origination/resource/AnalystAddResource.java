package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Analyst Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AnalystAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|INTERNAL|EXTERNAL",message="{analyst-type.pattern}")
	private String analystType; 
	
	@NotBlank(message = "{common.not-null}")
	private String analystUserId;  
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String  analystUserName; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String leadId;  
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 1500, message = "{observation.size}")
	private String  observation; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Valid
	private List<AnalystExceptionDetailsResource> analystExceptionDetailList;

	public String getAnalystType() {
		return analystType;
	}

	public void setAnalystType(String analystType) {
		this.analystType = analystType;
	}

	public String getAnalystUserId() {
		return analystUserId;
	}

	public void setAnalystUserId(String analystUserId) {
		this.analystUserId = analystUserId;
	}

	public String getAnalystUserName() {
		return analystUserName;
	}

	public void setAnalystUserName(String analystUserName) {
		this.analystUserName = analystUserName;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AnalystExceptionDetailsResource> getAnalystExceptionDetailList() {
		return analystExceptionDetailList;
	}

	public void setAnalystExceptionDetailList(List<AnalystExceptionDetailsResource> analystExceptionDetailList) {
		this.analystExceptionDetailList = analystExceptionDetailList;
	}

}
