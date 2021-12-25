package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SuppliesRequestResource extends PersonRequestResource{

	@JsonProperty("supReferenceCode")
	@NotBlank(message = "{supReferenceCode.not-null}")
	@Size(max = 15, message = "{supReferenceCode.size}")
	private String supReferenceCode;
	
	@JsonProperty("supSuppliesType")
	@NotBlank(message = "{supSuppliesType.not-null}")
	@Pattern(regexp = "^$|SUPPLIER|LAWYER|BUYER|ZIZER|VALUATION_AGENT|INSPECTION_AGENT|DEALER|AUCTIONEER|STOCK_BROKERS|LISTED_COMPANIES|REGISTRATION_AUTHORITY|SECURITY_FIRM|YARD_SUPPLIER|EMPLOYEE|GUARANTOR", message = "{supSuppliesType.pattern}")
	private String supSuppliesType;
	
	@JsonProperty("supSupplierTypeCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{supSupplierTypeCommonListId.pattern}")
	private String supSupplierTypeCommonListId;
	private String supSupplierTypeDesc;
	
	@JsonProperty("supLawyerType")
	@Pattern(regexp = "^$|INTERNAL|EXTERNAL", message = "{supLawyerType.pattern}")
	private String supLawyerType;
	
	@JsonProperty("supLagalCaseHandlingAreaCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{supLagalCaseHandlingAreaCommonListId.pattern}")
	private String supLagalCaseHandlingAreaCommonListId;
	private String supLagalCaseHandlingAreaDesc;
	
	@JsonProperty("supLawyerReferenceNumber")
	@Size(max = 255, message = "{supLawyerReferenceNumber.size}")
	private String supLawyerReferenceNumber;
	
	@JsonProperty("supOrganizationTypeCommonListId")
	@NotBlank(message = "{supOrganizationTypeCommonListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{supOrganizationTypeCommonListId.pattern}")
	private String supOrganizationTypeCommonListId;
	private String supOrganizationTypeDesc;
	
	@JsonProperty("supOriginationMethodCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{supOriginationMethodCommonListId.pattern}")
	private String supOriginationMethodCommonListId;
	private String supOriginationMethodDesc;
	
	@JsonProperty("supLocationLongitude")
	@Pattern(regexp = "^$|^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$",message="{supLocationLongitude.pattern}")
	private String supLocationLongitude;
	
	@JsonProperty("supLocationLatitude")
	@Pattern(regexp = "^$|^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$",message="{supLocationLatitude.pattern}")
	private String supLocationLatitude;
	
	@JsonProperty("supRelatedPartyStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{supRelatedPartyStatus.pattern}")
	private String supRelatedPartyStatus;
	
	@JsonProperty("supRelatedPartyTypeCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{supRelatedPartyTypeCommonListId.pattern}")
	private String supRelatedPartyTypeCommonListId;
	private String supRelatedPartyTypeDesc;
	
	@Size(max = 255, message = "{supComment.size}")
	private String supRelatedPartyComment;
	
	@JsonProperty("supSmsAlertStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{supSmsAlertStatus.pattern}")
	private String supSmsAlertStatus;
	
	@JsonProperty("supComment")
	@Size(max = 255, message = "{supComment.size}")
	private String supComment;
	
	@JsonProperty("supStatus")
	@NotBlank(message = "{supStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{supStatus.pattern}")
	private String supStatus;
	
	@JsonProperty("supRefCode1")
	@Size(max = 255, message = "{supRefCode1.size}")
	private String supRefCode1;
	
	@JsonProperty("supRefCode2")
	@Size(max = 255, message = "{supRefCode2.size}")
	private String supRefCode2;
	
	@JsonProperty("supRefCode3")
	@Size(max = 255, message = "{supRefCode3.size}")
	private String supRefCode3;
	
	@JsonProperty("supGroupId")
	@Size(max = 255, message = "{supGroupId.size}")
	private String supGroupId;

	public SuppliesRequestResource() {
		super();
	}

	public String getSupReferenceCode() {
		return supReferenceCode;
	}

	public void setSupReferenceCode(String supReferenceCode) {
		this.supReferenceCode = supReferenceCode;
	}

	public String getSupOrganizationTypeCommonListId() {
		return supOrganizationTypeCommonListId;
	}

	public void setSupOrganizationTypeCommonListId(String supOrganizationTypeCommonListId) {
		this.supOrganizationTypeCommonListId = supOrganizationTypeCommonListId;
	}

	public String getSupOrganizationTypeDesc() {
		return supOrganizationTypeDesc;
	}

	public void setSupOrganizationTypeDesc(String supOrganizationTypeDesc) {
		this.supOrganizationTypeDesc = supOrganizationTypeDesc;
	}

	public String getSupOriginationMethodCommonListId() {
		return supOriginationMethodCommonListId;
	}

	public void setSupOriginationMethodCommonListId(String supOriginationMethodCommonListId) {
		this.supOriginationMethodCommonListId = supOriginationMethodCommonListId;
	}

	public String getSupOriginationMethodDesc() {
		return supOriginationMethodDesc;
	}

	public void setSupOriginationMethodDesc(String supOriginationMethodDesc) {
		this.supOriginationMethodDesc = supOriginationMethodDesc;
	}

	public String getSupLocationLongitude() {
		return supLocationLongitude;
	}

	public void setSupLocationLongitude(String supLocationLongitude) {
		this.supLocationLongitude = supLocationLongitude;
	}

	public String getSupLocationLatitude() {
		return supLocationLatitude;
	}

	public void setSupLocationLatitude(String supLocationLatitude) {
		this.supLocationLatitude = supLocationLatitude;
	}

	public String getSupComment() {
		return supComment;
	}

	public void setSupComment(String supComment) {
		this.supComment = supComment;
	}

	public String getSupStatus() {
		return supStatus;
	}

	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}

	public String getSupRefCode1() {
		return supRefCode1;
	}

	public void setSupRefCode1(String supRefCode1) {
		this.supRefCode1 = supRefCode1;
	}

	public String getSupRefCode2() {
		return supRefCode2;
	}

	public void setSupRefCode2(String supRefCode2) {
		this.supRefCode2 = supRefCode2;
	}

	public String getSupRefCode3() {
		return supRefCode3;
	}

	public void setSupRefCode3(String supRefCode3) {
		this.supRefCode3 = supRefCode3;
	}

	public String getSupGroupId() {
		return supGroupId;
	}

	public void setSupGroupId(String supGroupId) {
		this.supGroupId = supGroupId;
	}

	public String getSupSupplierTypeCommonListId() {
		return supSupplierTypeCommonListId;
	}

	public void setSupSupplierTypeCommonListId(String supSupplierTypeCommonListId) {
		this.supSupplierTypeCommonListId = supSupplierTypeCommonListId;
	}

	public String getSupRelatedPartyStatus() {
		return supRelatedPartyStatus;
	}

	public void setSupRelatedPartyStatus(String supRelatedPartyStatus) {
		this.supRelatedPartyStatus = supRelatedPartyStatus;
	}

	public String getSupRelatedPartyTypeCommonListId() {
		return supRelatedPartyTypeCommonListId;
	}

	public void setSupRelatedPartyTypeCommonListId(String supRelatedPartyTypeCommonListId) {
		this.supRelatedPartyTypeCommonListId = supRelatedPartyTypeCommonListId;
	}

	public String getSupRelatedPartyTypeDesc() {
		return supRelatedPartyTypeDesc;
	}

	public void setSupRelatedPartyTypeDesc(String supRelatedPartyTypeDesc) {
		this.supRelatedPartyTypeDesc = supRelatedPartyTypeDesc;
	}

	public String getSupRelatedPartyComment() {
		return supRelatedPartyComment;
	}

	public void setSupRelatedPartyComment(String supRelatedPartyComment) {
		this.supRelatedPartyComment = supRelatedPartyComment;
	}

	public String getSupSmsAlertStatus() {
		return supSmsAlertStatus;
	}

	public void setSupSmsAlertStatus(String supSmsAlertStatus) {
		this.supSmsAlertStatus = supSmsAlertStatus;
	}

	public String getSupSupplierTypeDesc() {
		return supSupplierTypeDesc;
	}

	public void setSupSupplierTypeDesc(String supSupplierTypeDesc) {
		this.supSupplierTypeDesc = supSupplierTypeDesc;
	}

	public String getSupSuppliesType() {
		return supSuppliesType;
	}

	public void setSupSuppliesType(String supSuppliesType) {
		this.supSuppliesType = supSuppliesType;
	}

	public String getSupLawyerType() {
		return supLawyerType;
	}

	public void setSupLawyerType(String supLawyerType) {
		this.supLawyerType = supLawyerType;
	}

	public String getSupLagalCaseHandlingAreaCommonListId() {
		return supLagalCaseHandlingAreaCommonListId;
	}

	public void setSupLagalCaseHandlingAreaCommonListId(String supLagalCaseHandlingAreaCommonListId) {
		this.supLagalCaseHandlingAreaCommonListId = supLagalCaseHandlingAreaCommonListId;
	}

	public String getSupLagalCaseHandlingAreaDesc() {
		return supLagalCaseHandlingAreaDesc;
	}

	public void setSupLagalCaseHandlingAreaDesc(String supLagalCaseHandlingAreaDesc) {
		this.supLagalCaseHandlingAreaDesc = supLagalCaseHandlingAreaDesc;
	}

	public String getSupLawyerReferenceNumber() {
		return supLawyerReferenceNumber;
	}

	public void setSupLawyerReferenceNumber(String supLawyerReferenceNumber) {
		this.supLawyerReferenceNumber = supLawyerReferenceNumber;
	}
}
