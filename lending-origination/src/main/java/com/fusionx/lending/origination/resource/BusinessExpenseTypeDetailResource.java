package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessExpenseTypeDetailResource {

	private String expenseTypes;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String expenseTypesId;
	
	public String getExpenseTypes() {
		return expenseTypes;
	}

	public void setExpenseTypes(String expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	public String getExpenseTypesId() {
		return expenseTypesId;
	}

	public void setExpenseTypesId(String expenseTypesId) {
		this.expenseTypesId = expenseTypesId;
	}
	
}
