package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class CustomerBasicInfoResponseResource extends PersonBasicInfoResponseResource{

	private Long id;
	
	private String cusReferenceCode;
	
	private Long cusOrganizationTypeCommonListId;
	
	private String cusOrganizationTypeDesc;
	
	private String cusOrganizationTypeCode;
	
	private Long cusOriginationMethodCommonListId;
	
	private String cusOriginationMethodDesc;
	
	private Day cusPreferredDay;
	
	private String cusPreferredTime;
	
	private Long cusNearestBranchId;
	
	private String cusNearestBranchDesc;
	
	private String cusLocationLongitude;
	
	private String cusLocationLatitude;
	
	private Long cusProductDetailId;
	
	private WithinBranchArea cusWithinBranchArea;
	
	private String cusComment;
	
	private Long cusSectorId;
	
	private String cusSectorCode;
	
	private String cusSectorDesc;
	
	private Long cusSubSectorId;
	
	private String cusSubSectorCode;
	
	private String cusSubSectorDesc;
	
	private String cusNotesOnBusiness;
	
	private Long cusIndividualAnnualTurnoverId;
	
	private String cusIndividualAnnualTurnoverDesc;
	
	private Long cusBusinessAnnualTurnoverId;
	
	private String cusBusinessAnnualTurnoverDesc;
	
	private RelatedPartyStatus cusRelatedPartyStatus;
	
	private Long cusRelatedPartyTypeCommonListId;
	
	private String cusRelatedPartyTypeDesc;
	
	private String cusRelatedPartyComment;
	
	private KmpStatus cusKMPStatus;

	private Long cusMainGroupId;
	
	private String cusMainGroupCode;
	
	private String cusMainGroupDesc;
	
	private Long cusSubGroupId;
	
	private String cusSubGroupCode;
	
	private String cusSubGroupDesc;
	
	private Long cusAreaId;
	
	private String cusAreaDesc;
	
	private Long cusSecondaryDivisionId;
	
	private String cusSecondaryDivisionDesc;
	
	private SmsAlertStatus cusSmsAlertStatus;
	
	private CustomerStatus cusStatus;
	
	private String cusFatherName;
	
	private String cusSpouseName;
	
	private String cusGuardianName;
	
	private Long cusMajorOccupationId;
	
	private String cusMajorOccupationCode;
	
	private String cusMajorOccupationDesc;
	
	private Long cusSubOccupationId;
	
	private String cusSubOccupationCode;
	
	private String cusSubOccupationDesc;
	
	private Long cusOccupationId;
	
	private String cusOccupationCode;
	
	private String cusOccupationDesc;
	
	private Long cusResidentialStatusCommonListId;
	
	private String cusResidentialStatusDesc;
	
	private Date cusLivingPeriodStartDate;
	
	private LivingCategory cusLivingCategory;
	
	private PhysicallyChallengedStatus cusPhysicallyChallenged;
	
	private DeceaseStatus cusDeceaseStatus;
	
	private Date cusDeceaseDate;
	
	private Long cusPersonTypeCommonListId;
	
	private String cusPersonTypeDesc;
	
	private String cusPersonTypeCode;
	
	private Long cusLivingConditionCommonListId;
	
	private String cusLivingConditionDesc;
	
	private Long cusGoverningAuthorityCommonListId;
	
	private String cusGoverningAuthorityDesc;
	
	private Date cusRegisteredDate;
	
	private Date cusOperationStartDate;
	
	private String cusRefCode1;
	
	private String cusRefCode2;
	
	private String cusRefCode3;
	
	private String cusGroupId;
	
	private String cusCreatedUser;
	
	private Date cusCreatedDate;
	
	private String cusModifiedUser;
	
	private Date cusModifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCusReferenceCode() {
		return cusReferenceCode;
	}

	public void setCusReferenceCode(String cusReferenceCode) {
		this.cusReferenceCode = cusReferenceCode;
	}

	public Long getCusOrganizationTypeCommonListId() {
		return cusOrganizationTypeCommonListId;
	}

	public void setCusOrganizationTypeCommonListId(Long cusOrganizationTypeCommonListId) {
		this.cusOrganizationTypeCommonListId = cusOrganizationTypeCommonListId;
	}

	public Long getCusOriginationMethodCommonListId() {
		return cusOriginationMethodCommonListId;
	}

	public void setCusOriginationMethodCommonListId(Long cusOriginationMethodCommonListId) {
		this.cusOriginationMethodCommonListId = cusOriginationMethodCommonListId;
	}

	public Day getCusPreferredDay() {
		return cusPreferredDay;
	}

	public void setCusPreferredDay(Day cusPreferredDay) {
		this.cusPreferredDay = cusPreferredDay;
	}

	public String getCusPreferredTime() {
		return cusPreferredTime;
	}

	public void setCusPreferredTime(String cusPreferredTime) {
		this.cusPreferredTime = cusPreferredTime;
	}

	public Long getCusNearestBranchId() {
		return cusNearestBranchId;
	}

	public void setCusNearestBranchId(Long cusNearestBranchId) {
		this.cusNearestBranchId = cusNearestBranchId;
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

	public WithinBranchArea getCusWithinBranchArea() {
		return cusWithinBranchArea;
	}

	public void setCusWithinBranchArea(WithinBranchArea cusWithinBranchArea) {
		this.cusWithinBranchArea = cusWithinBranchArea;
	}

	public String getCusComment() {
		return cusComment;
	}

	public void setCusComment(String cusComment) {
		this.cusComment = cusComment;
	}

	public Long getCusSectorId() {
		return cusSectorId;
	}

	public void setCusSectorId(Long cusSectorId) {
		this.cusSectorId = cusSectorId;
	}

	public Long getCusSubSectorId() {
		return cusSubSectorId;
	}

	public void setCusSubSectorId(Long cusSubSectorId) {
		this.cusSubSectorId = cusSubSectorId;
	}
	
	public String getCusNotesOnBusiness() {
		return cusNotesOnBusiness;
	}

	public void setCusNotesOnBusiness(String cusNotesOnBusiness) {
		this.cusNotesOnBusiness = cusNotesOnBusiness;
	}
	
	public Long getCusIndividualAnnualTurnoverId() {
		return cusIndividualAnnualTurnoverId;
	}

	public void setCusIndividualAnnualTurnoverId(Long cusIndividualAnnualTurnoverId) {
		this.cusIndividualAnnualTurnoverId = cusIndividualAnnualTurnoverId;
	}

	public Long getCusBusinessAnnualTurnoverId() {
		return cusBusinessAnnualTurnoverId;
	}

	public void setCusBusinessAnnualTurnoverId(Long cusBusinessAnnualTurnoverId) {
		this.cusBusinessAnnualTurnoverId = cusBusinessAnnualTurnoverId;
	}

	public RelatedPartyStatus getCusRelatedPartyStatus() {
		return cusRelatedPartyStatus;
	}

	public void setCusRelatedPartyStatus(RelatedPartyStatus cusRelatedPartyStatus) {
		this.cusRelatedPartyStatus = cusRelatedPartyStatus;
	}

	public Long getCusRelatedPartyTypeCommonListId() {
		return cusRelatedPartyTypeCommonListId;
	}

	public void setCusRelatedPartyTypeCommonListId(Long cusRelatedPartyTypeCommonListId) {
		this.cusRelatedPartyTypeCommonListId = cusRelatedPartyTypeCommonListId;
	}

	public String getCusRelatedPartyComment() {
		return cusRelatedPartyComment;
	}

	public void setCusRelatedPartyComment(String cusRelatedPartyComment) {
		this.cusRelatedPartyComment = cusRelatedPartyComment;
	}

	public KmpStatus getCusKMPStatus() {
		return cusKMPStatus;
	}

	public void setCusKMPStatus(KmpStatus cusKMPStatus) {
		this.cusKMPStatus = cusKMPStatus;
	}

	public Long getCusMainGroupId() {
		return cusMainGroupId;
	}

	public void setCusMainGroupId(Long cusMainGroupId) {
		this.cusMainGroupId = cusMainGroupId;
	}

	public Long getCusSubGroupId() {
		return cusSubGroupId;
	}

	public void setCusSubGroupId(Long cusSubGroupId) {
		this.cusSubGroupId = cusSubGroupId;
	}

	public Long getCusAreaId() {
		return cusAreaId;
	}

	public void setCusAreaId(Long cusAreaId) {
		this.cusAreaId = cusAreaId;
	}

	public Long getCusSecondaryDivisionId() {
		return cusSecondaryDivisionId;
	}

	public void setCusSecondaryDivisionId(Long cusSecondaryDivisionId) {
		this.cusSecondaryDivisionId = cusSecondaryDivisionId;
	}

	public SmsAlertStatus getCusSmsAlertStatus() {
		return cusSmsAlertStatus;
	}

	public void setCusSmsAlertStatus(SmsAlertStatus cusSmsAlertStatus) {
		this.cusSmsAlertStatus = cusSmsAlertStatus;
	}

	public CustomerStatus getCusStatus() {
		return cusStatus;
	}

	public void setCusStatus(CustomerStatus cusStatus) {
		this.cusStatus = cusStatus;
	}

	public String getCusFatherName() {
		return cusFatherName;
	}

	public void setCusFatherName(String cusFatherName) {
		this.cusFatherName = cusFatherName;
	}

	public String getCusSpouseName() {
		return cusSpouseName;
	}

	public void setCusSpouseName(String cusSpouseName) {
		this.cusSpouseName = cusSpouseName;
	}

	public String getCusGuardianName() {
		return cusGuardianName;
	}

	public void setCusGuardianName(String cusGuardianName) {
		this.cusGuardianName = cusGuardianName;
	}

	public Long getCusMajorOccupationId() {
		return cusMajorOccupationId;
	}

	public void setCusMajorOccupationId(Long cusMajorOccupationId) {
		this.cusMajorOccupationId = cusMajorOccupationId;
	}

	public Long getCusSubOccupationId() {
		return cusSubOccupationId;
	}

	public void setCusSubOccupationId(Long cusSubOccupationId) {
		this.cusSubOccupationId = cusSubOccupationId;
	}

	public Long getCusOccupationId() {
		return cusOccupationId;
	}

	public void setCusOccupationId(Long cusOccupationId) {
		this.cusOccupationId = cusOccupationId;
	}

	public Long getCusResidentialStatusCommonListId() {
		return cusResidentialStatusCommonListId;
	}

	public void setCusResidentialStatusCommonListId(Long cusResidentialStatusCommonListId) {
		this.cusResidentialStatusCommonListId = cusResidentialStatusCommonListId;
	}

	public PhysicallyChallengedStatus getCusPhysicallyChallenged() {
		return cusPhysicallyChallenged;
	}

	public void setCusPhysicallyChallenged(PhysicallyChallengedStatus cusPhysicallyChallenged) {
		this.cusPhysicallyChallenged = cusPhysicallyChallenged;
	}

	public DeceaseStatus getCusDeceaseStatus() {
		return cusDeceaseStatus;
	}

	public void setCusDeceaseStatus(DeceaseStatus cusDeceaseStatus) {
		this.cusDeceaseStatus = cusDeceaseStatus;
	}

	public Date getCusDeceaseDate() {
		return cusDeceaseDate;
	}

	public void setCusDeceaseDate(Date cusDeceaseDate) {
		this.cusDeceaseDate = cusDeceaseDate;
	}

	public Long getCusPersonTypeCommonListId() {
		return cusPersonTypeCommonListId;
	}

	public void setCusPersonTypeCommonListId(Long cusPersonTypeCommonListId) {
		this.cusPersonTypeCommonListId = cusPersonTypeCommonListId;
	}

	public Long getCusLivingConditionCommonListId() {
		return cusLivingConditionCommonListId;
	}

	public void setCusLivingConditionCommonListId(Long cusLivingConditionCommonListId) {
		this.cusLivingConditionCommonListId = cusLivingConditionCommonListId;
	}

	public Long getCusGoverningAuthorityCommonListId() {
		return cusGoverningAuthorityCommonListId;
	}

	public void setCusGoverningAuthorityCommonListId(Long cusGoverningAuthorityCommonListId) {
		this.cusGoverningAuthorityCommonListId = cusGoverningAuthorityCommonListId;
	}

	public Date getCusRegisteredDate() {
		return cusRegisteredDate;
	}

	public void setCusRegisteredDate(Date cusRegisteredDate) {
		this.cusRegisteredDate = cusRegisteredDate;
	}

	public Date getCusOperationStartDate() {
		return cusOperationStartDate;
	}

	public void setCusOperationStartDate(Date cusOperationStartDate) {
		this.cusOperationStartDate = cusOperationStartDate;
	}

	public String getCusCreatedUser() {
		return cusCreatedUser;
	}

	public void setCusCreatedUser(String cusCreatedUser) {
		this.cusCreatedUser = cusCreatedUser;
	}

	public Date getCusCreatedDate() {
		return cusCreatedDate;
	}

	public void setCusCreatedDate(Date cusCreatedDate) {
		this.cusCreatedDate = cusCreatedDate;
	}

	public String getCusModifiedUser() {
		return cusModifiedUser;
	}

	public void setCusModifiedUser(String cusModifiedUser) {
		this.cusModifiedUser = cusModifiedUser;
	}

	public Date getCusModifiedDate() {
		return cusModifiedDate;
	}

	public void setCusModifiedDate(Date cusModifiedDate) {
		this.cusModifiedDate = cusModifiedDate;
	}

	public Long getCusProductDetailId() {
		return cusProductDetailId;
	}

	public void setCusProductDetailId(Long cusProductDetailId) {
		this.cusProductDetailId = cusProductDetailId;
	}

	public String getCusOrganizationTypeDesc() {
		return cusOrganizationTypeDesc;
	}

	public void setCusOrganizationTypeDesc(String cusOrganizationTypeDesc) {
		this.cusOrganizationTypeDesc = cusOrganizationTypeDesc;
	}

	public String getCusOriginationMethodDesc() {
		return cusOriginationMethodDesc;
	}

	public void setCusOriginationMethodDesc(String cusOriginationMethodDesc) {
		this.cusOriginationMethodDesc = cusOriginationMethodDesc;
	}

	public String getCusNearestBranchDesc() {
		return cusNearestBranchDesc;
	}

	public void setCusNearestBranchDesc(String cusNearestBranchDesc) {
		this.cusNearestBranchDesc = cusNearestBranchDesc;
	}

	public String getCusSectorDesc() {
		return cusSectorDesc;
	}

	public void setCusSectorDesc(String cusSectorDesc) {
		this.cusSectorDesc = cusSectorDesc;
	}

	public String getCusSubSectorDesc() {
		return cusSubSectorDesc;
	}

	public void setCusSubSectorDesc(String cusSubSectorDesc) {
		this.cusSubSectorDesc = cusSubSectorDesc;
	}

	public String getCusIndividualAnnualTurnoverDesc() {
		return cusIndividualAnnualTurnoverDesc;
	}

	public void setCusIndividualAnnualTurnoverDesc(String cusIndividualAnnualTurnoverDesc) {
		this.cusIndividualAnnualTurnoverDesc = cusIndividualAnnualTurnoverDesc;
	}

	public String getCusBusinessAnnualTurnoverDesc() {
		return cusBusinessAnnualTurnoverDesc;
	}

	public void setCusBusinessAnnualTurnoverDesc(String cusBusinessAnnualTurnoverDesc) {
		this.cusBusinessAnnualTurnoverDesc = cusBusinessAnnualTurnoverDesc;
	}

	public String getCusRelatedPartyTypeDesc() {
		return cusRelatedPartyTypeDesc;
	}

	public void setCusRelatedPartyTypeDesc(String cusRelatedPartyTypeDesc) {
		this.cusRelatedPartyTypeDesc = cusRelatedPartyTypeDesc;
	}

	public String getCusAreaDesc() {
		return cusAreaDesc;
	}

	public void setCusAreaDesc(String cusAreaDesc) {
		this.cusAreaDesc = cusAreaDesc;
	}

	public String getCusSecondaryDivisionDesc() {
		return cusSecondaryDivisionDesc;
	}

	public void setCusSecondaryDivisionDesc(String cusSecondaryDivisionDesc) {
		this.cusSecondaryDivisionDesc = cusSecondaryDivisionDesc;
	}

	public String getCusMajorOccupationDesc() {
		return cusMajorOccupationDesc;
	}

	public void setCusMajorOccupationDesc(String cusMajorOccupationDesc) {
		this.cusMajorOccupationDesc = cusMajorOccupationDesc;
	}

	public String getCusSubOccupationDesc() {
		return cusSubOccupationDesc;
	}

	public void setCusSubOccupationDesc(String cusSubOccupationDesc) {
		this.cusSubOccupationDesc = cusSubOccupationDesc;
	}

	public String getCusOccupationDesc() {
		return cusOccupationDesc;
	}

	public void setCusOccupationDesc(String cusOccupationDesc) {
		this.cusOccupationDesc = cusOccupationDesc;
	}

	public String getCusResidentialStatusDesc() {
		return cusResidentialStatusDesc;
	}

	public void setCusResidentialStatusDesc(String cusResidentialStatusDesc) {
		this.cusResidentialStatusDesc = cusResidentialStatusDesc;
	}

	public String getCusPersonTypeDesc() {
		return cusPersonTypeDesc;
	}

	public void setCusPersonTypeDesc(String cusPersonTypeDesc) {
		this.cusPersonTypeDesc = cusPersonTypeDesc;
	}

	public String getCusLivingConditionDesc() {
		return cusLivingConditionDesc;
	}

	public void setCusLivingConditionDesc(String cusLivingConditionDesc) {
		this.cusLivingConditionDesc = cusLivingConditionDesc;
	}

	public String getCusGoverningAuthorityDesc() {
		return cusGoverningAuthorityDesc;
	}

	public void setCusGoverningAuthorityDesc(String cusGoverningAuthorityDesc) {
		this.cusGoverningAuthorityDesc = cusGoverningAuthorityDesc;
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

	public Date getCusLivingPeriodStartDate() {
		return cusLivingPeriodStartDate;
	}

	public void setCusLivingPeriodStartDate(Date cusLivingPeriodStartDate) {
		this.cusLivingPeriodStartDate = cusLivingPeriodStartDate;
	}

	public LivingCategory getCusLivingCategory() {
		return cusLivingCategory;
	}

	public void setCusLivingCategory(LivingCategory cusLivingCategory) {
		this.cusLivingCategory = cusLivingCategory;
	}

	public String getCusOrganizationTypeCode() {
		return cusOrganizationTypeCode;
	}

	public void setCusOrganizationTypeCode(String cusOrganizationTypeCode) {
		this.cusOrganizationTypeCode = cusOrganizationTypeCode;
	}

	public String getCusMainGroupCode() {
		return cusMainGroupCode;
	}

	public void setCusMainGroupCode(String cusMainGroupCode) {
		this.cusMainGroupCode = cusMainGroupCode;
	}

	public String getCusMainGroupDesc() {
		return cusMainGroupDesc;
	}

	public void setCusMainGroupDesc(String cusMainGroupDesc) {
		this.cusMainGroupDesc = cusMainGroupDesc;
	}

	public String getCusSubGroupCode() {
		return cusSubGroupCode;
	}

	public void setCusSubGroupCode(String cusSubGroupCode) {
		this.cusSubGroupCode = cusSubGroupCode;
	}

	public String getCusSubGroupDesc() {
		return cusSubGroupDesc;
	}

	public void setCusSubGroupDesc(String cusSubGroupDesc) {
		this.cusSubGroupDesc = cusSubGroupDesc;
	}

	public String getCusPersonTypeCode() {
		return cusPersonTypeCode;
	}

	public void setCusPersonTypeCode(String cusPersonTypeCode) {
		this.cusPersonTypeCode = cusPersonTypeCode;
	}

	public String getCusSectorCode() {
		return cusSectorCode;
	}

	public void setCusSectorCode(String cusSectorCode) {
		this.cusSectorCode = cusSectorCode;
	}

	public String getCusSubSectorCode() {
		return cusSubSectorCode;
	}

	public void setCusSubSectorCode(String cusSubSectorCode) {
		this.cusSubSectorCode = cusSubSectorCode;
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

	public String getCusOccupationCode() {
		return cusOccupationCode;
	}

	public void setCusOccupationCode(String cusOccupationCode) {
		this.cusOccupationCode = cusOccupationCode;
	}
	
	
}
