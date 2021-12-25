package com.fusionx.lending.product.resources;

import java.util.List;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
 
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxProfileRequestResource {

	@JsonProperty("id")
	private String id;

	@JsonProperty("tenantId")
	private String tenantId;

//	@NotBlank(message = "{taxEffectiveDate.not-null}")
	@Pattern(regexp = "^$|(\\d{4})-(\\d{2})-(\\d{2})$", message = "{taxEffectiveDate.pattern}")
	private String taxEffectiveDate;

	@NotBlank(message = "{taxCodeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{taxCodeId.pattern}")
	private String taxCodeId;

	@NotBlank(message = "{taxApplicableLevel.not-null}")
	@Pattern(regexp = "^$|GLOBAL|CUSTOMER", message = "{taxApplicableLevel.pattern}")
	private String taxApplicableLevel;

	@NotBlank(message = "{productCategoryComnListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{productCategoryComnListId.pattern}")
	private String productCategoryComnListId;

	@NotBlank(message = "{productCategoryDesc.not-null}")
	@Size(max = 255, message = "{productCategoryDesc.size}")
	private String productCategoryDesc;

	@NotBlank(message = "{productCategoryComnListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{productCategoryComnListId.pattern}")
	private String subProductId;

	@NotBlank(message = "{applicableAccTypeComnListId.not-null}")
	@Pattern(regexp = "SAVINGS|CURRENT_ACCOUNT", message = "{applicableAccTypeComnListId.pattern}")
	private String applicableAccTypeComnListId;

	@NotBlank(message = "{applicableAccTypeDesc.not-null}")
	@Size(max = 255, message = "{applicableAccTypeDesc.size}")
	private String applicableAccTypeDesc;

	@NotBlank(message = "{applicableProductStatus.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{applicableProductStatus.pattern}")
	private String applicableProductStatus;

	@Pattern(regexp = "^$|[0-9]+", message = "{applicableProductId.pattern}")
	private String applicableProductId;

	@Size(max = 255, message = "{applicableProductName.size}")
	private String applicableProductName;

	// @NotBlank(message = "{declarationTypeComnListId.not-null}")
	// changed as NONDECLARED
	@Pattern(regexp = "^$|DECLARED|NONDECLARED|EXEMPTED|TAXPAYEE|IRDRATE", message = "{declarationTypeComnListId.pattern}")
	private String declarationTypeComnListId;

//	//@NotBlank(message = "{declarationTypeDesc.not-null}")
//	@Size(max = 255, message = "{declarationTypeDesc.size}")
//	private String declarationTypeDesc;

	@Pattern(regexp = "^$|[0-9]+", message = "{customerCategoryId.pattern}")
	private String customerCategoryId;

	private String customerCategoryCode;

	// @Pattern(regexp = "^$|INDIVIDUAL|NONINDIVIDUAL", message =
	// "{customerCategory.pattern}")
	private String customerCategory;

	@Pattern(regexp = "^$|[0-9]+", message = "{customerSubTypeIndividualId.pattern}")
	private String customerSubTypeIndividualId;

	@Size(max = 255, message = "{customerSubTypeIndividualDesc.size}")
	private String customerSubTypeIndividualDesc;

	@Pattern(regexp = "^$|[0-9]+", message = "{customerSubTypeNonIndividualId.pattern}")
	private String customerSubTypeNonIndividualId;

	@Size(max = 255, message = "{customerSubTypeNonIndividualDesc.size}")
	private String customerSubTypeNonIndividualDesc;

	@Pattern(regexp = "^$|[0-9]+", message = "{customerResidentTypeId.pattern}")
	private String customerResidentTypeId;

	@Size(max = 255, message = "{customerResidentTypeDesc.size}")
	private String customerResidentTypeDesc;

	@Pattern(regexp = "^$|[0-9]+", message = "{taxApplicableMinAge.pattern}")
	private String taxApplicableMinAge;

	@Pattern(regexp = "^$|[0-9]+", message = "{taxApplicableMaxAge.pattern}")
	private String taxApplicableMaxAge;

	@Pattern(regexp = "^$|ACTUAL|FINYEARSTART", message = "{ageEffectiveDateType.pattern}")
	private String ageEffectiveDateType;

	@NotBlank(message = "{taxAmountType.not-null}")
	@Pattern(regexp = "^$|AMOUNT|AMOUNTWITHTIER|RATE|RATEWITHTIER", message = "{taxAmountType.pattern}")
	private String taxAmountType;

	// @Pattern(regexp = "^$|\\d{1,13}\\.\\d{1,5}$",message="{taxAmount.pattern}")
//	@Pattern(regexp = "^$|[0-9]+", message = "{taxAmount.pattern}")
//	private String taxAmount;

	// @Pattern(regexp = "^$|\\d{1,13}\\.\\d{1,5}$",message="{taxRate.pattern}")
	// @Pattern(regexp = "^$|[0-9]*\\\\.?[0-9]*", message = "{taxRate.pattern}")
//	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{taxRate.pattern}")
//	private String taxRate;

	// @Pattern(regexp = "^$|[0-9]+",message="{taxApplicableMinValue.pattern}")
	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{taxApplicableMinValue.pattern}")
	private String taxApplicableMinValue;

	// @Pattern(regexp = "^$|[0-9]+",message="{taxApplicableMaxValue.pattern}")
	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{taxApplicableMaxValue.pattern}")
	private String taxApplicableMaxValue;

	// @Pattern(regexp = "^$|[0-9]+",message="{ceilingFloorfMinValue.pattern}")
	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{ceilingFloorfMaxValue.pattern}")
	private String ceilingFloorfMinValue;

	// @Pattern(regexp = "^$|[0-9]+",message="{ceilingFloorfMaxValue.pattern}")
	@Pattern(regexp = "^(([0-9]*)|(([0-9]*)\\.([0-9]*)))$", message = "{ceilingFloorfMaxValue.pattern}")
	private String ceilingFloorfMaxValue;

	@Pattern(regexp = "^$|YES|NO", message = "{otherInterestIncome.pattern}")
	private String otherInterestIncome;

	@NotBlank(message = "{taxProfileStatus.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{taxProfileStatus.pattern}")
	private String taxProfileStatus;

	@NotBlank(message = "{deductIndicator.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{deductIndicator.pattern}")
	private String deductIndicator;

	@NotBlank(message = "{baredBy.not-null}")
	@Pattern(regexp = "^$|CUSTOMER|BUSINESS", message = "{baredBy.pattern}")
	private String baredBy;

	private List<TaxProfileDetailsRequestResource> taxProfileDetailsList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTaxEffectiveDate() {
		return taxEffectiveDate;
	}

	public void setTaxEffectiveDate(String taxEffectiveDate) {
		this.taxEffectiveDate = taxEffectiveDate;
	}

	public String getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(String taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public String getTaxApplicableLevel() {
		return taxApplicableLevel;
	}

	public void setTaxApplicableLevel(String taxApplicableLevel) {
		this.taxApplicableLevel = taxApplicableLevel;
	}

	public String getProductCategoryComnListId() {
		return productCategoryComnListId;
	}

	public void setProductCategoryComnListId(String productCategoryComnListId) {
		this.productCategoryComnListId = productCategoryComnListId;
	}

	public String getApplicableAccTypeComnListId() {
		return applicableAccTypeComnListId;
	}

	public void setApplicableAccTypeComnListId(String applicableAccTypeComnListId) {
		this.applicableAccTypeComnListId = applicableAccTypeComnListId;
	}

	public String getApplicableAccTypeDesc() {
		return applicableAccTypeDesc;
	}

	public void setApplicableAccTypeDesc(String applicableAccTypeDesc) {
		this.applicableAccTypeDesc = applicableAccTypeDesc;
	}

	public String getApplicableProductStatus() {
		return applicableProductStatus;
	}

	public void setApplicableProductStatus(String applicableProductStatus) {
		this.applicableProductStatus = applicableProductStatus;
	}

	public String getApplicableProductId() {
		return applicableProductId;
	}

	public void setApplicableProductId(String applicableProductId) {
		this.applicableProductId = applicableProductId;
	}

	public String getApplicableProductName() {
		return applicableProductName;
	}

	public void setApplicableProductName(String applicableProductName) {
		this.applicableProductName = applicableProductName;
	}

	public String getDeclarationTypeComnListId() {
		return declarationTypeComnListId;
	}

	public void setDeclarationTypeComnListId(String declarationTypeComnListId) {
		this.declarationTypeComnListId = declarationTypeComnListId;
	}

//	public String getDeclarationTypeDesc() {
//		return declarationTypeDesc;
//	}
//
//	public void setDeclarationTypeDesc(String declarationTypeDesc) {
//		this.declarationTypeDesc = declarationTypeDesc;
//	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getCustomerSubTypeIndividualId() {
		return customerSubTypeIndividualId;
	}

	public void setCustomerSubTypeIndividualId(String customerSubTypeIndividualId) {
		this.customerSubTypeIndividualId = customerSubTypeIndividualId;
	}

	public String getCustomerSubTypeIndividualDesc() {
		return customerSubTypeIndividualDesc;
	}

	public void setCustomerSubTypeIndividualDesc(String customerSubTypeIndividualDesc) {
		this.customerSubTypeIndividualDesc = customerSubTypeIndividualDesc;
	}

	public String getCustomerSubTypeNonIndividualId() {
		return customerSubTypeNonIndividualId;
	}

	public void setCustomerSubTypeNonIndividualId(String customerSubTypeNonIndividualId) {
		this.customerSubTypeNonIndividualId = customerSubTypeNonIndividualId;
	}

	public String getCustomerSubTypeNonIndividualDesc() {
		return customerSubTypeNonIndividualDesc;
	}

	public void setCustomerSubTypeNonIndividualDesc(String customerSubTypeNonIndividualDesc) {
		this.customerSubTypeNonIndividualDesc = customerSubTypeNonIndividualDesc;
	}

	public String getCustomerResidentTypeId() {
		return customerResidentTypeId;
	}

	public void setCustomerResidentTypeId(String customerResidentTypeId) {
		this.customerResidentTypeId = customerResidentTypeId;
	}

	public String getCustomerResidentTypeDesc() {
		return customerResidentTypeDesc;
	}

	public void setCustomerResidentTypeDesc(String customerResidentTypeDesc) {
		this.customerResidentTypeDesc = customerResidentTypeDesc;
	}

	public String getTaxApplicableMinAge() {
		return taxApplicableMinAge;
	}

	public void setTaxApplicableMinAge(String taxApplicableMinAge) {
		this.taxApplicableMinAge = taxApplicableMinAge;
	}

	public String getTaxApplicableMaxAge() {
		return taxApplicableMaxAge;
	}

	public void setTaxApplicableMaxAge(String taxApplicableMaxAge) {
		this.taxApplicableMaxAge = taxApplicableMaxAge;
	}

	public String getAgeEffectiveDateType() {
		return ageEffectiveDateType;
	}

	public void setAgeEffectiveDateType(String ageEffectiveDateType) {
		this.ageEffectiveDateType = ageEffectiveDateType;
	}

	public String getTaxAmountType() {
		return taxAmountType;
	}

	public void setTaxAmountType(String taxAmountType) {
		this.taxAmountType = taxAmountType;
	}

	public String getTaxApplicableMinValue() {
		return taxApplicableMinValue;
	}

	public void setTaxApplicableMinValue(String taxApplicableMinValue) {
		this.taxApplicableMinValue = taxApplicableMinValue;
	}

	public String getTaxApplicableMaxValue() {
		return taxApplicableMaxValue;
	}

	public void setTaxApplicableMaxValue(String taxApplicableMaxValue) {
		this.taxApplicableMaxValue = taxApplicableMaxValue;
	}

	public String getCeilingFloorfMinValue() {
		return ceilingFloorfMinValue;
	}

	public void setCeilingFloorfMinValue(String ceilingFloorfMinValue) {
		this.ceilingFloorfMinValue = ceilingFloorfMinValue;
	}

	public String getCeilingFloorfMaxValue() {
		return ceilingFloorfMaxValue;
	}

	public void setCeilingFloorfMaxValue(String ceilingFloorfMaxValue) {
		this.ceilingFloorfMaxValue = ceilingFloorfMaxValue;
	}

	public String getOtherInterestIncome() {
		return otherInterestIncome;
	}

	public void setOtherInterestIncome(String otherInterestIncome) {
		this.otherInterestIncome = otherInterestIncome;
	}

	public String getTaxProfileStatus() {
		return taxProfileStatus;
	}

	public void setTaxProfileStatus(String taxProfileStatus) {
		this.taxProfileStatus = taxProfileStatus;
	}

	public String getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(String customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public String getCustomerCategoryCode() {
		return customerCategoryCode;
	}

	public void setCustomerCategoryCode(String customerCategoryCode) {
		this.customerCategoryCode = customerCategoryCode;
	}

	// Added by Senitha on 01-05-2020 for validate record exception
	private String message;

	private String version;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeductIndicator() {
		return deductIndicator;
	}

	public void setDeductIndicator(String deductIndicator) {
		this.deductIndicator = deductIndicator;
	}

	public String getBaredBy() {
		return baredBy;
	}

	public void setBaredBy(String baredBy) {
		this.baredBy = baredBy;
	}

	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getProductCategoryDesc() {
		return productCategoryDesc;
	}

	public void setProductCategoryDesc(String productCategoryDesc) {
		this.productCategoryDesc = productCategoryDesc;
	}

	public List<TaxProfileDetailsRequestResource> getTaxProfileDetailsList() {
		return taxProfileDetailsList;
	}

	public void setTaxProfileDetailsList(List<TaxProfileDetailsRequestResource> taxProfileDetailsList) {
		this.taxProfileDetailsList = taxProfileDetailsList;
	}

}
