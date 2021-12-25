package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonBasicInfoResponseResource {

	private Long id;
	
	private Long perId;
	
	private String perCode;
	
	private Long perTitleCommonListId;
	
	private String perTitleDesc;

	private String perFirstName;

	private String perMiddleName;

	private String perLastName;

	private String perFullName;

	private String perInitials;

	private String perPreferredName;

	private String perOtherName;

	private Long perGenderCommonListId;
	
	private String perGenderDesc;

	private String perBirthPlace;

	private Date perDateOfBirth;

	private Long perBirthCountryId;
	
	private String perBirthCountryDesc;

	private Long perNationality1CommonListId;
	
	private String perNationality1Desc;
	
	private Long perNationality2CommonListId;
	
	private String perNationality2Desc;
	
	private Long perNationality3CommonListId;
	
	private String perNationality3Desc;
	
	private Long perNationality1CountryId;
	
	private String perNationality1CountryDesc;
	
	private Long perNationality2CountryId;
	
	private String perNationality2CountryDesc;
	
	private Long perNationality3CountryId;
	
	private String perNationality3CountryDesc;

	private Long perMaritalStatusCommonListId;
	
	private String perMaritalStatusDesc;

	private Long perResidentStatusCommonListId;
	
	private String perResidentStatusDesc;
	
	private String perResidentStatusCode;
	
	private Long perResident1CountryId;
	
	private String perResident1CountryDesc;
	
	private Long perResident2CountryId;
	
	private String perResident2CountryDesc;
	
	private Long perResident3CountryId;
	
	private String perResident3CountryDesc;

	private Long perEducationalLevelCommonListId;
	
	private String perEducationalLevelDesc;
	
	private String perCompanyName;

	private String perGroupCompanyName;

	private Long perTypeOfCompanyCommonListId;
	
	private String perTypeOfCompanyDesc;
	
	private Long perCorporateCategoryCommonListId;
	
	private String perCorporateCategoryDesc;
	
	private String perCorporateCategoryCode;
	
	private Long perNatureOfBusinessCommonListId;
	
	private String perNatureOfBusinessDesc;

	private String perBusinessRegNo;
	
	private String perBusinessRegNoOld;

	private String perTaxNo;
	
	private Long perRegisCountryId;
	
	private String perRegisCountryDesc;
	
	private Long perRegistrationAuthorityCommonListId;
	
	private String perRegistrationAuthorityDesc;

	private Long perPreferredLanguageId;
	
	private String perPreferredLanguageDesc;

	private String perContactPerson;
	
	private String perComment;
	
	private String perAttribute1;
	
	private String perAttribute2;
	
	private String perAttribute3;
	
	private String perAttribute4;
	
	private String perAttribute5;
	
	private String perAttribute6;
	
	private String perAttribute7;
	
	private String perAttribute8;
	
	private String perAttribute9;
	
	private String perAttribute10;
	
	private String perAttribute11;
	
	private String perAttribute12;
	
	private String perAttribute13;
	
	private String perAttribute14;
	
	private String perAttribute15;
	
	private String perAttribute16;
	
	private String perAttribute17;
	
	private String perAttribute18;
	
	private String perAttribute19;
	
	private String perAttribute20;
	
	private Long perKYCId;
	
	private Long perRiskProfileId;
	
	private String perPepStatus;
	
	private String perPepComment;

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public Long getPerTitleCommonListId() {
		return perTitleCommonListId;
	}

	public void setPerTitleCommonListId(Long perTitleCommonListId) {
		this.perTitleCommonListId = perTitleCommonListId;
	}

	public String getPerFirstName() {
		return perFirstName;
	}

	public void setPerFirstName(String perFirstName) {
		this.perFirstName = perFirstName;
	}

	public String getPerMiddleName() {
		return perMiddleName;
	}

	public void setPerMiddleName(String perMiddleName) {
		this.perMiddleName = perMiddleName;
	}

	public String getPerLastName() {
		return perLastName;
	}

	public void setPerLastName(String perLastName) {
		this.perLastName = perLastName;
	}

	public String getPerFullName() {
		return perFullName;
	}

	public void setPerFullName(String perFullName) {
		this.perFullName = perFullName;
	}

	public String getPerInitials() {
		return perInitials;
	}

	public void setPerInitials(String perInitials) {
		this.perInitials = perInitials;
	}

	public String getPerPreferredName() {
		return perPreferredName;
	}

	public void setPerPreferredName(String perPreferredName) {
		this.perPreferredName = perPreferredName;
	}

	public String getPerOtherName() {
		return perOtherName;
	}

	public void setPerOtherName(String perOtherName) {
		this.perOtherName = perOtherName;
	}

	public Long getPerGenderCommonListId() {
		return perGenderCommonListId;
	}

	public void setPerGenderCommonListId(Long perGenderCommonListId) {
		this.perGenderCommonListId = perGenderCommonListId;
	}

	public String getPerBirthPlace() {
		return perBirthPlace;
	}

	public void setPerBirthPlace(String perBirthPlace) {
		this.perBirthPlace = perBirthPlace;
	}

	public Date getPerDateOfBirth() {
		return perDateOfBirth;
	}

	public void setPerDateOfBirth(Date perDateOfBirth) {
		this.perDateOfBirth = perDateOfBirth;
	}

	public Long getPerBirthCountryId() {
		return perBirthCountryId;
	}

	public void setPerBirthCountryId(Long perBirthCountryId) {
		this.perBirthCountryId = perBirthCountryId;
	}

	public Long getPerNationality1CommonListId() {
		return perNationality1CommonListId;
	}

	public void setPerNationality1CommonListId(Long perNationality1CommonListId) {
		this.perNationality1CommonListId = perNationality1CommonListId;
	}

	public Long getPerNationality2CommonListId() {
		return perNationality2CommonListId;
	}

	public void setPerNationality2CommonListId(Long perNationality2CommonListId) {
		this.perNationality2CommonListId = perNationality2CommonListId;
	}

	public Long getPerNationality3CommonListId() {
		return perNationality3CommonListId;
	}

	public void setPerNationality3CommonListId(Long perNationality3CommonListId) {
		this.perNationality3CommonListId = perNationality3CommonListId;
	}

	public Long getPerNationality1CountryId() {
		return perNationality1CountryId;
	}

	public void setPerNationality1CountryId(Long perNationality1CountryId) {
		this.perNationality1CountryId = perNationality1CountryId;
	}

	public Long getPerNationality2CountryId() {
		return perNationality2CountryId;
	}

	public void setPerNationality2CountryId(Long perNationality2CountryId) {
		this.perNationality2CountryId = perNationality2CountryId;
	}

	public Long getPerNationality3CountryId() {
		return perNationality3CountryId;
	}

	public void setPerNationality3CountryId(Long perNationality3CountryId) {
		this.perNationality3CountryId = perNationality3CountryId;
	}

	public Long getPerMaritalStatusCommonListId() {
		return perMaritalStatusCommonListId;
	}

	public void setPerMaritalStatusCommonListId(Long perMaritalStatusCommonListId) {
		this.perMaritalStatusCommonListId = perMaritalStatusCommonListId;
	}

	public Long getPerResidentStatusCommonListId() {
		return perResidentStatusCommonListId;
	}

	public void setPerResidentStatusCommonListId(Long perResidentStatusCommonListId) {
		this.perResidentStatusCommonListId = perResidentStatusCommonListId;
	}

	public Long getPerResident1CountryId() {
		return perResident1CountryId;
	}

	public void setPerResident1CountryId(Long perResident1CountryId) {
		this.perResident1CountryId = perResident1CountryId;
	}

	public Long getPerResident2CountryId() {
		return perResident2CountryId;
	}

	public void setPerResident2CountryId(Long perResident2CountryId) {
		this.perResident2CountryId = perResident2CountryId;
	}

	public Long getPerResident3CountryId() {
		return perResident3CountryId;
	}

	public void setPerResident3CountryId(Long perResident3CountryId) {
		this.perResident3CountryId = perResident3CountryId;
	}

	public Long getPerEducationalLevelCommonListId() {
		return perEducationalLevelCommonListId;
	}

	public void setPerEducationalLevelCommonListId(Long perEducationalLevelCommonListId) {
		this.perEducationalLevelCommonListId = perEducationalLevelCommonListId;
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

	public Long getPerTypeOfCompanyCommonListId() {
		return perTypeOfCompanyCommonListId;
	}

	public void setPerTypeOfCompanyCommonListId(Long perTypeOfCompanyCommonListId) {
		this.perTypeOfCompanyCommonListId = perTypeOfCompanyCommonListId;
	}

	public Long getPerCorporateCategoryCommonListId() {
		return perCorporateCategoryCommonListId;
	}

	public void setPerCorporateCategoryCommonListId(Long perCorporateCategoryCommonListId) {
		this.perCorporateCategoryCommonListId = perCorporateCategoryCommonListId;
	}

	public Long getPerNatureOfBusinessCommonListId() {
		return perNatureOfBusinessCommonListId;
	}

	public void setPerNatureOfBusinessCommonListId(Long perNatureOfBusinessCommonListId) {
		this.perNatureOfBusinessCommonListId = perNatureOfBusinessCommonListId;
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

	public Long getPerRegisCountryId() {
		return perRegisCountryId;
	}

	public void setPerRegisCountryId(Long perRegisCountryId) {
		this.perRegisCountryId = perRegisCountryId;
	}

	public Long getPerRegistrationAuthorityCommonListId() {
		return perRegistrationAuthorityCommonListId;
	}

	public void setPerRegistrationAuthorityCommonListId(Long perRegistrationAuthorityCommonListId) {
		this.perRegistrationAuthorityCommonListId = perRegistrationAuthorityCommonListId;
	}

	public Long getPerPreferredLanguageId() {
		return perPreferredLanguageId;
	}

	public void setPerPreferredLanguageId(Long perPreferredLanguageId) {
		this.perPreferredLanguageId = perPreferredLanguageId;
	}

	public String getPerContactPerson() {
		return perContactPerson;
	}

	public void setPerContactPerson(String perContactPerson) {
		this.perContactPerson = perContactPerson;
	}

	public String getPerComment() {
		return perComment;
	}

	public void setPerComment(String perComment) {
		this.perComment = perComment;
	}

	public String getPerAttribute1() {
		return perAttribute1;
	}

	public void setPerAttribute1(String perAttribute1) {
		this.perAttribute1 = perAttribute1;
	}

	public String getPerAttribute2() {
		return perAttribute2;
	}

	public void setPerAttribute2(String perAttribute2) {
		this.perAttribute2 = perAttribute2;
	}

	public String getPerAttribute3() {
		return perAttribute3;
	}

	public void setPerAttribute3(String perAttribute3) {
		this.perAttribute3 = perAttribute3;
	}

	public String getPerAttribute4() {
		return perAttribute4;
	}

	public void setPerAttribute4(String perAttribute4) {
		this.perAttribute4 = perAttribute4;
	}

	public String getPerAttribute5() {
		return perAttribute5;
	}

	public void setPerAttribute5(String perAttribute5) {
		this.perAttribute5 = perAttribute5;
	}

	public String getPerAttribute6() {
		return perAttribute6;
	}

	public void setPerAttribute6(String perAttribute6) {
		this.perAttribute6 = perAttribute6;
	}

	public String getPerAttribute7() {
		return perAttribute7;
	}

	public void setPerAttribute7(String perAttribute7) {
		this.perAttribute7 = perAttribute7;
	}

	public String getPerAttribute8() {
		return perAttribute8;
	}

	public void setPerAttribute8(String perAttribute8) {
		this.perAttribute8 = perAttribute8;
	}

	public String getPerAttribute9() {
		return perAttribute9;
	}

	public void setPerAttribute9(String perAttribute9) {
		this.perAttribute9 = perAttribute9;
	}

	public String getPerAttribute10() {
		return perAttribute10;
	}

	public void setPerAttribute10(String perAttribute10) {
		this.perAttribute10 = perAttribute10;
	}

	public String getPerAttribute11() {
		return perAttribute11;
	}

	public void setPerAttribute11(String perAttribute11) {
		this.perAttribute11 = perAttribute11;
	}

	public String getPerAttribute12() {
		return perAttribute12;
	}

	public void setPerAttribute12(String perAttribute12) {
		this.perAttribute12 = perAttribute12;
	}

	public String getPerAttribute13() {
		return perAttribute13;
	}

	public void setPerAttribute13(String perAttribute13) {
		this.perAttribute13 = perAttribute13;
	}

	public String getPerAttribute14() {
		return perAttribute14;
	}

	public void setPerAttribute14(String perAttribute14) {
		this.perAttribute14 = perAttribute14;
	}

	public String getPerAttribute15() {
		return perAttribute15;
	}

	public void setPerAttribute15(String perAttribute15) {
		this.perAttribute15 = perAttribute15;
	}

	public String getPerAttribute16() {
		return perAttribute16;
	}

	public void setPerAttribute16(String perAttribute16) {
		this.perAttribute16 = perAttribute16;
	}

	public String getPerAttribute17() {
		return perAttribute17;
	}

	public void setPerAttribute17(String perAttribute17) {
		this.perAttribute17 = perAttribute17;
	}

	public String getPerAttribute18() {
		return perAttribute18;
	}

	public void setPerAttribute18(String perAttribute18) {
		this.perAttribute18 = perAttribute18;
	}

	public String getPerAttribute19() {
		return perAttribute19;
	}

	public void setPerAttribute19(String perAttribute19) {
		this.perAttribute19 = perAttribute19;
	}

	public String getPerAttribute20() {
		return perAttribute20;
	}

	public void setPerAttribute20(String perAttribute20) {
		this.perAttribute20 = perAttribute20;
	}

	public Long getPerKYCId() {
		return perKYCId;
	}

	public void setPerKYCId(Long perKYCId) {
		this.perKYCId = perKYCId;
	}

	public Long getPerRiskProfileId() {
		return perRiskProfileId;
	}

	public void setPerRiskProfileId(Long perRiskProfileId) {
		this.perRiskProfileId = perRiskProfileId;
	}

	public String getPerPepStatus() {
		return perPepStatus;
	}

	public void setPerPepStatus(String perPepStatus) {
		this.perPepStatus = perPepStatus;
	}

	public String getPerPepComment() {
		return perPepComment;
	}

	public void setPerPepComment(String perPepComment) {
		this.perPepComment = perPepComment;
	}

	public String getPerTitleDesc() {
		return perTitleDesc;
	}

	public void setPerTitleDesc(String perTitleDesc) {
		this.perTitleDesc = perTitleDesc;
	}

	public String getPerGenderDesc() {
		return perGenderDesc;
	}

	public void setPerGenderDesc(String perGenderDesc) {
		this.perGenderDesc = perGenderDesc;
	}

	public String getPerBirthCountryDesc() {
		return perBirthCountryDesc;
	}

	public void setPerBirthCountryDesc(String perBirthCountryDesc) {
		this.perBirthCountryDesc = perBirthCountryDesc;
	}

	public String getPerNationality1Desc() {
		return perNationality1Desc;
	}

	public void setPerNationality1Desc(String perNationality1Desc) {
		this.perNationality1Desc = perNationality1Desc;
	}

	public String getPerNationality2Desc() {
		return perNationality2Desc;
	}

	public void setPerNationality2Desc(String perNationality2Desc) {
		this.perNationality2Desc = perNationality2Desc;
	}

	public String getPerNationality3Desc() {
		return perNationality3Desc;
	}

	public void setPerNationality3Desc(String perNationality3Desc) {
		this.perNationality3Desc = perNationality3Desc;
	}

	public String getPerNationality1CountryDesc() {
		return perNationality1CountryDesc;
	}

	public void setPerNationality1CountryDesc(String perNationality1CountryDesc) {
		this.perNationality1CountryDesc = perNationality1CountryDesc;
	}

	public String getPerNationality2CountryDesc() {
		return perNationality2CountryDesc;
	}

	public void setPerNationality2CountryDesc(String perNationality2CountryDesc) {
		this.perNationality2CountryDesc = perNationality2CountryDesc;
	}

	public String getPerNationality3CountryDesc() {
		return perNationality3CountryDesc;
	}

	public void setPerNationality3CountryDesc(String perNationality3CountryDesc) {
		this.perNationality3CountryDesc = perNationality3CountryDesc;
	}

	public String getPerMaritalStatusDesc() {
		return perMaritalStatusDesc;
	}

	public void setPerMaritalStatusDesc(String perMaritalStatusDesc) {
		this.perMaritalStatusDesc = perMaritalStatusDesc;
	}

	public String getPerResidentStatusDesc() {
		return perResidentStatusDesc;
	}

	public void setPerResidentStatusDesc(String perResidentStatusDesc) {
		this.perResidentStatusDesc = perResidentStatusDesc;
	}

	public String getPerResident1CountryDesc() {
		return perResident1CountryDesc;
	}

	public void setPerResident1CountryDesc(String perResident1CountryDesc) {
		this.perResident1CountryDesc = perResident1CountryDesc;
	}

	public String getPerResident2CountryDesc() {
		return perResident2CountryDesc;
	}

	public void setPerResident2CountryDesc(String perResident2CountryDesc) {
		this.perResident2CountryDesc = perResident2CountryDesc;
	}

	public String getPerResident3CountryDesc() {
		return perResident3CountryDesc;
	}

	public void setPerResident3CountryDesc(String perResident3CountryDesc) {
		this.perResident3CountryDesc = perResident3CountryDesc;
	}

	public String getPerEducationalLevelDesc() {
		return perEducationalLevelDesc;
	}

	public void setPerEducationalLevelDesc(String perEducationalLevelDesc) {
		this.perEducationalLevelDesc = perEducationalLevelDesc;
	}

	public String getPerTypeOfCompanyDesc() {
		return perTypeOfCompanyDesc;
	}

	public void setPerTypeOfCompanyDesc(String perTypeOfCompanyDesc) {
		this.perTypeOfCompanyDesc = perTypeOfCompanyDesc;
	}

	public String getPerCorporateCategoryDesc() {
		return perCorporateCategoryDesc;
	}

	public void setPerCorporateCategoryDesc(String perCorporateCategoryDesc) {
		this.perCorporateCategoryDesc = perCorporateCategoryDesc;
	}

	public String getPerNatureOfBusinessDesc() {
		return perNatureOfBusinessDesc;
	}

	public void setPerNatureOfBusinessDesc(String perNatureOfBusinessDesc) {
		this.perNatureOfBusinessDesc = perNatureOfBusinessDesc;
	}

	public String getPerRegisCountryDesc() {
		return perRegisCountryDesc;
	}

	public void setPerRegisCountryDesc(String perRegisCountryDesc) {
		this.perRegisCountryDesc = perRegisCountryDesc;
	}

	public String getPerRegistrationAuthorityDesc() {
		return perRegistrationAuthorityDesc;
	}

	public void setPerRegistrationAuthorityDesc(String perRegistrationAuthorityDesc) {
		this.perRegistrationAuthorityDesc = perRegistrationAuthorityDesc;
	}

	public String getPerPreferredLanguageDesc() {
		return perPreferredLanguageDesc;
	}

	public void setPerPreferredLanguageDesc(String perPreferredLanguageDesc) {
		this.perPreferredLanguageDesc = perPreferredLanguageDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPerResidentStatusCode() {
		return perResidentStatusCode;
	}

	public void setPerResidentStatusCode(String perResidentStatusCode) {
		this.perResidentStatusCode = perResidentStatusCode;
	}

	public String getPerCorporateCategoryCode() {
		return perCorporateCategoryCode;
	}

	public void setPerCorporateCategoryCode(String perCorporateCategoryCode) {
		this.perCorporateCategoryCode = perCorporateCategoryCode;
	}
}
