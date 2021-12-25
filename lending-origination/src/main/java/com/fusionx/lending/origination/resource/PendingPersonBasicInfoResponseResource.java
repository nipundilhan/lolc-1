package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingPersonBasicInfoResponseResource {

	private Long pperId;
	
	private Long perId;
	
	private String pperCode;
	
	private Long pperTitleCommonListId;
	
	private String pperTitleDesc;

	private String pperFirstName;

	private String pperMiddleName;

	private String pperLastName;

	private String pperFullName;

	private String pperInitials;

	private String pperPreferredName;

	private String pperOtherName;

	private Long pperGenderCommonListId;
	
	private String pperGenderDesc;

	private String pperBirthPlace;

	private Date pperDateOfBirth;

	private Long pperBirthCountryId;
	
	private String pperBirthCountryDesc;

	private Long pperNationality1CommonListId;
	
	private String pperNationality1Desc;
	
	private Long pperNationality2CommonListId;
	
	private String pperNationality2Desc;
	
	private Long pperNationality3CommonListId;
	
	private String pperNationality3Desc;
	
	private Long pperNationality1CountryId;
	
	private String pperNationality1CountryDesc;
	
	private Long pperNationality2CountryId;
	
	private String pperNationality2CountryDesc;
	
	private Long pperNationality3CountryId;
	
	private String pperNationality3CountryDesc;

	private Long pperMaritalStatusCommonListId;
	
	private String pperMaritalStatusDesc;

	private Long pperResidentStatusCommonListId;
	
	private String pperResidentStatusDesc;
	
	private String pperResidentStatusCode;
	
	private Long pperResident1CountryId;
	
	private String pperResident1CountryDesc;
	
	private Long pperResident2CountryId;
	
	private String pperResident2CountryDesc;
	
	private Long pperResident3CountryId;
	
	private String pperResident3CountryDesc;

	private Long pperEducationalLevelCommonListId;
	
	private String pperEducationalLevelDesc;
	
	private String pperCompanyName;

	private String pperGroupCompanyName;

	private Long pperTypeOfCompanyCommonListId;
	
	private String pperTypeOfCompanyDesc;
	
	private Long pperCorporateCategoryCommonListId;
	
	private String pperCorporateCategoryDesc;
	
	private String pperCorporateCategoryCode;
	
	private Long pperNatureOfBusinessCommonListId;
	
	private String pperNatureOfBusinessDesc;

	private String pperBusinessRegNo;

	private String pperTaxNo;
	
	private Long pperRegisCountryId;
	
	private String pperRegisCountryDesc;
	
	private Long pperRegistrationAuthorityCommonListId;
	
	private String pperRegistrationAuthorityDesc;

	private Long pperPreferredLanguageId;
	
	private String pperPreferredLanguageDesc;

	private String pperContactPerson;
	
	private String pperComment;
	
	private String pperAttribute1;
	
	private String pperAttribute2;
	
	private String pperAttribute3;
	
	private String pperAttribute4;
	
	private String pperAttribute5;
	
	private String pperAttribute6;
	
	private String pperAttribute7;
	
	private String pperAttribute8;
	
	private String pperAttribute9;
	
	private String pperAttribute10;
	
	private String pperAttribute11;
	
	private String pperAttribute12;
	
	private String pperAttribute13;
	
	private String pperAttribute14;
	
	private String pperAttribute15;
	
	private String pperAttribute16;
	
	private String pperAttribute17;
	
	private String pperAttribute18;
	
	private String pperAttribute19;
	
	private String pperAttribute20;
	
	private Long pperKYCId;
	
	private Long perRiskProfileId;
	
	private String pperPepStatus;
	
	private String pperPepComment;

	public Long getPperId() {
		return pperId;
	}

	public void setPperId(Long pperId) {
		this.pperId = pperId;
	}

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

	public String getPperCode() {
		return pperCode;
	}

	public void setPperCode(String pperCode) {
		this.pperCode = pperCode;
	}

	public Long getPperTitleCommonListId() {
		return pperTitleCommonListId;
	}

	public void setPperTitleCommonListId(Long pperTitleCommonListId) {
		this.pperTitleCommonListId = pperTitleCommonListId;
	}

	public String getPperFirstName() {
		return pperFirstName;
	}

	public void setPperFirstName(String pperFirstName) {
		this.pperFirstName = pperFirstName;
	}

	public String getPperMiddleName() {
		return pperMiddleName;
	}

	public void setPperMiddleName(String pperMiddleName) {
		this.pperMiddleName = pperMiddleName;
	}

	public String getPperLastName() {
		return pperLastName;
	}

	public void setPperLastName(String pperLastName) {
		this.pperLastName = pperLastName;
	}

	public String getPperFullName() {
		return pperFullName;
	}

	public void setPperFullName(String pperFullName) {
		this.pperFullName = pperFullName;
	}

	public String getPperInitials() {
		return pperInitials;
	}

	public void setPperInitials(String pperInitials) {
		this.pperInitials = pperInitials;
	}

	public String getPperPreferredName() {
		return pperPreferredName;
	}

	public void setPperPreferredName(String pperPreferredName) {
		this.pperPreferredName = pperPreferredName;
	}

	public String getPperOtherName() {
		return pperOtherName;
	}

	public void setPperOtherName(String pperOtherName) {
		this.pperOtherName = pperOtherName;
	}

	public Long getPperGenderCommonListId() {
		return pperGenderCommonListId;
	}

	public void setPperGenderCommonListId(Long pperGenderCommonListId) {
		this.pperGenderCommonListId = pperGenderCommonListId;
	}

	public String getPperBirthPlace() {
		return pperBirthPlace;
	}

	public void setPperBirthPlace(String pperBirthPlace) {
		this.pperBirthPlace = pperBirthPlace;
	}

	public Date getPperDateOfBirth() {
		return pperDateOfBirth;
	}

	public void setPperDateOfBirth(Date pperDateOfBirth) {
		this.pperDateOfBirth = pperDateOfBirth;
	}

	public Long getPperBirthCountryId() {
		return pperBirthCountryId;
	}

	public void setPperBirthCountryId(Long pperBirthCountryId) {
		this.pperBirthCountryId = pperBirthCountryId;
	}

	public Long getPperNationality1CommonListId() {
		return pperNationality1CommonListId;
	}

	public void setPperNationality1CommonListId(Long pperNationality1CommonListId) {
		this.pperNationality1CommonListId = pperNationality1CommonListId;
	}

	public Long getPperNationality2CommonListId() {
		return pperNationality2CommonListId;
	}

	public void setPperNationality2CommonListId(Long pperNationality2CommonListId) {
		this.pperNationality2CommonListId = pperNationality2CommonListId;
	}

	public Long getPperNationality3CommonListId() {
		return pperNationality3CommonListId;
	}

	public void setPperNationality3CommonListId(Long pperNationality3CommonListId) {
		this.pperNationality3CommonListId = pperNationality3CommonListId;
	}

	public Long getPperNationality1CountryId() {
		return pperNationality1CountryId;
	}

	public void setPperNationality1CountryId(Long pperNationality1CountryId) {
		this.pperNationality1CountryId = pperNationality1CountryId;
	}

	public Long getPperNationality2CountryId() {
		return pperNationality2CountryId;
	}

	public void setPperNationality2CountryId(Long pperNationality2CountryId) {
		this.pperNationality2CountryId = pperNationality2CountryId;
	}

	public Long getPperNationality3CountryId() {
		return pperNationality3CountryId;
	}

	public void setPperNationality3CountryId(Long pperNationality3CountryId) {
		this.pperNationality3CountryId = pperNationality3CountryId;
	}

	public Long getPperMaritalStatusCommonListId() {
		return pperMaritalStatusCommonListId;
	}

	public void setPperMaritalStatusCommonListId(Long pperMaritalStatusCommonListId) {
		this.pperMaritalStatusCommonListId = pperMaritalStatusCommonListId;
	}

	public Long getPperResidentStatusCommonListId() {
		return pperResidentStatusCommonListId;
	}

	public void setPperResidentStatusCommonListId(Long pperResidentStatusCommonListId) {
		this.pperResidentStatusCommonListId = pperResidentStatusCommonListId;
	}

	public Long getPperResident1CountryId() {
		return pperResident1CountryId;
	}

	public void setPperResident1CountryId(Long pperResident1CountryId) {
		this.pperResident1CountryId = pperResident1CountryId;
	}

	public Long getPperResident2CountryId() {
		return pperResident2CountryId;
	}

	public void setPperResident2CountryId(Long pperResident2CountryId) {
		this.pperResident2CountryId = pperResident2CountryId;
	}

	public Long getPperResident3CountryId() {
		return pperResident3CountryId;
	}

	public void setPperResident3CountryId(Long pperResident3CountryId) {
		this.pperResident3CountryId = pperResident3CountryId;
	}

	public Long getPperEducationalLevelCommonListId() {
		return pperEducationalLevelCommonListId;
	}

	public void setPperEducationalLevelCommonListId(Long pperEducationalLevelCommonListId) {
		this.pperEducationalLevelCommonListId = pperEducationalLevelCommonListId;
	}

	public String getPperCompanyName() {
		return pperCompanyName;
	}

	public void setPperCompanyName(String pperCompanyName) {
		this.pperCompanyName = pperCompanyName;
	}

	public String getPperGroupCompanyName() {
		return pperGroupCompanyName;
	}

	public void setPperGroupCompanyName(String pperGroupCompanyName) {
		this.pperGroupCompanyName = pperGroupCompanyName;
	}

	public Long getPperTypeOfCompanyCommonListId() {
		return pperTypeOfCompanyCommonListId;
	}

	public void setPperTypeOfCompanyCommonListId(Long pperTypeOfCompanyCommonListId) {
		this.pperTypeOfCompanyCommonListId = pperTypeOfCompanyCommonListId;
	}

	public Long getPperCorporateCategoryCommonListId() {
		return pperCorporateCategoryCommonListId;
	}

	public void setPperCorporateCategoryCommonListId(Long pperCorporateCategoryCommonListId) {
		this.pperCorporateCategoryCommonListId = pperCorporateCategoryCommonListId;
	}

	public Long getPperNatureOfBusinessCommonListId() {
		return pperNatureOfBusinessCommonListId;
	}

	public void setPperNatureOfBusinessCommonListId(Long pperNatureOfBusinessCommonListId) {
		this.pperNatureOfBusinessCommonListId = pperNatureOfBusinessCommonListId;
	}

	public String getPperBusinessRegNo() {
		return pperBusinessRegNo;
	}

	public void setPperBusinessRegNo(String pperBusinessRegNo) {
		this.pperBusinessRegNo = pperBusinessRegNo;
	}

	public String getPperTaxNo() {
		return pperTaxNo;
	}

	public void setPperTaxNo(String pperTaxNo) {
		this.pperTaxNo = pperTaxNo;
	}

	public Long getPperRegistrationAuthorityCommonListId() {
		return pperRegistrationAuthorityCommonListId;
	}

	public void setPperRegistrationAuthorityCommonListId(Long pperRegistrationAuthorityCommonListId) {
		this.pperRegistrationAuthorityCommonListId = pperRegistrationAuthorityCommonListId;
	}

	public Long getPperPreferredLanguageId() {
		return pperPreferredLanguageId;
	}

	public void setPperPreferredLanguageId(Long pperPreferredLanguageId) {
		this.pperPreferredLanguageId = pperPreferredLanguageId;
	}

	public String getPperContactPerson() {
		return pperContactPerson;
	}

	public void setPperContactPerson(String pperContactPerson) {
		this.pperContactPerson = pperContactPerson;
	}

	public String getPperComment() {
		return pperComment;
	}

	public void setPperComment(String pperComment) {
		this.pperComment = pperComment;
	}

	public String getPperAttribute1() {
		return pperAttribute1;
	}

	public void setPperAttribute1(String pperAttribute1) {
		this.pperAttribute1 = pperAttribute1;
	}

	public String getPperAttribute2() {
		return pperAttribute2;
	}

	public void setPperAttribute2(String pperAttribute2) {
		this.pperAttribute2 = pperAttribute2;
	}

	public String getPperAttribute3() {
		return pperAttribute3;
	}

	public void setPperAttribute3(String pperAttribute3) {
		this.pperAttribute3 = pperAttribute3;
	}

	public String getPperAttribute4() {
		return pperAttribute4;
	}

	public void setPperAttribute4(String pperAttribute4) {
		this.pperAttribute4 = pperAttribute4;
	}

	public String getPperAttribute5() {
		return pperAttribute5;
	}

	public void setPperAttribute5(String pperAttribute5) {
		this.pperAttribute5 = pperAttribute5;
	}

	public String getPperAttribute6() {
		return pperAttribute6;
	}

	public void setPperAttribute6(String pperAttribute6) {
		this.pperAttribute6 = pperAttribute6;
	}

	public String getPperAttribute7() {
		return pperAttribute7;
	}

	public void setPperAttribute7(String pperAttribute7) {
		this.pperAttribute7 = pperAttribute7;
	}

	public String getPperAttribute8() {
		return pperAttribute8;
	}

	public void setPperAttribute8(String pperAttribute8) {
		this.pperAttribute8 = pperAttribute8;
	}

	public String getPperAttribute9() {
		return pperAttribute9;
	}

	public void setPperAttribute9(String pperAttribute9) {
		this.pperAttribute9 = pperAttribute9;
	}

	public String getPperAttribute10() {
		return pperAttribute10;
	}

	public void setPperAttribute10(String pperAttribute10) {
		this.pperAttribute10 = pperAttribute10;
	}

	public String getPperAttribute11() {
		return pperAttribute11;
	}

	public void setPperAttribute11(String pperAttribute11) {
		this.pperAttribute11 = pperAttribute11;
	}

	public String getPperAttribute12() {
		return pperAttribute12;
	}

	public void setPperAttribute12(String pperAttribute12) {
		this.pperAttribute12 = pperAttribute12;
	}

	public String getPperAttribute13() {
		return pperAttribute13;
	}

	public void setPperAttribute13(String pperAttribute13) {
		this.pperAttribute13 = pperAttribute13;
	}

	public String getPperAttribute14() {
		return pperAttribute14;
	}

	public void setPperAttribute14(String pperAttribute14) {
		this.pperAttribute14 = pperAttribute14;
	}

	public String getPperAttribute15() {
		return pperAttribute15;
	}

	public void setPperAttribute15(String pperAttribute15) {
		this.pperAttribute15 = pperAttribute15;
	}

	public String getPperAttribute16() {
		return pperAttribute16;
	}

	public void setPperAttribute16(String pperAttribute16) {
		this.pperAttribute16 = pperAttribute16;
	}

	public String getPperAttribute17() {
		return pperAttribute17;
	}

	public void setPperAttribute17(String pperAttribute17) {
		this.pperAttribute17 = pperAttribute17;
	}

	public String getPperAttribute18() {
		return pperAttribute18;
	}

	public void setPperAttribute18(String pperAttribute18) {
		this.pperAttribute18 = pperAttribute18;
	}

	public String getPperAttribute19() {
		return pperAttribute19;
	}

	public void setPperAttribute19(String pperAttribute19) {
		this.pperAttribute19 = pperAttribute19;
	}

	public String getPperAttribute20() {
		return pperAttribute20;
	}

	public void setPperAttribute20(String pperAttribute20) {
		this.pperAttribute20 = pperAttribute20;
	}

	public Long getPperKYCId() {
		return pperKYCId;
	}

	public void setPperKYCId(Long pperKYCId) {
		this.pperKYCId = pperKYCId;
	}

	public Long getPerRiskProfileId() {
		return perRiskProfileId;
	}

	public void setPerRiskProfileId(Long perRiskProfileId) {
		this.perRiskProfileId = perRiskProfileId;
	}

	public String getPperPepStatus() {
		return pperPepStatus;
	}

	public void setPperPepStatus(String pperPepStatus) {
		this.pperPepStatus = pperPepStatus;
	}

	public String getPperPepComment() {
		return pperPepComment;
	}

	public void setPperPepComment(String pperPepComment) {
		this.pperPepComment = pperPepComment;
	}

	public String getPperTitleDesc() {
		return pperTitleDesc;
	}

	public void setPperTitleDesc(String pperTitleDesc) {
		this.pperTitleDesc = pperTitleDesc;
	}

	public String getPperGenderDesc() {
		return pperGenderDesc;
	}

	public void setPperGenderDesc(String pperGenderDesc) {
		this.pperGenderDesc = pperGenderDesc;
	}

	public String getPperBirthCountryDesc() {
		return pperBirthCountryDesc;
	}

	public void setPperBirthCountryDesc(String pperBirthCountryDesc) {
		this.pperBirthCountryDesc = pperBirthCountryDesc;
	}

	public String getPperNationality1Desc() {
		return pperNationality1Desc;
	}

	public void setPperNationality1Desc(String pperNationality1Desc) {
		this.pperNationality1Desc = pperNationality1Desc;
	}

	public String getPperNationality2Desc() {
		return pperNationality2Desc;
	}

	public void setPperNationality2Desc(String pperNationality2Desc) {
		this.pperNationality2Desc = pperNationality2Desc;
	}

	public String getPperNationality3Desc() {
		return pperNationality3Desc;
	}

	public void setPperNationality3Desc(String pperNationality3Desc) {
		this.pperNationality3Desc = pperNationality3Desc;
	}

	public String getPperNationality1CountryDesc() {
		return pperNationality1CountryDesc;
	}

	public void setPperNationality1CountryDesc(String pperNationality1CountryDesc) {
		this.pperNationality1CountryDesc = pperNationality1CountryDesc;
	}

	public String getPperNationality2CountryDesc() {
		return pperNationality2CountryDesc;
	}

	public void setPperNationality2CountryDesc(String pperNationality2CountryDesc) {
		this.pperNationality2CountryDesc = pperNationality2CountryDesc;
	}

	public String getPperNationality3CountryDesc() {
		return pperNationality3CountryDesc;
	}

	public void setPperNationality3CountryDesc(String pperNationality3CountryDesc) {
		this.pperNationality3CountryDesc = pperNationality3CountryDesc;
	}

	public String getPperMaritalStatusDesc() {
		return pperMaritalStatusDesc;
	}

	public void setPperMaritalStatusDesc(String pperMaritalStatusDesc) {
		this.pperMaritalStatusDesc = pperMaritalStatusDesc;
	}

	public String getPperResidentStatusDesc() {
		return pperResidentStatusDesc;
	}

	public void setPperResidentStatusDesc(String pperResidentStatusDesc) {
		this.pperResidentStatusDesc = pperResidentStatusDesc;
	}

	public String getPperResident1CountryDesc() {
		return pperResident1CountryDesc;
	}

	public void setPperResident1CountryDesc(String pperResident1CountryDesc) {
		this.pperResident1CountryDesc = pperResident1CountryDesc;
	}

	public String getPperResident2CountryDesc() {
		return pperResident2CountryDesc;
	}

	public void setPperResident2CountryDesc(String pperResident2CountryDesc) {
		this.pperResident2CountryDesc = pperResident2CountryDesc;
	}

	public String getPperResident3CountryDesc() {
		return pperResident3CountryDesc;
	}

	public void setPperResident3CountryDesc(String pperResident3CountryDesc) {
		this.pperResident3CountryDesc = pperResident3CountryDesc;
	}

	public String getPperEducationalLevelDesc() {
		return pperEducationalLevelDesc;
	}

	public void setPperEducationalLevelDesc(String pperEducationalLevelDesc) {
		this.pperEducationalLevelDesc = pperEducationalLevelDesc;
	}

	public String getPperTypeOfCompanyDesc() {
		return pperTypeOfCompanyDesc;
	}

	public void setPperTypeOfCompanyDesc(String pperTypeOfCompanyDesc) {
		this.pperTypeOfCompanyDesc = pperTypeOfCompanyDesc;
	}

	public String getPperCorporateCategoryDesc() {
		return pperCorporateCategoryDesc;
	}

	public void setPperCorporateCategoryDesc(String pperCorporateCategoryDesc) {
		this.pperCorporateCategoryDesc = pperCorporateCategoryDesc;
	}

	public String getPperNatureOfBusinessDesc() {
		return pperNatureOfBusinessDesc;
	}

	public void setPperNatureOfBusinessDesc(String pperNatureOfBusinessDesc) {
		this.pperNatureOfBusinessDesc = pperNatureOfBusinessDesc;
	}

	public String getPperRegistrationAuthorityDesc() {
		return pperRegistrationAuthorityDesc;
	}

	public void setPperRegistrationAuthorityDesc(String pperRegistrationAuthorityDesc) {
		this.pperRegistrationAuthorityDesc = pperRegistrationAuthorityDesc;
	}

	public String getPperPreferredLanguageDesc() {
		return pperPreferredLanguageDesc;
	}

	public void setPperPreferredLanguageDesc(String pperPreferredLanguageDesc) {
		this.pperPreferredLanguageDesc = pperPreferredLanguageDesc;
	}

	public String getPperResidentStatusCode() {
		return pperResidentStatusCode;
	}

	public void setPperResidentStatusCode(String pperResidentStatusCode) {
		this.pperResidentStatusCode = pperResidentStatusCode;
	}

	public String getPperCorporateCategoryCode() {
		return pperCorporateCategoryCode;
	}

	public void setPperCorporateCategoryCode(String pperCorporateCategoryCode) {
		this.pperCorporateCategoryCode = pperCorporateCategoryCode;
	}

	public Long getPperRegisCountryId() {
		return pperRegisCountryId;
	}

	public void setPperRegisCountryId(Long pperRegisCountryId) {
		this.pperRegisCountryId = pperRegisCountryId;
	}

	public String getPperRegisCountryDesc() {
		return pperRegisCountryDesc;
	}

	public void setPperRegisCountryDesc(String pperRegisCountryDesc) {
		this.pperRegisCountryDesc = pperRegisCountryDesc;
	}
}
