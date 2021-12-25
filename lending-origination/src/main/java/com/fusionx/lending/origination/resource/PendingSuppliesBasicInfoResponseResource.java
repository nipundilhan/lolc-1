package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.LawyerType;
import com.fusionx.lending.origination.enums.RelatedPartyStatus;
import com.fusionx.lending.origination.enums.SmsAlertStatus;
import com.fusionx.lending.origination.enums.SuppliesPendingStatus;
import com.fusionx.lending.origination.enums.SuppliesStatus;
import com.fusionx.lending.origination.enums.SuppliesType;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingSuppliesBasicInfoResponseResource extends PendingPersonBasicInfoResponseResource{

	private Long id;
	
	private String psupReferenceCode;
	
	private Long psupOrganizationTypeCommonListId;
	
	private String psupOrganizationTypeDesc;
	
	private String psupOrganizationTypeCode;
	
	private Long psupOriginationMethodCommonListId;
	
	private String psupOriginationMethodDesc;
	
	private String psupLocationLongitude;
	
	private String psupLocationLatitude;
	
	private SuppliesType psupSuppliesType;
	
	private Long psupSupplierTypeCommonListId;
	
	private String psupSupplierTypeDesc;
	
	private LawyerType psupLawyerType;
	
	private Long psupLagalCaseHandlingAreaCommonListId;
	
	private String psupLagalCaseHandlingAreaDesc;
	
	private String psupLawyerReferenceNumber;
	
	private RelatedPartyStatus psupRelatedPartyStatus;
	
	private Long psupRelatedPartyTypeCommonListId;
	
	private String psupRelatedPartyTypeDesc;
	
	private String psupRelatedPartyComment;
	
	private SmsAlertStatus psupSmsAlertStatus;
	
	private String psupComment;
	
	private SuppliesStatus psupStatus;
	
	private SuppliesPendingStatus psupPendingStatus;
	
	private String psupRefCode1;
	
	private String psupRefCode2;
	
	private String psupRefCode3;
	
	private String psupGroupId;
	
	private String psupCreatedUser;
	
	private Date psupCreatedDate;
	
	private String psupModifiedUser;
	
	private Date psupModifiedDate;
	
	private Long psupPendingPersonId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPsupReferenceCode() {
		return psupReferenceCode;
	}

	public void setPsupReferenceCode(String psupReferenceCode) {
		this.psupReferenceCode = psupReferenceCode;
	}

	public Long getPsupOrganizationTypeCommonListId() {
		return psupOrganizationTypeCommonListId;
	}

	public void setPsupOrganizationTypeCommonListId(Long psupOrganizationTypeCommonListId) {
		this.psupOrganizationTypeCommonListId = psupOrganizationTypeCommonListId;
	}

	public String getPsupOrganizationTypeDesc() {
		return psupOrganizationTypeDesc;
	}

	public void setPsupOrganizationTypeDesc(String psupOrganizationTypeDesc) {
		this.psupOrganizationTypeDesc = psupOrganizationTypeDesc;
	}

	public String getPsupOrganizationTypeCode() {
		return psupOrganizationTypeCode;
	}

	public void setPsupOrganizationTypeCode(String psupOrganizationTypeCode) {
		this.psupOrganizationTypeCode = psupOrganizationTypeCode;
	}

	public Long getPsupOriginationMethodCommonListId() {
		return psupOriginationMethodCommonListId;
	}

	public void setPsupOriginationMethodCommonListId(Long psupOriginationMethodCommonListId) {
		this.psupOriginationMethodCommonListId = psupOriginationMethodCommonListId;
	}

	public String getPsupOriginationMethodDesc() {
		return psupOriginationMethodDesc;
	}

	public void setPsupOriginationMethodDesc(String psupOriginationMethodDesc) {
		this.psupOriginationMethodDesc = psupOriginationMethodDesc;
	}

	public String getPsupLocationLongitude() {
		return psupLocationLongitude;
	}

	public void setPsupLocationLongitude(String psupLocationLongitude) {
		this.psupLocationLongitude = psupLocationLongitude;
	}

	public String getPsupLocationLatitude() {
		return psupLocationLatitude;
	}

	public void setPsupLocationLatitude(String psupLocationLatitude) {
		this.psupLocationLatitude = psupLocationLatitude;
	}

	public String getPsupComment() {
		return psupComment;
	}

	public void setPsupComment(String psupComment) {
		this.psupComment = psupComment;
	}

	public SuppliesStatus getPsupStatus() {
		return psupStatus;
	}

	public void setPsupStatus(SuppliesStatus psupStatus) {
		this.psupStatus = psupStatus;
	}

	public SuppliesPendingStatus getPsupPendingStatus() {
		return psupPendingStatus;
	}

	public void setPsupPendingStatus(SuppliesPendingStatus psupPendingStatus) {
		this.psupPendingStatus = psupPendingStatus;
	}

	public String getPsupRefCode1() {
		return psupRefCode1;
	}

	public void setPsupRefCode1(String psupRefCode1) {
		this.psupRefCode1 = psupRefCode1;
	}

	public String getPsupRefCode2() {
		return psupRefCode2;
	}

	public void setPsupRefCode2(String psupRefCode2) {
		this.psupRefCode2 = psupRefCode2;
	}

	public String getPsupRefCode3() {
		return psupRefCode3;
	}

	public void setPsupRefCode3(String psupRefCode3) {
		this.psupRefCode3 = psupRefCode3;
	}

	public String getPsupGroupId() {
		return psupGroupId;
	}

	public void setPsupGroupId(String psupGroupId) {
		this.psupGroupId = psupGroupId;
	}

	public String getPsupCreatedUser() {
		return psupCreatedUser;
	}

	public void setPsupCreatedUser(String psupCreatedUser) {
		this.psupCreatedUser = psupCreatedUser;
	}

	public Date getPsupCreatedDate() {
		return psupCreatedDate;
	}

	public void setPsupCreatedDate(Date psupCreatedDate) {
		this.psupCreatedDate = psupCreatedDate;
	}

	public String getPsupModifiedUser() {
		return psupModifiedUser;
	}

	public void setPsupModifiedUser(String psupModifiedUser) {
		this.psupModifiedUser = psupModifiedUser;
	}

	public Date getPsupModifiedDate() {
		return psupModifiedDate;
	}

	public void setPsupModifiedDate(Date psupModifiedDate) {
		this.psupModifiedDate = psupModifiedDate;
	}

	public Long getPsupSupplierTypeCommonListId() {
		return psupSupplierTypeCommonListId;
	}

	public void setPsupSupplierTypeCommonListId(Long psupSupplierTypeCommonListId) {
		this.psupSupplierTypeCommonListId = psupSupplierTypeCommonListId;
	}

	public String getPsupSupplierTypeDesc() {
		return psupSupplierTypeDesc;
	}

	public void setPsupSupplierTypeDesc(String psupSupplierTypeDesc) {
		this.psupSupplierTypeDesc = psupSupplierTypeDesc;
	}

	public RelatedPartyStatus getPsupRelatedPartyStatus() {
		return psupRelatedPartyStatus;
	}

	public void setPsupRelatedPartyStatus(RelatedPartyStatus psupRelatedPartyStatus) {
		this.psupRelatedPartyStatus = psupRelatedPartyStatus;
	}

	public Long getPsupRelatedPartyTypeCommonListId() {
		return psupRelatedPartyTypeCommonListId;
	}

	public void setPsupRelatedPartyTypeCommonListId(Long psupRelatedPartyTypeCommonListId) {
		this.psupRelatedPartyTypeCommonListId = psupRelatedPartyTypeCommonListId;
	}

	public String getPsupRelatedPartyTypeDesc() {
		return psupRelatedPartyTypeDesc;
	}

	public void setPsupRelatedPartyTypeDesc(String psupRelatedPartyTypeDesc) {
		this.psupRelatedPartyTypeDesc = psupRelatedPartyTypeDesc;
	}

	public String getPsupRelatedPartyComment() {
		return psupRelatedPartyComment;
	}

	public void setPsupRelatedPartyComment(String psupRelatedPartyComment) {
		this.psupRelatedPartyComment = psupRelatedPartyComment;
	}

	public SmsAlertStatus getPsupSmsAlertStatus() {
		return psupSmsAlertStatus;
	}

	public void setPsupSmsAlertStatus(SmsAlertStatus psupSmsAlertStatus) {
		this.psupSmsAlertStatus = psupSmsAlertStatus;
	}

	public SuppliesType getPsupSuppliesType() {
		return psupSuppliesType;
	}

	public void setPsupSuppliesType(SuppliesType psupSuppliesType) {
		this.psupSuppliesType = psupSuppliesType;
	}

	public LawyerType getPsupLawyerType() {
		return psupLawyerType;
	}

	public void setPsupLawyerType(LawyerType psupLawyerType) {
		this.psupLawyerType = psupLawyerType;
	}

	public Long getPsupLagalCaseHandlingAreaCommonListId() {
		return psupLagalCaseHandlingAreaCommonListId;
	}

	public void setPsupLagalCaseHandlingAreaCommonListId(Long psupLagalCaseHandlingAreaCommonListId) {
		this.psupLagalCaseHandlingAreaCommonListId = psupLagalCaseHandlingAreaCommonListId;
	}

	public String getPsupLagalCaseHandlingAreaDesc() {
		return psupLagalCaseHandlingAreaDesc;
	}

	public void setPsupLagalCaseHandlingAreaDesc(String psupLagalCaseHandlingAreaDesc) {
		this.psupLagalCaseHandlingAreaDesc = psupLagalCaseHandlingAreaDesc;
	}

	public String getPsupLawyerReferenceNumber() {
		return psupLawyerReferenceNumber;
	}

	public void setPsupLawyerReferenceNumber(String psupLawyerReferenceNumber) {
		this.psupLawyerReferenceNumber = psupLawyerReferenceNumber;
	}

	public Long getPsupPendingPersonId() {
		return psupPendingPersonId;
	}

	public void setPsupPendingPersonId(Long psupPendingPersonId) {
		this.psupPendingPersonId = psupPendingPersonId;
	}
	
	
}
