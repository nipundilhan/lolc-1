package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskGradingUpdateResource {

	@NotBlank(message = "{common.not-null}")	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessPersonTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String businessPersonTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String industryTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String industryTypeName;
	
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	@NotBlank(message = "{common.not-null}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<RiskGradingDetailUpdateResource> riskGradingDetails;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getBusinessPersonTypeId() {
		return businessPersonTypeId;
	}

	public void setBusinessPersonTypeId(String businessPersonTypeId) {
		this.businessPersonTypeId = businessPersonTypeId;
	}

	public String getBusinessPersonTypeName() {
		return businessPersonTypeName;
	}

	public void setBusinessPersonTypeName(String businessPersonTypeName) {
		this.businessPersonTypeName = businessPersonTypeName;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}

	public String getIndustryTypeName() {
		return industryTypeName;
	}

	public void setIndustryTypeName(String industryTypeName) {
		this.industryTypeName = industryTypeName;
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

	public List<RiskGradingDetailUpdateResource> getRiskGradingDetails() {
		return riskGradingDetails;
	}

	public void setRiskGradingDetails(List<RiskGradingDetailUpdateResource> riskGradingDetails) {
		this.riskGradingDetails = riskGradingDetails;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
