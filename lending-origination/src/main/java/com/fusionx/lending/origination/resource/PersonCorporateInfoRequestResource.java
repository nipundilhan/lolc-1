package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonCorporateInfoRequestResource {

	@JsonProperty("perCompanyName")
	@NotBlank(message = "{perCompanyName.not-null}")
	@Size(max = 255, message = "{perCompanyName.size}")
	private String perCompanyName;

	@JsonProperty("perGroupCompanyName")
	@Size(max = 255, message = "{perGroupCompanyName.size}")
	private String perGroupCompanyName;

	@JsonProperty("perTypeOfCompanyCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perTypeOfCompanyCommonListId.pattern}")
	private String perTypeOfCompanyCommonListId;
	private String perTypeOfCompanyDesc;
	
	@NotBlank(message = "{perCorporateCategoryCommonListId.not-null}")
	@JsonProperty("perCorporateCategoryCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perCorporateCategoryCommonListId.pattern}")
	private String perCorporateCategoryCommonListId;
	private String perCorporateCategoryDesc;
	
	@JsonProperty("perNatureOfBusinessCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNatureOfBusinessCommonListId.pattern}")
	private String perNatureOfBusinessCommonListId;
	private String perNatureOfBusinessDesc;

	@JsonProperty("perBusinessRegNo")
	@NotBlank(message = "{perBusinessRegNo.not-null}")
	@Size(max = 255, message = "{perBusinessRegNo.size}")
	private String perBusinessRegNo;
	
	@JsonProperty("perBusinessRegNoOld")
	@Size(max = 255, message = "{perBusinessRegNoOld.size}")
	private String perBusinessRegNoOld;

	@JsonProperty("perTaxNo")
	@Size(max = 255, message = "{perTaxNo.size}")
	private String perTaxNo;
	
	@JsonProperty("perRegisteredCountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perRegisteredCountryId.pattern}")
	private String perRegisteredCountryId;
	private String perRegisteredCountryDesc;
	
	@JsonProperty("perRegistrationAuthorityCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perRegistrationAuthorityCommonListId.pattern}")
	private String perRegistrationAuthorityCommonListId;
	private String perRegistrationAuthorityDesc;
	
	@JsonProperty("perResidentStatusCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perResidentStatusCommonListId.pattern}")
	private String perResidentStatusCommonListId;
	private String perResidentStatusDesc;
	
	public PersonCorporateInfoRequestResource() {
		super();
	}

	public String getPerCompanyName() {
		return perCompanyName;
	}

	public void setPerCompanyName(String perCompanyName) {
		this.perCompanyName = perCompanyName;
	}

	public String getPerGroupCompanyName() {
		return perGroupCompanyName;
	}

	public void setPerGroupCompanyName(String perGroupCompanyName) {
		this.perGroupCompanyName = perGroupCompanyName;
	}

	public String getPerTypeOfCompanyCommonListId() {
		return perTypeOfCompanyCommonListId;
	}

	public void setPerTypeOfCompanyCommonListId(String perTypeOfCompanyCommonListId) {
		this.perTypeOfCompanyCommonListId = perTypeOfCompanyCommonListId;
	}

	public String getPerTypeOfCompanyDesc() {
		return perTypeOfCompanyDesc;
	}

	public void setPerTypeOfCompanyDesc(String perTypeOfCompanyDesc) {
		this.perTypeOfCompanyDesc = perTypeOfCompanyDesc;
	}

	public String getPerCorporateCategoryCommonListId() {
		return perCorporateCategoryCommonListId;
	}

	public void setPerCorporateCategoryCommonListId(String perCorporateCategoryCommonListId) {
		this.perCorporateCategoryCommonListId = perCorporateCategoryCommonListId;
	}

	public String getPerCorporateCategoryDesc() {
		return perCorporateCategoryDesc;
	}

	public void setPerCorporateCategoryDesc(String perCorporateCategoryDesc) {
		this.perCorporateCategoryDesc = perCorporateCategoryDesc;
	}

	public String getPerNatureOfBusinessCommonListId() {
		return perNatureOfBusinessCommonListId;
	}

	public void setPerNatureOfBusinessCommonListId(String perNatureOfBusinessCommonListId) {
		this.perNatureOfBusinessCommonListId = perNatureOfBusinessCommonListId;
	}

	public String getPerNatureOfBusinessDesc() {
		return perNatureOfBusinessDesc;
	}

	public void setPerNatureOfBusinessDesc(String perNatureOfBusinessDesc) {
		this.perNatureOfBusinessDesc = perNatureOfBusinessDesc;
	}

	public String getPerBusinessRegNo() {
		return perBusinessRegNo;
	}

	public void setPerBusinessRegNo(String perBusinessRegNo) {
		this.perBusinessRegNo = perBusinessRegNo;
	}

	public String getPerBusinessRegNoOld() {
		return perBusinessRegNoOld;
	}

	public void setPerBusinessRegNoOld(String perBusinessRegNoOld) {
		this.perBusinessRegNoOld = perBusinessRegNoOld;
	}

	public String getPerTaxNo() {
		return perTaxNo;
	}

	public void setPerTaxNo(String perTaxNo) {
		this.perTaxNo = perTaxNo;
	}

	public String getPerRegisteredCountryId() {
		return perRegisteredCountryId;
	}

	public void setPerRegisteredCountryId(String perRegisteredCountryId) {
		this.perRegisteredCountryId = perRegisteredCountryId;
	}

	public String getPerRegisteredCountryDesc() {
		return perRegisteredCountryDesc;
	}

	public void setPerRegisteredCountryDesc(String perRegisteredCountryDesc) {
		this.perRegisteredCountryDesc = perRegisteredCountryDesc;
	}

	public String getPerRegistrationAuthorityCommonListId() {
		return perRegistrationAuthorityCommonListId;
	}

	public void setPerRegistrationAuthorityCommonListId(String perRegistrationAuthorityCommonListId) {
		this.perRegistrationAuthorityCommonListId = perRegistrationAuthorityCommonListId;
	}

	public String getPerRegistrationAuthorityDesc() {
		return perRegistrationAuthorityDesc;
	}

	public void setPerRegistrationAuthorityDesc(String perRegistrationAuthorityDesc) {
		this.perRegistrationAuthorityDesc = perRegistrationAuthorityDesc;
	}

	public String getPerResidentStatusCommonListId() {
		return perResidentStatusCommonListId;
	}

	public void setPerResidentStatusCommonListId(String perResidentStatusCommonListId) {
		this.perResidentStatusCommonListId = perResidentStatusCommonListId;
	}

	public String getPerResidentStatusDesc() {
		return perResidentStatusDesc;
	}

	public void setPerResidentStatusDesc(String perResidentStatusDesc) {
		this.perResidentStatusDesc = perResidentStatusDesc;
	}
}
