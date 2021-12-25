package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AppraisalUploadCheckListAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String checklistTemplateId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String documentId;
	
	@NotBlank(message = "{common.not-null}")
	private String documentName;
	
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "Y|N", message = "{common-enable.pattern}")
	private String isMandatory;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "Y|N", message = "{common-enable.pattern}")
	private String isCompleted;
	
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}")
	private String completedDate;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;

	public String getChecklistTemplateId() {
		return checklistTemplateId;
	}

	public void setChecklistTemplateId(String checklistTemplateId) {
		this.checklistTemplateId = checklistTemplateId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
		
}
