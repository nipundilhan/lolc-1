package com.fusionx.lending.origination.resource;
/**
 * Other Income Expense Type Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtherIncomeExpenseTypeUpdateResource{
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String otherIncomeCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String otherIncomeCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<ExpenseTypeListResource> expenseTypeList;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
