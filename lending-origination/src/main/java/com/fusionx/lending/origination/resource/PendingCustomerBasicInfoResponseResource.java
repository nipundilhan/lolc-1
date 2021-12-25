package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.CustomerPendingStatus;
import com.fusionx.lending.origination.enums.CustomerStatus;
import com.fusionx.lending.origination.enums.Day;
import com.fusionx.lending.origination.enums.DeceaseStatus;
import com.fusionx.lending.origination.enums.KmpStatus;
import com.fusionx.lending.origination.enums.LivingCategory;
import com.fusionx.lending.origination.enums.PhysicallyChallengedStatus;
import com.fusionx.lending.origination.enums.RelatedPartyStatus;
import com.fusionx.lending.origination.enums.SmsAlertStatus;
import com.fusionx.lending.origination.enums.WithinBranchArea;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingCustomerBasicInfoResponseResource extends PendingPersonBasicInfoResponseResource{

	private Long id;
	
	private String pcusReferenceCode;
	
	private Long pcusOrganizationTypeCommonListId;
	
	private String pcusOrganizationTypeDesc;
	
	private String pcusOrganizationTypeCode;
	
	private Long pcusOriginationMethodCommonListId;
	
	private String pcusOriginationMethodDesc;
	
	private Day pcusPreferredDay;
	
	private String pcusPreferredTime;
	
	private Long pcusNearestBranchId;
	
	private String pcusNearestBranchDesc;
	
	private String pcusLocationLongitude;
	
	private String pcusLocationLatitude;
	
	private WithinBranchArea pcusWithinBranchArea;
	
	private String pcusComment;
	
	private Long pcusSectorId;
	
	private String pcusSectorCode;
	
	private String pcusSectorDesc;
	
	private Long pcusSubSectorId;
	
	private String pcusSubSectorCode;
	
	private String pcusSubSectorDesc;
	
	private String pcusNotesOnBusiness;
	
	private Long pcusIndividualAnnualTurnoverId;
	
	private String pcusIndividualAnnualTurnoverDesc;
	
	private Long pcusBusinessAnnualTurnoverId;
	
	private String pcusBusinessAnnualTurnoverDesc;
	
	private RelatedPartyStatus pcusRelatedPartyStatus;
	
	private Long pcusRelatedPartyTypeCommonListId;
	
	private String pcusRelatedPartyTypeDesc;
	
	private String pcusRelatedPartyComment;
	
	private KmpStatus pcusKMPStatus;
	
	private Long pcusMainGroupId;
	
	private String pcusMainGroupCode;
	
	private String pcusMainGroupDesc;
	
	private Long pcusSubGroupId;
	
	private String pcusSubGroupCode;
	
	private String pcusSubGroupDesc;
	
	private Long pcusAreaId;
	
	private String pcusAreaDesc;
	
	private Long pcusSecondaryDivisionId;
	
	private String pcusSecondaryDivisionDesc;
	
	private SmsAlertStatus pcusSmsAlertStatus;
	
	private CustomerStatus pcusStatus;
	
	private CustomerPendingStatus pcusPendingStatus;
	
	private String pcusFatherName;
	
	private String pcusSpouseName;
	
	private String pcusGuardianName;
	
	private Long pcusMajorOccupationId;
	
	private String pcusMajorOccupationCode;
	
	private String pcusMajorOccupationDesc;
	
	private Long pcusSubOccupationId;
	
	private String pcusSubOccupationCode;
	
	private String pcusSubOccupationDesc;
	
	private Long pcusOccupationId;
	
	private String pcusOccupationCode;
	
	private String pcusOccupationDesc;
	
	private Long pcusResidentialStatusCommonListId;
	
	private String pcusResidentialStatusDesc;
	
	private Date pcusLivingPeriodStartDate;
	
	private LivingCategory pcusLivingCategory;
	
	private PhysicallyChallengedStatus pcusPhysicallyChallenged;
	
	private DeceaseStatus pcusDeceaseStatus;
	
	private Date pcusDeceaseDate;
	
	private Long pcusPersonTypeCommonListId;
	
	private String pcusPersonTypeDesc;
	
	private String pcusPersonTypeCode;
	
	private Long pcusLivingConditionCommonListId;
	
	private String pcusLivingConditionDesc;
	
	private Long pcusGoverningAuthorityCommonListId;
	
	private String pcusGoverningAuthorityDesc;
	
	private Date pcusRegisteredDate;
	
	private Date pcusOperationStartDate;
	
	private String pcusRefCode1;
	
	private String pcusRefCode2;
	
	private String pcusRefCode3;
	
	private String pcusGroupId;
	
	private String pcusCreatedUser;
	
	private Date pcusCreatedDate;
	
	private String pcusModifiedUser;
	
	private Date pcusModifiedDate;
	
	private Long pcusPenPernId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPcusReferenceCode() {
		return pcusReferenceCode;
	}

	public void setPcusReferenceCode(String pcusReferenceCode) {
		this.pcusReferenceCode = pcusReferenceCode;
	}

	public Long getPcusOrganizationTypeCommonListId() {
		return pcusOrganizationTypeCommonListId;
	}

	public void setPcusOrganizationTypeCommonListId(Long pcusOrganizationTypeCommonListId) {
		this.pcusOrganizationTypeCommonListId = pcusOrganizationTypeCommonListId;
	}

	public Long getPcusOriginationMethodCommonListId() {
		return pcusOriginationMethodCommonListId;
	}

	public void setPcusOriginationMethodCommonListId(Long pcusOriginationMethodCommonListId) {
		this.pcusOriginationMethodCommonListId = pcusOriginationMethodCommonListId;
	}

	public Day getPcusPreferredDay() {
		return pcusPreferredDay;
	}

	public void setPcusPreferredDay(Day pcusPreferredDay) {
		this.pcusPreferredDay = pcusPreferredDay;
	}

	public String getPcusPreferredTime() {
		return pcusPreferredTime;
	}

	public void setPcusPreferredTime(String pcusPreferredTime) {
		this.pcusPreferredTime = pcusPreferredTime;
	}

	public Long getPcusNearestBranchId() {
		return pcusNearestBranchId;
	}

	public void setPcusNearestBranchId(Long pcusNearestBranchId) {
		this.pcusNearestBranchId = pcusNearestBranchId;
	}

	public String getPcusLocationLongitude() {
		return pcusLocationLongitude;
	}

	public void setPcusLocationLongitude(String pcusLocationLongitude) {
		this.pcusLocationLongitude = pcusLocationLongitude;
	}

	public String getPcusLocationLatitude() {
		return pcusLocationLatitude;
	}

	public void setPcusLocationLatitude(String pcusLocationLatitude) {
		this.pcusLocationLatitude = pcusLocationLatitude;
	}

	public WithinBranchArea getPcusWithinBranchArea() {
		return pcusWithinBranchArea;
	}

	public void setPcusWithinBranchArea(WithinBranchArea pcusWithinBranchArea) {
		this.pcusWithinBranchArea = pcusWithinBranchArea;
	}

	public String getPcusComment() {
		return pcusComment;
	}

	public void setPcusComment(String pcusComment) {
		this.pcusComment = pcusComment;
	}

	public Long getPcusSectorId() {
		return pcusSectorId;
	}

	public void setPcusSectorId(Long pcusSectorId) {
		this.pcusSectorId = pcusSectorId;
	}

	public Long getPcusSubSectorId() {
		return pcusSubSectorId;
	}

	public void setPcusSubSectorId(Long pcusSubSectorId) {
		this.pcusSubSectorId = pcusSubSectorId;
	}

	public String getPcusNotesOnBusiness() {
		return pcusNotesOnBusiness;
	}

	public void setPcusNotesOnBusiness(String pcusNotesOnBusiness) {
		this.pcusNotesOnBusiness = pcusNotesOnBusiness;
	}

	public Long getPcusIndividualAnnualTurnoverId() {
		return pcusIndividualAnnualTurnoverId;
	}

	public void setPcusIndividualAnnualTurnoverId(Long pcusIndividualAnnualTurnoverId) {
		this.pcusIndividualAnnualTurnoverId = pcusIndividualAnnualTurnoverId;
	}

	public Long getPcusBusinessAnnualTurnoverId() {
		return pcusBusinessAnnualTurnoverId;
	}

	public void setPcusBusinessAnnualTurnoverId(Long pcusBusinessAnnualTurnoverId) {
		this.pcusBusinessAnnualTurnoverId = pcusBusinessAnnualTurnoverId;
	}

	public RelatedPartyStatus getPcusRelatedPartyStatus() {
		return pcusRelatedPartyStatus;
	}

	public void setPcusRelatedPartyStatus(RelatedPartyStatus pcusRelatedPartyStatus) {
		this.pcusRelatedPartyStatus = pcusRelatedPartyStatus;
	}

	public Long getPcusRelatedPartyTypeCommonListId() {
		return pcusRelatedPartyTypeCommonListId;
	}

	public void setPcusRelatedPartyTypeCommonListId(Long pcusRelatedPartyTypeCommonListId) {
		this.pcusRelatedPartyTypeCommonListId = pcusRelatedPartyTypeCommonListId;
	}

	public String getPcusRelatedPartyComment() {
		return pcusRelatedPartyComment;
	}

	public void setPcusRelatedPartyComment(String pcusRelatedPartyComment) {
		this.pcusRelatedPartyComment = pcusRelatedPartyComment;
	}

	public KmpStatus getPcusKMPStatus() {
		return pcusKMPStatus;
	}

	public void setPcusKMPStatus(KmpStatus pcusKMPStatus) {
		this.pcusKMPStatus = pcusKMPStatus;
	}

	public Long getPcusMainGroupId() {
		return pcusMainGroupId;
	}

	public void setPcusMainGroupId(Long pcusMainGroupId) {
		this.pcusMainGroupId = pcusMainGroupId;
	}

	public Long getPcusSubGroupId() {
		return pcusSubGroupId;
	}

	public void setPcusSubGroupId(Long pcusSubGroupId) {
		this.pcusSubGroupId = pcusSubGroupId;
	}

	public Long getPcusAreaId() {
		return pcusAreaId;
	}

	public void setPcusAreaId(Long pcusAreaId) {
		this.pcusAreaId = pcusAreaId;
	}

	public Long getPcusSecondaryDivisionId() {
		return pcusSecondaryDivisionId;
	}

	public void setPcusSecondaryDivisionId(Long pcusSecondaryDivisionId) {
		this.pcusSecondaryDivisionId = pcusSecondaryDivisionId;
	}

	public SmsAlertStatus getPcusSmsAlertStatus() {
		return pcusSmsAlertStatus;
	}

	public void setPcusSmsAlertStatus(SmsAlertStatus pcusSmsAlertStatus) {
		this.pcusSmsAlertStatus = pcusSmsAlertStatus;
	}

	public CustomerStatus getPcusStatus() {
		return pcusStatus;
	}

	public void setPcusStatus(CustomerStatus pcusStatus) {
		this.pcusStatus = pcusStatus;
	}

	public String getPcusFatherName() {
		return pcusFatherName;
	}

	public void setPcusFatherName(String pcusFatherName) {
		this.pcusFatherName = pcusFatherName;
	}

	public String getPcusSpouseName() {
		return pcusSpouseName;
	}

	public void setPcusSpouseName(String pcusSpouseName) {
		this.pcusSpouseName = pcusSpouseName;
	}

	public String getPcusGuardianName() {
		return pcusGuardianName;
	}

	public void setPcusGuardianName(String pcusGuardianName) {
		this.pcusGuardianName = pcusGuardianName;
	}

	public Long getPcusMajorOccupationId() {
		return pcusMajorOccupationId;
	}

	public void setPcusMajorOccupationId(Long pcusMajorOccupationId) {
		this.pcusMajorOccupationId = pcusMajorOccupationId;
	}

	public Long getPcusSubOccupationId() {
		return pcusSubOccupationId;
	}

	public void setPcusSubOccupationId(Long pcusSubOccupationId) {
		this.pcusSubOccupationId = pcusSubOccupationId;
	}

	public Long getPcusOccupationId() {
		return pcusOccupationId;
	}

	public void setPcusOccupationId(Long pcusOccupationId) {
		this.pcusOccupationId = pcusOccupationId;
	}

	public Long getPcusResidentialStatusCommonListId() {
		return pcusResidentialStatusCommonListId;
	}

	public void setPcusResidentialStatusCommonListId(Long pcusResidentialStatusCommonListId) {
		this.pcusResidentialStatusCommonListId = pcusResidentialStatusCommonListId;
	}

	public PhysicallyChallengedStatus getPcusPhysicallyChallenged() {
		return pcusPhysicallyChallenged;
	}

	public void setPcusPhysicallyChallenged(PhysicallyChallengedStatus pcusPhysicallyChallenged) {
		this.pcusPhysicallyChallenged = pcusPhysicallyChallenged;
	}

	public DeceaseStatus getPcusDeceaseStatus() {
		return pcusDeceaseStatus;
	}

	public void setPcusDeceaseStatus(DeceaseStatus pcusDeceaseStatus) {
		this.pcusDeceaseStatus = pcusDeceaseStatus;
	}

	public Date getPcusDeceaseDate() {
		return pcusDeceaseDate;
	}

	public void setPcusDeceaseDate(Date pcusDeceaseDate) {
		this.pcusDeceaseDate = pcusDeceaseDate;
	}

	public Long getPcusPersonTypeCommonListId() {
		return pcusPersonTypeCommonListId;
	}

	public void setPcusPersonTypeCommonListId(Long pcusPersonTypeCommonListId) {
		this.pcusPersonTypeCommonListId = pcusPersonTypeCommonListId;
	}

	public Long getPcusLivingConditionCommonListId() {
		return pcusLivingConditionCommonListId;
	}

	public void setPcusLivingConditionCommonListId(Long pcusLivingConditionCommonListId) {
		this.pcusLivingConditionCommonListId = pcusLivingConditionCommonListId;
	}

	public Long getPcusGoverningAuthorityCommonListId() {
		return pcusGoverningAuthorityCommonListId;
	}

	public void setPcusGoverningAuthorityCommonListId(Long pcusGoverningAuthorityCommonListId) {
		this.pcusGoverningAuthorityCommonListId = pcusGoverningAuthorityCommonListId;
	}

	public Date getPcusRegisteredDate() {
		return pcusRegisteredDate;
	}

	public void setPcusRegisteredDate(Date pcusRegisteredDate) {
		this.pcusRegisteredDate = pcusRegisteredDate;
	}

	public Date getPcusOperationStartDate() {
		return pcusOperationStartDate;
	}

	public void setPcusOperationStartDate(Date pcusOperationStartDate) {
		this.pcusOperationStartDate = pcusOperationStartDate;
	}

	public String getPcusRefCode1() {
		return pcusRefCode1;
	}

	public void setPcusRefCode1(String pcusRefCode1) {
		this.pcusRefCode1 = pcusRefCode1;
	}

	public String getPcusRefCode2() {
		return pcusRefCode2;
	}

	public void setPcusRefCode2(String pcusRefCode2) {
		this.pcusRefCode2 = pcusRefCode2;
	}

	public String getPcusRefCode3() {
		return pcusRefCode3;
	}

	public void setPcusRefCode3(String pcusRefCode3) {
		this.pcusRefCode3 = pcusRefCode3;
	}

	public String getPcusCreatedUser() {
		return pcusCreatedUser;
	}

	public void setPcusCreatedUser(String pcusCreatedUser) {
		this.pcusCreatedUser = pcusCreatedUser;
	}

	public Date getPcusCreatedDate() {
		return pcusCreatedDate;
	}

	public void setPcusCreatedDate(Date pcusCreatedDate) {
		this.pcusCreatedDate = pcusCreatedDate;
	}

	public Date getPcusLivingPeriodStartDate() {
		return pcusLivingPeriodStartDate;
	}

	public void setPcusLivingPeriodStartDate(Date pcusLivingPeriodStartDate) {
		this.pcusLivingPeriodStartDate = pcusLivingPeriodStartDate;
	}

	public LivingCategory getPcusLivingCategory() {
		return pcusLivingCategory;
	}

	public void setPcusLivingCategory(LivingCategory pcusLivingCategory) {
		this.pcusLivingCategory = pcusLivingCategory;
	}

	public String getPcusMainGroupCode() {
		return pcusMainGroupCode;
	}

	public void setPcusMainGroupCode(String pcusMainGroupCode) {
		this.pcusMainGroupCode = pcusMainGroupCode;
	}

	public String getPcusSubGroupCode() {
		return pcusSubGroupCode;
	}

	public void setPcusSubGroupCode(String pcusSubGroupCode) {
		this.pcusSubGroupCode = pcusSubGroupCode;
	}

	public String getPcusModifiedUser() {
		return pcusModifiedUser;
	}

	public void setPcusModifiedUser(String pcusModifiedUser) {
		this.pcusModifiedUser = pcusModifiedUser;
	}

	public Date getPcusModifiedDate() {
		return pcusModifiedDate;
	}

	public void setPcusModifiedDate(Date pcusModifiedDate) {
		this.pcusModifiedDate = pcusModifiedDate;
	}

	public String getPcusOrganizationTypeDesc() {
		return pcusOrganizationTypeDesc;
	}

	public void setPcusOrganizationTypeDesc(String pcusOrganizationTypeDesc) {
		this.pcusOrganizationTypeDesc = pcusOrganizationTypeDesc;
	}

	public String getPcusOriginationMethodDesc() {
		return pcusOriginationMethodDesc;
	}

	public void setPcusOriginationMethodDesc(String pcusOriginationMethodDesc) {
		this.pcusOriginationMethodDesc = pcusOriginationMethodDesc;
	}

	public String getPcusNearestBranchDesc() {
		return pcusNearestBranchDesc;
	}

	public void setPcusNearestBranchDesc(String pcusNearestBranchDesc) {
		this.pcusNearestBranchDesc = pcusNearestBranchDesc;
	}

	public String getPcusSectorDesc() {
		return pcusSectorDesc;
	}

	public void setPcusSectorDesc(String pcusSectorDesc) {
		this.pcusSectorDesc = pcusSectorDesc;
	}

	public String getPcusSubSectorDesc() {
		return pcusSubSectorDesc;
	}

	public void setPcusSubSectorDesc(String pcusSubSectorDesc) {
		this.pcusSubSectorDesc = pcusSubSectorDesc;
	}

	public String getPcusIndividualAnnualTurnoverDesc() {
		return pcusIndividualAnnualTurnoverDesc;
	}

	public void setPcusIndividualAnnualTurnoverDesc(String pcusIndividualAnnualTurnoverDesc) {
		this.pcusIndividualAnnualTurnoverDesc = pcusIndividualAnnualTurnoverDesc;
	}

	public String getPcusBusinessAnnualTurnoverDesc() {
		return pcusBusinessAnnualTurnoverDesc;
	}

	public void setPcusBusinessAnnualTurnoverDesc(String pcusBusinessAnnualTurnoverDesc) {
		this.pcusBusinessAnnualTurnoverDesc = pcusBusinessAnnualTurnoverDesc;
	}

	public String getPcusRelatedPartyTypeDesc() {
		return pcusRelatedPartyTypeDesc;
	}

	public void setPcusRelatedPartyTypeDesc(String pcusRelatedPartyTypeDesc) {
		this.pcusRelatedPartyTypeDesc = pcusRelatedPartyTypeDesc;
	}

	public String getPcusAreaDesc() {
		return pcusAreaDesc;
	}

	public void setPcusAreaDesc(String pcusAreaDesc) {
		this.pcusAreaDesc = pcusAreaDesc;
	}

	public String getPcusSecondaryDivisionDesc() {
		return pcusSecondaryDivisionDesc;
	}

	public void setPcusSecondaryDivisionDesc(String pcusSecondaryDivisionDesc) {
		this.pcusSecondaryDivisionDesc = pcusSecondaryDivisionDesc;
	}

	public String getPcusMajorOccupationDesc() {
		return pcusMajorOccupationDesc;
	}

	public void setPcusMajorOccupationDesc(String pcusMajorOccupationDesc) {
		this.pcusMajorOccupationDesc = pcusMajorOccupationDesc;
	}

	public String getPcusSubOccupationDesc() {
		return pcusSubOccupationDesc;
	}

	public void setPcusSubOccupationDesc(String pcusSubOccupationDesc) {
		this.pcusSubOccupationDesc = pcusSubOccupationDesc;
	}

	public String getPcusOccupationDesc() {
		return pcusOccupationDesc;
	}

	public void setPcusOccupationDesc(String pcusOccupationDesc) {
		this.pcusOccupationDesc = pcusOccupationDesc;
	}

	public String getPcusResidentialStatusDesc() {
		return pcusResidentialStatusDesc;
	}

	public void setPcusResidentialStatusDesc(String pcusResidentialStatusDesc) {
		this.pcusResidentialStatusDesc = pcusResidentialStatusDesc;
	}

	public String getPcusPersonTypeDesc() {
		return pcusPersonTypeDesc;
	}

	public void setPcusPersonTypeDesc(String pcusPersonTypeDesc) {
		this.pcusPersonTypeDesc = pcusPersonTypeDesc;
	}

	public String getPcusLivingConditionDesc() {
		return pcusLivingConditionDesc;
	}

	public void setPcusLivingConditionDesc(String pcusLivingConditionDesc) {
		this.pcusLivingConditionDesc = pcusLivingConditionDesc;
	}

	public String getPcusGoverningAuthorityDesc() {
		return pcusGoverningAuthorityDesc;
	}

	public void setPcusGoverningAuthorityDesc(String pcusGoverningAuthorityDesc) {
		this.pcusGoverningAuthorityDesc = pcusGoverningAuthorityDesc;
	}

	public String getPcusGroupId() {
		return pcusGroupId;
	}

	public void setPcusGroupId(String pcusGroupId) {
		this.pcusGroupId = pcusGroupId;
	}

	public String getPcusOrganizationTypeCode() {
		return pcusOrganizationTypeCode;
	}

	public void setPcusOrganizationTypeCode(String pcusOrganizationTypeCode) {
		this.pcusOrganizationTypeCode = pcusOrganizationTypeCode;
	}

	public String getPcusMainGroupDesc() {
		return pcusMainGroupDesc;
	}

	public void setPcusMainGroupDesc(String pcusMainGroupDesc) {
		this.pcusMainGroupDesc = pcusMainGroupDesc;
	}

	public String getPcusSubGroupDesc() {
		return pcusSubGroupDesc;
	}

	public void setPcusSubGroupDesc(String pcusSubGroupDesc) {
		this.pcusSubGroupDesc = pcusSubGroupDesc;
	}

	public String getPcusPersonTypeCode() {
		return pcusPersonTypeCode;
	}

	public void setPcusPersonTypeCode(String pcusPersonTypeCode) {
		this.pcusPersonTypeCode = pcusPersonTypeCode;
	}

	public CustomerPendingStatus getPcusPendingStatus() {
		return pcusPendingStatus;
	}

	public void setPcusPendingStatus(CustomerPendingStatus pcusPendingStatus) {
		this.pcusPendingStatus = pcusPendingStatus;
	}

	public String getPcusSectorCode() {
		return pcusSectorCode;
	}

	public void setPcusSectorCode(String pcusSectorCode) {
		this.pcusSectorCode = pcusSectorCode;
	}

	public String getPcusSubSectorCode() {
		return pcusSubSectorCode;
	}

	public void setPcusSubSectorCode(String pcusSubSectorCode) {
		this.pcusSubSectorCode = pcusSubSectorCode;
	}

	public String getPcusMajorOccupationCode() {
		return pcusMajorOccupationCode;
	}

	public void setPcusMajorOccupationCode(String pcusMajorOccupationCode) {
		this.pcusMajorOccupationCode = pcusMajorOccupationCode;
	}

	public String getPcusSubOccupationCode() {
		return pcusSubOccupationCode;
	}

	public void setPcusSubOccupationCode(String pcusSubOccupationCode) {
		this.pcusSubOccupationCode = pcusSubOccupationCode;
	}

	public String getPcusOccupationCode() {
		return pcusOccupationCode;
	}

	public void setPcusOccupationCode(String pcusOccupationCode) {
		this.pcusOccupationCode = pcusOccupationCode;
	}

	public Long getPcusPenPernId() {
		return pcusPenPernId;
	}

	public void setPcusPenPernId(Long pcusPenPernId) {
		this.pcusPenPernId = pcusPenPernId;
	}
	
	
}
