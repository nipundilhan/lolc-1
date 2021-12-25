package com.fusionx.lending.origination.resource;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskRatingLevelUpdateResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskAuthorityId;

	@NotBlank(message = "{common.not-null}")
	private String riskType;

	@NotBlank(message = "{common.not-null}")
	private String riskRatingType;

	@Size(max = 255, message = "{note.size}")
	private String note;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	private List<RiskRatingLevelDetailsUpdateResource> riskRatingLevelDetails;

	public String getRiskAuthorityId() {
		return riskAuthorityId;
	}

	public void setRiskAuthorityId(String riskAuthorityId) {
		this.riskAuthorityId = riskAuthorityId;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getRiskRatingType() {
		return riskRatingType;
	}

	public void setRiskRatingType(String riskRatingType) {
		this.riskRatingType = riskRatingType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public List<RiskRatingLevelDetailsUpdateResource> getRiskRatingLevelDetails() {
		return riskRatingLevelDetails;
	}

	public void setRiskRatingLevelDetails(List<RiskRatingLevelDetailsUpdateResource> riskRatingLevelDetails) {
		this.riskRatingLevelDetails = riskRatingLevelDetails;
	}

}
