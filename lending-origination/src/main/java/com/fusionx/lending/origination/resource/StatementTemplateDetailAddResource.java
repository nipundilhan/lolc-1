package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StatementTemplateDetailAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String levelId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String levelName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String parentLevelId;

	@Size(max = 70, message = "{common-name.size}")
	private String parentLevelName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{common.invalid-value}")
	private String totalValRequired;

	@Size(max = 300, message = "{common.size300}")
	private String formula;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{common.invalid-value}")
	private String formulaEnabled;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{common.invalid-value}")
	private String additionalNoteRequired;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getParentLevelId() {
		return parentLevelId;
	}

	public void setParentLevelId(String parentLevelId) {
		this.parentLevelId = parentLevelId;
	}

	public String getParentLevelName() {
		return parentLevelName;
	}

	public void setParentLevelName(String parentLevelName) {
		this.parentLevelName = parentLevelName;
	}

	public String getTotalValRequired() {
		return totalValRequired;
	}

	public void setTotalValRequired(String totalValRequired) {
		this.totalValRequired = totalValRequired;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormulaEnabled() {
		return formulaEnabled;
	}

	public void setFormulaEnabled(String formulaEnabled) {
		this.formulaEnabled = formulaEnabled;
	}

	public String getAdditionalNoteRequired() {
		return additionalNoteRequired;
	}

	public void setAdditionalNoteRequired(String additionalNoteRequired) {
		this.additionalNoteRequired = additionalNoteRequired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
