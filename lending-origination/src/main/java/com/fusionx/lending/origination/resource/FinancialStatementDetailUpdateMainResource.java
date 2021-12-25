package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialStatementDetailUpdateMainResource {

	@Valid
	List<FinancialStatementDetailUpdateResource> statementDetailList;

	public List<FinancialStatementDetailUpdateResource> getStatementDetailList() {
		return statementDetailList;
	}

	public void setStatementDetailList(List<FinancialStatementDetailUpdateResource> statementDetailList) {
		this.statementDetailList = statementDetailList;
	}
	
	
}
