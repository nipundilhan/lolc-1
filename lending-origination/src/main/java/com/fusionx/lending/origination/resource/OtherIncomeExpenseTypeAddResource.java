package com.fusionx.lending.origination.resource;
/**
 * Other Income Expense Type Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtherIncomeExpenseTypeAddResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String otherIncomeCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String otherIncomeCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	private List<ExpenseTypeListResource> expenseTypeList;

	public String getOtherIncomeCategoryId() {
		return otherIncomeCategoryId;
	}

	public void setOtherIncomeCategoryId(String otherIncomeCategoryId) {
		this.otherIncomeCategoryId = otherIncomeCategoryId;
	}

	public String getOtherIncomeCategoryName() {
		return otherIncomeCategoryName;
	}

	public void setOtherIncomeCategoryName(String otherIncomeCategoryName) {
		this.otherIncomeCategoryName = otherIncomeCategoryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ExpenseTypeListResource> getExpenseTypeList() {
		return expenseTypeList;
	}

	public void setExpenseTypeList(List<ExpenseTypeListResource> expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}
	
	

}
