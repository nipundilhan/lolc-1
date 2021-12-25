package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalInterestTypeAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String applicationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String applicationFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String calculationFrequencyName;
	
	@NotBlank(message = "{code.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String code;
	
	@NotBlank(message = "{name.not-null}")
	@Size(max = 70, message = "{name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String transSubCodeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String transSubCodeDescription;
	
	@NotBlank(message = "{code.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String transSubCodeCode;
	
	@Size(max = 2500, message = "{common-length01.size}")
	private String description;
	
	@Valid
	private List<PenalInterestTypeDetailsResource> penalInterestTypeDetails;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransSubCodeId() {
		return transSubCodeId;
	}

	public void setTransSubCodeId(String transSubCodeId) {
		this.transSubCodeId = transSubCodeId;
	}

	public String getTransSubCodeDescription() {
		return transSubCodeDescription;
	}

	public void setTransSubCodeDescription(String transSubCodeDescription) {
		this.transSubCodeDescription = transSubCodeDescription;
	}

	public String getTransSubCodeCode() {
		return transSubCodeCode;
	}

	public void setTransSubCodeCode(String transSubCodeCode) {
		this.transSubCodeCode = transSubCodeCode;
	}

	public String getApplicationFrequencyId() {
		return applicationFrequencyId;
	}

	public void setApplicationFrequencyId(String applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}

	public String getApplicationFrequencyName() {
		return applicationFrequencyName;
	}

	public void setApplicationFrequencyName(String applicationFrequencyName) {
		this.applicationFrequencyName = applicationFrequencyName;
	}

	public String getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(String calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PenalInterestTypeDetailsResource> getPenalInterestTypeDetails() {
		return penalInterestTypeDetails;
	}

	public void setPenalInterestTypeDetails(List<PenalInterestTypeDetailsResource> penalInterestTypeDetails) {
		this.penalInterestTypeDetails = penalInterestTypeDetails;
	}

}
