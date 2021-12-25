package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Business Income Expense Request Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessIncomeExpenseRequestResource {
	
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessTypesId;

	@Size(max = 70, message = "{common-name.size}")
	private String businessTypeName;
	
	@Size(max = 255, message = "{description.size}")
	private String description;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String frequencyId;
	private String frequencyCode;
	private String frequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalGrossIncome;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalExpences;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String profitMargin;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalNetIncome;
	
	@Size(max = 255, message = "{comment.size}")
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	List<IncomeExpenseDetailsResource> incomeExpenseDetails;
	
	@Valid
	List<IncomeExpenseTaxResource> incomeExpenseTax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessTypesId() {
		return businessTypesId;
	}

	public void setBusinessTypesId(String businessTypesId) {
		this.businessTypesId = businessTypesId;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public String getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(String totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}

	public String getTotalExpences() {
		return totalExpences;
	}

	public void setTotalExpences(String totalExpences) {
		this.totalExpences = totalExpences;
	}

	public String getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(String profitMargin) {
		this.profitMargin = profitMargin;
	}

	public String getTotalNetIncome() {
		return totalNetIncome;
	}

	public void setTotalNetIncome(String totalNetIncome) {
		this.totalNetIncome = totalNetIncome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<IncomeExpenseDetailsResource> getIncomeExpenseDetails() {
		return incomeExpenseDetails;
	}

	public void setIncomeExpenseDetails(List<IncomeExpenseDetailsResource> incomeExpenseDetails) {
		this.incomeExpenseDetails = incomeExpenseDetails;
	}

	public List<IncomeExpenseTaxResource> getIncomeExpenseTax() {
		return incomeExpenseTax;
	}

	public void setIncomeExpenseTax(List<IncomeExpenseTaxResource> incomeExpenseTax) {
		this.incomeExpenseTax = incomeExpenseTax;
	}
}
