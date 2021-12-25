package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerRequestResource extends PersonRequestResource{

	@JsonProperty("cusReferenceCode")
	@NotBlank(message = "{cusReferenceCode.not-null}")
	@Size(max = 15, message = "{cusReferenceCode.size}")
	private String cusReferenceCode;
	
	@JsonProperty("cusOrganizationTypeCommonListId")
	@NotBlank(message = "{cusOrganizationTypeCommonListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusOrganizationTypeCommonListId.pattern}")
	private String cusOrganizationTypeCommonListId;
	private String cusOrganizationTypeDesc;
	
	@JsonProperty("cusOriginationMethodCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusOriginationMethodCommonListId.pattern}")
	private String cusOriginationMethodCommonListId;
	private String cusOriginationMethodDesc;
	
	@JsonProperty("cusPreferredDay")
	@Pattern(regexp = "^$|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY", message = "{cusPreferredDay.pattern}")
	private String cusPreferredDay;
	
	@JsonProperty("cusPreferredTime")
	@Pattern(regexp = "^$|(?:[01]\\d|2[0123]):(?:[012345]\\d)", message = "{cusPreferredTime.pattern}")
	private String cusPreferredTime;
	
	@JsonProperty("cusNearestBranchId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusNearestBranchId.pattern}")
	private String cusNearestBranchId;
	private String cusNearestBranchDesc;
	
	@JsonProperty("cusLocationLongitude")
	@Pattern(regexp = "^$|^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$",message="{cusLocationLongitude.pattern}")
	private String cusLocationLongitude;
	
	@JsonProperty("cusLocationLatitude")
	@Pattern(regexp = "^$|^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$",message="{cusLocationLatitude.pattern}")
	private String cusLocationLatitude;
	
	@JsonProperty("cusProductDetailId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusProductDetailId.pattern}")
	private String cusProductDetailId;
	private String cusProductDetailDesc;
	
	@JsonProperty("cusWithinBranchArea")
	@Pattern(regexp = "^$|YES|NO", message = "{cusWithinBranchArea.pattern}")
	private String cusWithinBranchArea;
	
	@JsonProperty("cusComment")
	@Size(max = 255, message = "{cusComment.size}")
	private String cusComment;
	
	@JsonProperty("cusSectorId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusSectorId.pattern}")
	private String cusSectorId;
	private String cusSectorDesc;
	
	@JsonProperty("cusSubSectorId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusSubSectorId.pattern}")
	private String cusSubSectorId;
	private String cusSubSectorDesc;
	
	@JsonProperty("cusNotesOnBusiness")
	@Size(max = 255, message = "{cusNotesOnBusiness.size}")
	private String cusNotesOnBusiness;
	
	@JsonProperty("cusIndividualAnnualTurnoverId")
	@Pattern(regexp = "^$|[0-9]+", message = "{turnoverId.pattern}")
	private String cusIndividualAnnualTurnoverId;
	private String cusIndividualAnnualTurnoverDesc;
	
	@JsonProperty("cusBusinessAnnualTurnoverId")
	@Pattern(regexp = "^$|[0-9]+", message = "{turnoverId.pattern}")
	private String cusBusinessAnnualTurnoverId;
	private String cusBusinessAnnualTurnoverDesc;
	
	@JsonProperty("cusRelatedPartyStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{cusRelatedPartyStatus.pattern}")
	private String cusRelatedPartyStatus;
	
	@JsonProperty("cusRelatedPartyTypeCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusRelatedPartyTypeCommonListId.pattern}")
	private String cusRelatedPartyTypeCommonListId;
	private String cusRelatedPartyTypeDesc;
	
	@Size(max = 255, message = "{cusComment.size}")
	private String cusRelatedPartyComment;
	
	@JsonProperty("cusKMPStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{cusKMPStatus.pattern}")
	private String cusKMPStatus;
	
	@JsonProperty("cusMainGroupId")
	@Pattern(regexp = "^$|[0-9]+", message = "{groupId.pattern}")
	private String cusMainGroupId;
	
	@JsonProperty("cusMainGroupCode")
	@Size(max = 60, message = "{groupCode.size}")
	private String cusMainGroupCode;
	private String cusMainGroupDesc;
	
	@JsonProperty("cusSubGroupId")
	@Pattern(regexp = "^$|[0-9]+", message = "{groupId.pattern}")
	private String cusSubGroupId;
	
	@JsonProperty("cusSubGroupCode")
	@Size(max = 60, message = "{groupCode.size}")
	private String cusSubGroupCode;
	private String cusSubGroupDesc;
	
	@JsonProperty("cusAreaId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusAreaId.pattern}")
	private String cusAreaId;
	private String cusAreaDesc;
	
	@JsonProperty("cusSecondaryDivisionId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusSecondaryDivisionId.pattern}")
	private String cusSecondaryDivisionId;
	private String cusSecondaryDivisionDesc;
	
	@JsonProperty("cusSmsAlertStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{cusSmsAlertStatus.pattern}")
	private String cusSmsAlertStatus;
	
	@JsonProperty("cusStatus")
	@NotBlank(message = "{cusStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{cusStatus.pattern}")
	private String cusStatus;
	
	@JsonProperty("cusRefCode1")
	@Size(max = 255, message = "{cusRefCode1.size}")
	private String cusRefCode1;
	
	@JsonProperty("cusRefCode2")
	@Size(max = 255, message = "{cusRefCode2.size}")
	private String cusRefCode2;
	
	@JsonProperty("cusRefCode3")
	@Size(max = 255, message = "{cusRefCode3.size}")
	private String cusRefCode3;
	
	@JsonProperty("cusGroupId")
	@Size(max = 255, message = "{cusGroupId.size}")
	private String cusGroupId;

	public CustomerRequestResource() {
		super();
	}

	public String getCusReferenceCode() {
		return cusReferenceCode;
	}

	public void setCusReferenceCode(String cusReferenceCode) {
		this.cusReferenceCode = cusReferenceCode;
	}

	public String getCusOrganizationTypeCommonListId() {
		return cusOrganizationTypeCommonListId;
	}

	public void setCusOrganizationTypeCommonListId(String cusOrganizationTypeCommonListId) {
		this.cusOrganizationTypeCommonListId = cusOrganizationTypeCommonListId;
	}

	public String getCusOrganizationTypeDesc() {
		return cusOrganizationTypeDesc;
	}

	public void setCusOrganizationTypeDesc(String cusOrganizationTypeDesc) {
		this.cusOrganizationTypeDesc = cusOrganizationTypeDesc;
	}

	public String getCusOriginationMethodCommonListId() {
		return cusOriginationMethodCommonListId;
	}

	public void setCusOriginationMethodCommonListId(String cusOriginationMethodCommonListId) {
		this.cusOriginationMethodCommonListId = cusOriginationMethodCommonListId;
	}

	public String getCusOriginationMethodDesc() {
		return cusOriginationMethodDesc;
	}

	public void setCusOriginationMethodDesc(String cusOriginationMethodDesc) {
		this.cusOriginationMethodDesc = cusOriginationMethodDesc;
	}

	public String getCusPreferredDay() {
		return cusPreferredDay;
	}

	public void setCusPreferredDay(String cusPreferredDay) {
		this.cusPreferredDay = cusPreferredDay;
	}

	public String getCusPreferredTime() {
		return cusPreferredTime;
	}

	public void setCusPreferredTime(String cusPreferredTime) {
		this.cusPreferredTime = cusPreferredTime;
	}

	public String getCusNearestBranchId() {
		return cusNearestBranchId;
	}

	public void setCusNearestBranchId(String cusNearestBranchId) {
		this.cusNearestBranchId = cusNearestBranchId;
	}

	public String getCusNearestBranchDesc() {
		return cusNearestBranchDesc;
	}

	public void setCusNearestBranchDesc(String cusNearestBranchDesc) {
		this.cusNearestBranchDesc = cusNearestBranchDesc;
	}

	public String getCusLocationLongitude() {
		return cusLocationLongitude;
	}

	public void setCusLocationLongitude(String cusLocationLongitude) {
		this.cusLocationLongitude = cusLocationLongitude;
	}

	public String getCusLocationLatitude() {
		return cusLocationLatitude;
	}

	public void setCusLocationLatitude(String cusLocationLatitude) {
		this.cusLocationLatitude = cusLocationLatitude;
	}

	public String getCusComment() {
		return cusComment;
	}

	public void setCusComment(String cusComment) {
		this.cusComment = cusComment;
	}

	public String getCusSectorId() {
		return cusSectorId;
	}

	public void setCusSectorId(String cusSectorId) {
		this.cusSectorId = cusSectorId;
	}

	public String getCusSectorDesc() {
		return cusSectorDesc;
	}

	public void setCusSectorDesc(String cusSectorDesc) {
		this.cusSectorDesc = cusSectorDesc;
	}

	public String getCusSubSectorId() {
		return cusSubSectorId;
	}

	public void setCusSubSectorId(String cusSubSectorId) {
		this.cusSubSectorId = cusSubSectorId;
	}

	public String getCusSubSectorDesc() {
		return cusSubSectorDesc;
	}

	public void setCusSubSectorDesc(String cusSubSectorDesc) {
		this.cusSubSectorDesc = cusSubSectorDesc;
	}

	public String getCusNotesOnBusiness() {
		return cusNotesOnBusiness;
	}

	public void setCusNotesOnBusiness(String cusNotesOnBusiness) {
		this.cusNotesOnBusiness = cusNotesOnBusiness;
	}

	public String getCusStatus() {
		return cusStatus;
	}

	public void setCusStatus(String cusStatus) {
		this.cusStatus = cusStatus;
	}
/*
	public String getCusProductDetailId() {
		return cusProductDetailId;
	}

	public void setCusProductDetailId(String cusProductDetailId) {
		this.cusProductDetailId = cusProductDetailId;
	}

	public String getCusProductDetailDesc() {
		return cusProductDetailDesc;
	}

	public void setCusProductDetailDesc(String cusProductDetailDesc) {
		this.cusProductDetailDesc = cusProductDetailDesc;
	}
*/
	public String getCusWithinBranchArea() {
		return cusWithinBranchArea;
	}

	public void setCusWithinBranchArea(String cusWithinBranchArea) {
		this.cusWithinBranchArea = cusWithinBranchArea;
	}

	public String getCusIndividualAnnualTurnoverId() {
		return cusIndividualAnnualTurnoverId;
	}

	public void setCusIndividualAnnualTurnoverId(String cusIndividualAnnualTurnoverId) {
		this.cusIndividualAnnualTurnoverId = cusIndividualAnnualTurnoverId;
	}

	public String getCusIndividualAnnualTurnoverDesc() {
		return cusIndividualAnnualTurnoverDesc;
	}

	public void setCusIndividualAnnualTurnoverDesc(String cusIndividualAnnualTurnoverDesc) {
		this.cusIndividualAnnualTurnoverDesc = cusIndividualAnnualTurnoverDesc;
	}

	public String getCusBusinessAnnualTurnoverId() {
		return cusBusinessAnnualTurnoverId;
	}

	public void setCusBusinessAnnualTurnoverId(String cusBusinessAnnualTurnoverId) {
		this.cusBusinessAnnualTurnoverId = cusBusinessAnnualTurnoverId;
	}

	public String getCusBusinessAnnualTurnoverDesc() {
		return cusBusinessAnnualTurnoverDesc;
	}

	public void setCusBusinessAnnualTurnoverDesc(String cusBusinessAnnualTurnoverDesc) {
		this.cusBusinessAnnualTurnoverDesc = cusBusinessAnnualTurnoverDesc;
	}

	public String getCusRelatedPartyStatus() {
		return cusRelatedPartyStatus;
	}

	public void setCusRelatedPartyStatus(String cusRelatedPartyStatus) {
		this.cusRelatedPartyStatus = cusRelatedPartyStatus;
	}

	public String getCusRelatedPartyTypeCommonListId() {
		return cusRelatedPartyTypeCommonListId;
	}

	public void setCusRelatedPartyTypeCommonListId(String cusRelatedPartyTypeCommonListId) {
		this.cusRelatedPartyTypeCommonListId = cusRelatedPartyTypeCommonListId;
	}

	public String getCusRelatedPartyTypeDesc() {
		return cusRelatedPartyTypeDesc;
	}

	public void setCusRelatedPartyTypeDesc(String cusRelatedPartyTypeDesc) {
		this.cusRelatedPartyTypeDesc = cusRelatedPartyTypeDesc;
	}

	public String getCusRelatedPartyComment() {
		return cusRelatedPartyComment;
	}

	public void setCusRelatedPartyComment(String cusRelatedPartyComment) {
		this.cusRelatedPartyComment = cusRelatedPartyComment;
	}

	public String getCusKMPStatus() {
		return cusKMPStatus;
	}

	public void setCusKMPStatus(String cusKMPStatus) {
		this.cusKMPStatus = cusKMPStatus;
	}

	public String getCusMainGroupId() {
		return cusMainGroupId;
	}

	public void setCusMainGroupId(String cusMainGroupId) {
		this.cusMainGroupId = cusMainGroupId;
	}

	public String getCusMainGroupDesc() {
		return cusMainGroupDesc;
	}

	public void setCusMainGroupDesc(String cusMainGroupDesc) {
		this.cusMainGroupDesc = cusMainGroupDesc;
	}

	public String getCusSubGroupId() {
		return cusSubGroupId;
	}

	public void setCusSubGroupId(String cusSubGroupId) {
		this.cusSubGroupId = cusSubGroupId;
	}

	public String getCusSubGroupDesc() {
		return cusSubGroupDesc;
	}

	public void setCusSubGroupDesc(String cusSubGroupDesc) {
		this.cusSubGroupDesc = cusSubGroupDesc;
	}

	public String getCusAreaId() {
		return cusAreaId;
	}

	public void setCusAreaId(String cusAreaId) {
		this.cusAreaId = cusAreaId;
	}

	public String getCusAreaDesc() {
		return cusAreaDesc;
	}

	public void setCusAreaDesc(String cusAreaDesc) {
		this.cusAreaDesc = cusAreaDesc;
	}

	public String getCusSecondaryDivisionId() {
		return cusSecondaryDivisionId;
	}

	public void setCusSecondaryDivisionId(String cusSecondaryDivisionId) {
		this.cusSecondaryDivisionId = cusSecondaryDivisionId;
	}

	public String getCusSecondaryDivisionDesc() {
		return cusSecondaryDivisionDesc;
	}

	public void setCusSecondaryDivisionDesc(String cusSecondaryDivisionDesc) {
		this.cusSecondaryDivisionDesc = cusSecondaryDivisionDesc;
	}

	public String getCusSmsAlertStatus() {
		return cusSmsAlertStatus;
	}

	public void setCusSmsAlertStatus(String cusSmsAlertStatus) {
		this.cusSmsAlertStatus = cusSmsAlertStatus;
	}

	public String getCusMainGroupCode() {
		return cusMainGroupCode;
	}

	public void setCusMainGroupCode(String cusMainGroupCode) {
		this.cusMainGroupCode = cusMainGroupCode;
	}

	public String getCusSubGroupCode() {
		return cusSubGroupCode;
	}

	public void setCusSubGroupCode(String cusSubGroupCode) {
		this.cusSubGroupCode = cusSubGroupCode;
	}

	public String getCusRefCode1() {
		return cusRefCode1;
	}

	public void setCusRefCode1(String cusRefCode1) {
		this.cusRefCode1 = cusRefCode1;
	}

	public String getCusRefCode2() {
		return cusRefCode2;
	}

	public void setCusRefCode2(String cusRefCode2) {
		this.cusRefCode2 = cusRefCode2;
	}

	public String getCusRefCode3() {
		return cusRefCode3;
	}

	public void setCusRefCode3(String cusRefCode3) {
		this.cusRefCode3 = cusRefCode3;
	}

	public String getCusGroupId() {
		return cusGroupId;
	}

	public void setCusGroupId(String cusGroupId) {
		this.cusGroupId = cusGroupId;
	}
}
