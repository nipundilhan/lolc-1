package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinanceStatementUpdateRequest {
	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String statementTemplateId;

	@NotBlank(message = "{common.not-null}")
	private String statementTemplateName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String statementTypeId;

	@NotBlank(message = "{common.not-null}")
	private String statementTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String fromDate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String toDate;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfReports;
	
//	@NotBlank(message = "{common.not-null}")
//	private String auditByUserId;

	@NotBlank(message = "{common.not-null}")
	private String auditByUserName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String version;
	

	public String getStatementTemplateId() {
		return statementTemplateId;
	}

	public void setStatementTemplateId(String statementTemplateId) {
		this.statementTemplateId = statementTemplateId;
	}

	public String getStatementTemplateName() {
		return statementTemplateName;
	}

	public void setStatementTemplateName(String statementTemplateName) {
		this.statementTemplateName = statementTemplateName;
	}

	public String getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(String statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

	public String getStatementTypeName() {
		return statementTypeName;
	}

	public void setStatementTypeName(String statementTypeName) {
		this.statementTypeName = statementTypeName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getNoOfReports() {
		return noOfReports;
	}

	public void setNoOfReports(String noOfReports) {
		this.noOfReports = noOfReports;
	}
//
//	public String getAuditByUserId() {
//		return auditByUserId;
//	}
//
//	public void setAuditByUserId(String auditByUserId) {
//		this.auditByUserId = auditByUserId;
//	}
	
	

	public String getAuditByUserName() {
		return auditByUserName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setAuditByUserName(String auditByUserName) {
		this.auditByUserName = auditByUserName;
	}

}
