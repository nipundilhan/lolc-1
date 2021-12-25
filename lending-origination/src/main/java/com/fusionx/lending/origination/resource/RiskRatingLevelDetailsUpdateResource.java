package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *Risk Rating Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-338 		 FXL-684 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskRatingLevelDetailsUpdateResource {

	private Long id;

//	@NotBlank(message = "{common.not-null}")
//	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@Size(max = 20, message = "{common-name.size}")
	private String grade;

//	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common-name.size}")
	private String level;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskRatingLevelsId;

	@NotBlank(message = "{common.not-null}")
//	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String sequence;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getGrade() {
		return grade;
	}

	public String getLevel() {
		return level;
	}

	public String getRiskRatingLevelsId() {
		return riskRatingLevelsId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setRiskRatingLevelsId(String riskRatingLevelsId) {
		this.riskRatingLevelsId = riskRatingLevelsId;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
