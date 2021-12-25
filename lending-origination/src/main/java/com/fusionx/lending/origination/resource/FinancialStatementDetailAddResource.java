package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialStatementDetailAddResource {
	

	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String financailStatementTemplateDetailId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{common-amount.pattern}") 
	private String amount;
	
	@Valid
	List<FinancialStatementDetailNoteRequest> noteList;

	public List<FinancialStatementDetailNoteRequest> getNoteList() {
		return noteList;
	}

	public void setNoteList(List<FinancialStatementDetailNoteRequest> noteList) {
		this.noteList = noteList;
	}

	public String getFinancailStatementTemplateDetailId() {
		return financailStatementTemplateDetailId;
	}

	public void setFinancailStatementTemplateDetailId(String financailStatementTemplateDetailId) {
		this.financailStatementTemplateDetailId = financailStatementTemplateDetailId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}


	

}
