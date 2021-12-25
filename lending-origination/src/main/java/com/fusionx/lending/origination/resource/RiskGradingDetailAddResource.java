package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskGradingDetailAddResource {

	@NotBlank(message = "{common.not-null}")	
	private String grading;
	
	@NotBlank(message = "{common.not-null}")
	private String shortCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String fromScore;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String toScore;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getGrading() {
		return grading;
	}

	public void setGrading(String grading) {
		this.grading = grading;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	/*
	public String getRiskGradingId() {
		return riskGradingId;
	}

	public void setRiskGradingId(String riskGradingId) {
		this.riskGradingId = riskGradingId;
	}
	*/
	public String getFromScore() {
		return fromScore;
	}

	public void setFromScore(String fromScore) {
		this.fromScore = fromScore;
	}

	public String getToScore() {
		return toScore;
	}

	public void setToScore(String toScore) {
		this.toScore = toScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
