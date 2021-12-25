package com.fusionx.lending.origination.resource;

import java.util.Date;
import java.util.List;

public class ComnCustomerResponceResource {
	
	private Long cusMainGroupId;
	
	private String cusSubGroupCode;
	
	private String cusPersonTypeDesc;
	
	private String cusPersonTypeCode;
	
	private String perTitleDesc;

	private String perFirstName;

	private String perMiddleName;

	private String perLastName;

	private String perFullName;

	private String perInitials;

	private String perPreferredName;
	
	private Long perPreferredLanguageId;
	
	private String perPreferredLanguageDesc;
	
	private Long cusOriginationMethodCommonListId;
	
	private String cusOriginationMethodDesc;
	
	private String cusMajorOccupationCode;
	
	private String cusSubOccupationCode;
	
	private Long perMaritalStatusCommonListId;
	
	private String perMaritalStatusDesc;
	
	private Long perGenderCommonListId;
	
	private String perGenderDesc;
	
	private Date perDateOfBirth;
	
	private String cusSmsAlertStatus;
	
	private Long cusAreaId;
	
	private String cusAreaDesc;
	
	private String perBusinessRegNo;
	
	private String perBusinessRegNoOld;
	
	private Long cusNearestBranchId;
	
	private String cusNearestBranchDesc;
	
	private List<PersonResponseIdentificationResource> perIdentifications;
	
	private List<PersonResponseContactResource> perContacts;
	
	private List<PersonResponseAddressResource> perAddresses;

	public Long getCusMainGroupId() {
		return cusMainGroupId;
	}

	public void setCusMainGroupId(Long cusMainGroupId) {
		this.cusMainGroupId = cusMainGroupId;
	}

	public String getCusSubGroupCode() {
		return cusSubGroupCode;
	}

	public void setCusSubGroupCode(String cusSubGroupCode) {
		this.cusSubGroupCode = cusSubGroupCode;
	}

	public String getCusPersonTypeDesc() {
		return cusPersonTypeDesc;
	}

	public void setCusPersonTypeDesc(String cusPersonTypeDesc) {
		this.cusPersonTypeDesc = cusPersonTypeDesc;
	}

	public String getCusPersonTypeCode() {
		return cusPersonTypeCode;
	}

	public void setCusPersonTypeCode(String cusPersonTypeCode) {
		this.cusPersonTypeCode = cusPersonTypeCode;
	}

	public String getPerTitleDesc() {
		return perTitleDesc;
	}

	public void setPerTitleDesc(String perTitleDesc) {
		this.perTitleDesc = perTitleDesc;
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

	public Long getPerPreferredLanguageId() {
		return perPreferredLanguageId;
	}

	public void setPerPreferredLanguageId(Long perPreferredLanguageId) {
		this.perPreferredLanguageId = perPreferredLanguageId;
	}

	public String getPerPreferredLanguageDesc() {
		return perPreferredLanguageDesc;
	}

	public void setPerPreferredLanguageDesc(String perPreferredLanguageDesc) {
		this.perPreferredLanguageDesc = perPreferredLanguageDesc;
	}

	public Long getCusOriginationMethodCommonListId() {
		return cusOriginationMethodCommonListId;
	}

	public void setCusOriginationMethodCommonListId(Long cusOriginationMethodCommonListId) {
		this.cusOriginationMethodCommonListId = cusOriginationMethodCommonListId;
	}

	public String getCusOriginationMethodDesc() {
		return cusOriginationMethodDesc;
	}

	public void setCusOriginationMethodDesc(String cusOriginationMethodDesc) {
		this.cusOriginationMethodDesc = cusOriginationMethodDesc;
	}

	public String getCusMajorOccupationCode() {
		return cusMajorOccupationCode;
	}

	public void setCusMajorOccupationCode(String cusMajorOccupationCode) {
		this.cusMajorOccupationCode = cusMajorOccupationCode;
	}

	public String getCusSubOccupationCode() {
		return cusSubOccupationCode;
	}

	public void setCusSubOccupationCode(String cusSubOccupationCode) {
		this.cusSubOccupationCode = cusSubOccupationCode;
	}

	public Long getPerMaritalStatusCommonListId() {
		return perMaritalStatusCommonListId;
	}

	public void setPerMaritalStatusCommonListId(Long perMaritalStatusCommonListId) {
		this.perMaritalStatusCommonListId = perMaritalStatusCommonListId;
	}

	public String getPerMaritalStatusDesc() {
		return perMaritalStatusDesc;
	}

	public void setPerMaritalStatusDesc(String perMaritalStatusDesc) {
		this.perMaritalStatusDesc = perMaritalStatusDesc;
	}

	public Long getPerGenderCommonListId() {
		return perGenderCommonListId;
	}

	public void setPerGenderCommonListId(Long perGenderCommonListId) {
		this.perGenderCommonListId = perGenderCommonListId;
	}

	public String getPerGenderDesc() {
		return perGenderDesc;
	}

	public void setPerGenderDesc(String perGenderDesc) {
		this.perGenderDesc = perGenderDesc;
	}

	public Date getPerDateOfBirth() {
		return perDateOfBirth;
	}

	public void setPerDateOfBirth(Date perDateOfBirth) {
		this.perDateOfBirth = perDateOfBirth;
	}

	public String getCusSmsAlertStatus() {
		return cusSmsAlertStatus;
	}

	public void setCusSmsAlertStatus(String cusSmsAlertStatus) {
		this.cusSmsAlertStatus = cusSmsAlertStatus;
	}

	public Long getCusAreaId() {
		return cusAreaId;
	}

	public void setCusAreaId(Long cusAreaId) {
		this.cusAreaId = cusAreaId;
	}

	public String getCusAreaDesc() {
		return cusAreaDesc;
	}

	public void setCusAreaDesc(String cusAreaDesc) {
		this.cusAreaDesc = cusAreaDesc;
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

	public Long getCusNearestBranchId() {
		return cusNearestBranchId;
	}

	public void setCusNearestBranchId(Long cusNearestBranchId) {
		this.cusNearestBranchId = cusNearestBranchId;
	}

	public String getCusNearestBranchDesc() {
		return cusNearestBranchDesc;
	}

	public void setCusNearestBranchDesc(String cusNearestBranchDesc) {
		this.cusNearestBranchDesc = cusNearestBranchDesc;
	}

	public List<PersonResponseIdentificationResource> getPerIdentifications() {
		return perIdentifications;
	}

	public void setPerIdentifications(List<PersonResponseIdentificationResource> perIdentifications) {
		this.perIdentifications = perIdentifications;
	}

	public List<PersonResponseContactResource> getPerContacts() {
		return perContacts;
	}

	public void setPerContacts(List<PersonResponseContactResource> perContacts) {
		this.perContacts = perContacts;
	}

	public List<PersonResponseAddressResource> getPerAddresses() {
		return perAddresses;
	}

	public void setPerAddresses(List<PersonResponseAddressResource> perAddresses) {
		this.perAddresses = perAddresses;
	}
}
