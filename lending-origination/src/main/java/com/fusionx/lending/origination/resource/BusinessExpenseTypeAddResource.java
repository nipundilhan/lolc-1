package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessExpenseTypeAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessTypesId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;

	@Valid
	private List<BusinessExpenseTypeDetailResource> expenseTypesList;

	public String getBusinessTypesId() {
		return businessTypesId;
	}

	public void setBusinessTypesId(String businessTypesId) {
		this.businessTypesId = businessTypesId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BusinessExpenseTypeDetailResource> getExpenseTypesList() {
		return expenseTypesList;
	}

	public void setExpenseTypesList(List<BusinessExpenseTypeDetailResource> expenseTypesList) {
		this.expenseTypesList = expenseTypesList;
	}
	
}
