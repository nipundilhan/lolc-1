package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fusionx.lending.origination.domain.RiskParaTempCalFeild;

public class RiskParameterTemplateUpdateResource {

	
	@NotBlank(message = "{common.not-null}")
	private String name;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	private String calculationMethod;

	@NotBlank(message = "{common.not-null}")
	private String equation;

	@NotBlank(message = "{common.not-null}")
	private String riskMainCriteriaId;
	
	@NotBlank(message = "{common.not-null}")
	private String riskSubCriteriaId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<RiskParaTempCalFeildUpdateResource> riskParaTempCalFeild;

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

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}

	public String getRiskMainCriteriaId() {
		return riskMainCriteriaId;
	}

	public void setRiskMainCriteriaId(String riskMainCriteriaId) {
		this.riskMainCriteriaId = riskMainCriteriaId;
	}

	public String getRiskSubCriteriaId() {
		return riskSubCriteriaId;
	}

	public void setRiskSubCriteriaId(String riskSubCriteriaId) {
		this.riskSubCriteriaId = riskSubCriteriaId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<RiskParaTempCalFeildUpdateResource> getRiskParaTempCalFeild() {
		return riskParaTempCalFeild;
	}

	public void setRiskParaTempCalFeild(List<RiskParaTempCalFeildUpdateResource> riskParaTempCalFeild) {
		this.riskParaTempCalFeild = riskParaTempCalFeild;
	}

}
