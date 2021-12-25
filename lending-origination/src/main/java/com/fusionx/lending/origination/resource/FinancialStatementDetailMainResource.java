package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialStatementDetailMainResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String financailStatementTemplateId;
	
	@Valid
	List<FinancialStatementDetailAddResource> statementDetailList;

	
	
	public String getFinancailStatementTemplateId() {
		return financailStatementTemplateId;
	}

	public void setFinancailStatementTemplateId(String financailStatementTemplateId) {
		this.financailStatementTemplateId = financailStatementTemplateId;
	}

	public List<FinancialStatementDetailAddResource> getStatementDetailList() {
		return statementDetailList;
	}

	public void setStatementDetailList(List<FinancialStatementDetailAddResource> statementDetailList) {
		this.statementDetailList = statementDetailList;
	}
	
	

}
