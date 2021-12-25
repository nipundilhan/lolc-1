package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExpenceTypeCultivationCategoryAddResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String cultivationCatId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String cultivationCatName;
	
//	@NotBlank(message = "{common.not-null}")
//	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
//	private String status;
	
	@Valid
	private List<ExpenseTypeListResource> expenseTypes;

	public String getCultivationCatId() {
		return cultivationCatId;
	}

	public void setCultivationCatId(String cultivationCatId) {
		this.cultivationCatId = cultivationCatId;
	}

	public String getCultivationCatName() {
		return cultivationCatName;
	}

	public void setCultivationCatName(String cultivationCatName) {
		this.cultivationCatName = cultivationCatName;
	}

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

	public List<ExpenseTypeListResource> getExpenseTypes() {
		return expenseTypes;
	}

	public void setExpenseTypes(List<ExpenseTypeListResource> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

}
