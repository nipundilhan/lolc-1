package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.LawyerType;
import com.fusionx.lending.origination.enums.RelatedPartyStatus;
import com.fusionx.lending.origination.enums.SmsAlertStatus;
import com.fusionx.lending.origination.enums.SuppliesStatus;
import com.fusionx.lending.origination.enums.SuppliesType;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SuppliesBasicInfoResponseResource extends PersonBasicInfoResponseResource{

	private Long id;
	
	private String supReferenceCode;
	
	private Long supOrganizationTypeCommonListId;
	
	private String supOrganizationTypeDesc;
	
	private String supOrganizationTypeCode;
	
	private Long supOriginationMethodCommonListId;
	
	private String supOriginationMethodDesc;
	
	private String supLocationLongitude;
	
	private String supLocationLatitude;
	
	private SuppliesType supSuppliesType;
	
	private Long supSupplierTypeCommonListId;
	
	private String supSupplierTypeDesc;
	
	private LawyerType supLawyerType;
	
	private Long supLagalCaseHandlingAreaCommonListId;
	
	private String supLagalCaseHandlingAreaDesc;
	
	private String supLawyerReferenceNumber;
	
	private RelatedPartyStatus supRelatedPartyStatus;
	
	private Long supRelatedPartyTypeCommonListId;
	
	private String supRelatedPartyTypeDesc;
	
	private String supRelatedPartyComment;
	
	private SmsAlertStatus supSmsAlertStatus;
	
	private String supComment;
	
	private SuppliesStatus supStatus;
	
	private String supRefCode1;
	
	private String supRefCode2;
	
	private String supRefCode3;
	
	private String supGroupId;
	
	private String supCreatedUser;
	
	private Date supCreatedDate;
	
	private String supModifiedUser;
	
	private Date supModifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupReferenceCode() {
		return supReferenceCode;
	}

	public void setSupReferenceCode(String supReferenceCode) {
		this.supReferenceCode = supReferenceCode;
	}

	public Long getSupOrganizationTypeCommonListId() {
		return supOrganizationTypeCommonListId;
	}

	public void setSupOrganizationTypeCommonListId(Long supOrganizationTypeCommonListId) {
		this.supOrganizationTypeCommonListId = supOrganizationTypeCommonListId;
	}

	public String getSupOrganizationTypeDesc() {
		return supOrganizationTypeDesc;
	}

	public void setSupOrganizationTypeDesc(String supOrganizationTypeDesc) {
		this.supOrganizationTypeDesc = supOrganizationTypeDesc;
	}

	public String getSupOrganizationTypeCode() {
		return supOrganizationTypeCode;
	}

	public void setSupOrganizationTypeCode(String supOrganizationTypeCode) {
		this.supOrganizationTypeCode = supOrganizationTypeCode;
	}

	public Long getSupOriginationMethodCommonListId() {
		return supOriginationMethodCommonListId;
	}

	public void setSupOriginationMethodCommonListId(Long supOriginationMethodCommonListId) {
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

	public SuppliesStatus getSupStatus() {
		return supStatus;
	}

	public void setSupStatus(SuppliesStatus supStatus) {
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

	public String getSupCreatedUser() {
		return supCreatedUser;
	}

	public void setSupCreatedUser(String supCreatedUser) {
		this.supCreatedUser = supCreatedUser;
	}

	public Date getSupCreatedDate() {
		return supCreatedDate;
	}

	public void setSupCreatedDate(Date supCreatedDate) {
		this.supCreatedDate = supCreatedDate;
	}

	public String getSupModifiedUser() {
		return supModifiedUser;
	}

	public void setSupModifiedUser(String supModifiedUser) {
		this.supModifiedUser = supModifiedUser;
	}

	public Date getSupModifiedDate() {
		return supModifiedDate;
	}

	public void setSupModifiedDate(Date supModifiedDate) {
		this.supModifiedDate = supModifiedDate;
	}

	public Long getSupSupplierTypeCommonListId() {
		return supSupplierTypeCommonListId;
	}

	public void setSupSupplierTypeCommonListId(Long supSupplierTypeCommonListId) {
		this.supSupplierTypeCommonListId = supSupplierTypeCommonListId;
	}

	public String getSupSupplierTypeDesc() {
		return supSupplierTypeDesc;
	}

	public void setSupSupplierTypeDesc(String supSupplierTypeDesc) {
		this.supSupplierTypeDesc = supSupplierTypeDesc;
	}

	public RelatedPartyStatus getSupRelatedPartyStatus() {
		return supRelatedPartyStatus;
	}

	public void setSupRelatedPartyStatus(RelatedPartyStatus supRelatedPartyStatus) {
		this.supRelatedPartyStatus = supRelatedPartyStatus;
	}

	public Long getSupRelatedPartyTypeCommonListId() {
		return supRelatedPartyTypeCommonListId;
	}

	public void setSupRelatedPartyTypeCommonListId(Long supRelatedPartyTypeCommonListId) {
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

	public SmsAlertStatus getSupSmsAlertStatus() {
		return supSmsAlertStatus;
	}

	public void setSupSmsAlertStatus(SmsAlertStatus supSmsAlertStatus) {
		this.supSmsAlertStatus = supSmsAlertStatus;
	}

	public SuppliesType getSupSuppliesType() {
		return supSuppliesType;
	}

	public void setSupSuppliesType(SuppliesType supSuppliesType) {
		this.supSuppliesType = supSuppliesType;
	}

	public LawyerType getSupLawyerType() {
		return supLawyerType;
	}

	public void setSupLawyerType(LawyerType supLawyerType) {
		this.supLawyerType = supLawyerType;
	}

	public Long getSupLagalCaseHandlingAreaCommonListId() {
		return supLagalCaseHandlingAreaCommonListId;
	}

	public void setSupLagalCaseHandlingAreaCommonListId(Long supLagalCaseHandlingAreaCommonListId) {
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
