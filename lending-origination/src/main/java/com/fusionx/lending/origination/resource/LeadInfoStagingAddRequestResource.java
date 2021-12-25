package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LeadInfoStagingAddRequestResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String leadId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{note.size}")
	private String stagingStatus;
	
	@Size(max = 350, message = "{note.size}")
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.invalid}")
	private String status;

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getStagingStatus() {
		return stagingStatus;
	}

	public void setStagingStatus(String stagingStatus) {
		this.stagingStatus = stagingStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
