package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeExpensesUpdateResource {
	
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String salaryIncomeTypeId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String salaryExpenseTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 30, message = "{common-name.size}")
	private String typeCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String freequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 30, message = "{common-name.size}")
	private String freequencyCode;
	
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String amount;
	
	private String description;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalaryIncomeTypeId() {
		return salaryIncomeTypeId;
	}

	public void setSalaryIncomeTypeId(String salaryIncomeTypeId) {
		this.salaryIncomeTypeId = salaryIncomeTypeId;
	}

	public String getSalaryExpenseTypeId() {
		return salaryExpenseTypeId;
	}

	public void setSalaryExpenseTypeId(String salaryExpenseTypeId) {
		this.salaryExpenseTypeId = salaryExpenseTypeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFreequencyId() {
		return freequencyId;
	}

	public void setFreequencyId(String freequencyId) {
		this.freequencyId = freequencyId;
	}

	public String getFreequencyCode() {
		return freequencyCode;
	}

	public void setFreequencyCode(String freequencyCode) {
		this.freequencyCode = freequencyCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
