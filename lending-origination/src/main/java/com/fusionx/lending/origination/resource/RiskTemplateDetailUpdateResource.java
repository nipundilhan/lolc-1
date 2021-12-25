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
public class RiskTemplateDetailUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	private String weightagePercentage;
	
	@NotBlank(message = "{common.not-null}")
	private String indicator;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskParameterTemplateId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<RiskTemplateDetailsListUpdateResource> riskTemplateDetailsList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(String weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getRiskParameterTemplateId() {
		return riskParameterTemplateId;
	}

	public void setRiskParameterTemplateId(String riskParameterTemplateId) {
		this.riskParameterTemplateId = riskParameterTemplateId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RiskTemplateDetailsListUpdateResource> getRiskTemplateDetailsList() {
		return riskTemplateDetailsList;
	}

	public void setRiskTemplateDetailsList(List<RiskTemplateDetailsListUpdateResource> riskTemplateDetailsList) {
		this.riskTemplateDetailsList = riskTemplateDetailsList;
	}
	
}
