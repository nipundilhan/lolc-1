package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskTemplateAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskGradingId;
	
	@NotBlank(message = "{common.not-null}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<RiskTemplateDetailAddResource> riskTemplateDetails;
	
	public String getRiskGradingId() {
		return riskGradingId;
	}

	public void setRiskGradingId(String riskGradingId) {
		this.riskGradingId = riskGradingId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RiskTemplateDetailAddResource> getRiskTemplateDetails() {
		return riskTemplateDetails;
	}

	public void setRiskTemplateDetails(List<RiskTemplateDetailAddResource> riskTemplateDetails) {
		this.riskTemplateDetails = riskTemplateDetails;
	}
	
}
