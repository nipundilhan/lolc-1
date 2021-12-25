package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsPendingApproveResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String insuranceDetailsStagingId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")	
	private String workflowProcessId;
	
	@Size(max = 350, message = "{common-description.size}")
	private String remarks;

	public String getInsuranceDetailsStagingId() {
		return insuranceDetailsStagingId;
	}

	public void setInsuranceDetailsStagingId(String insuranceDetailsStagingId) {
		this.insuranceDetailsStagingId = insuranceDetailsStagingId;
	}

	public String getWorkflowProcessId() {
		return workflowProcessId;
	}

	public void setWorkflowProcessId(String workflowProcessId) {
		this.workflowProcessId = workflowProcessId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
