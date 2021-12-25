package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialStatementDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String levelId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String levelName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String sequence;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String parentLevelId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{value.pattern}")
	private String totalValRequired;

	@Size(max = 300, message = "{common.size300}")
	private String formula;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{value.pattern}")
	private String formulaEnabled;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{value.pattern}")
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

	public String getTotalValRequired() {
		return totalValRequired;
	}

	public void setTotalValRequired(String totalValRequired) {
		this.totalValRequired = totalValRequired;
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

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
