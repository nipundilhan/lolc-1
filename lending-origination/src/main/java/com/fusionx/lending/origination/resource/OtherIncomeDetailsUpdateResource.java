package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtherIncomeDetailsUpdateResource {
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeSourceDetailsId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String otherIncomeTypeId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{common-description.size}")
	private String description;

	@Size(max = 350, message = "{note.size}")
	private String behaviourCode;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String behaviourId;

	@Size(max = 350, message = "{note.size}")
	private String incomeLevelCode;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeLevelId;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfYearsEarned;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfMonthsEarned;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{note.size}")
	private String sourceType;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.invalid}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	private List<DocumentUpdateResource> otherIncomeDocumentList;

	public String getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}

	public void setIncomeSourceDetailsId(String incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}

	public String getOtherIncomeTypeId() {
		return otherIncomeTypeId;
	}

	public void setOtherIncomeTypeId(String otherIncomeTypeId) {
		this.otherIncomeTypeId = otherIncomeTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBehaviourCode() {
		return behaviourCode;
	}

	public void setBehaviourCode(String behaviourCode) {
		this.behaviourCode = behaviourCode;
	}

	public String getBehaviourId() {
		return behaviourId;
	}

	public void setBehaviourId(String behaviourId) {
		this.behaviourId = behaviourId;
	}

	public String getIncomeLevelCode() {
		return incomeLevelCode;
	}

	public void setIncomeLevelCode(String incomeLevelCode) {
		this.incomeLevelCode = incomeLevelCode;
	}

	public String getIncomeLevelId() {
		return incomeLevelId;
	}

	public void setIncomeLevelId(String incomeLevelId) {
		this.incomeLevelId = incomeLevelId;
	}

	public String getNoOfYearsEarned() {
		return noOfYearsEarned;
	}

	public void setNoOfYearsEarned(String noOfYearsEarned) {
		this.noOfYearsEarned = noOfYearsEarned;
	}

	public String getNoOfMonthsEarned() {
		return noOfMonthsEarned;
	}

	public void setNoOfMonthsEarned(String noOfMonthsEarned) {
		this.noOfMonthsEarned = noOfMonthsEarned;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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

	public List<DocumentUpdateResource> getOtherIncomeDocumentList() {
		return otherIncomeDocumentList;
	}

	public void setOtherIncomeDocumentList(List<DocumentUpdateResource> otherIncomeDocumentList) {
		this.otherIncomeDocumentList = otherIncomeDocumentList;
	}

}
