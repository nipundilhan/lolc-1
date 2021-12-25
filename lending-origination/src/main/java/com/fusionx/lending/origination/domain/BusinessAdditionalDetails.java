package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.SourceTypeEnum;


/**
 * Business Additional Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@Entity
@Table(name = "business_additional_details")
public class BusinessAdditionalDetails extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -7661888325648799145L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_type_id" ,nullable=false)
	private BusinessType businessType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_sub_type_id" ,nullable=false)
	private BusinessSubType businessSubType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ownership_id" ,nullable=false)
	private CommonList ownership;
	
	@Column(name = "business_name",nullable=false)
	private String businessName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_size_id",nullable=false )
	private CommonList businessSize;
	
	@Column(name = "no_of_years_in_business",nullable=false)
	private String noOfYearsInBusiness;
	
	@Column(name = "busi_opertaion_start_date",nullable=false)
	private Timestamp busiOpertaionStartDate;
	
	@Column(name = "business_regi_no")
	private String businessRegiNo;
	
	@Column(name = "business_regi_date")
	private Timestamp businessRegiDate;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "profit_margin")
	private double profitMargin;
	
	@Column(name = "comments")
	private String comment;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "source_type", length=20)
	private SourceTypeEnum sourceType;
	
	@Column(name = "no_of_branches")
	private Long noOfBranches;
	
	@Column(name = "skills_of_key_person")
	private String skillsOfKeyPerson;
	
	@Column(name = "previous_busi_history")
	private String previousBusiHistory;
	
	@Column(name = "busi_continuity_plan")
	private String busiContinuityPlan;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id")
	private LeadInfo lead;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "linked_person_id")
	private LinkedPerson linkedPerson;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private Long businessTypesId;
	
	@Transient
	private String businessTypesName;
	
	@Transient
	private Long businessSubTypesId;
	
	@Transient
	private String businessSubTypesName;
	
	@Transient
	private Long ownershipsId;
	
	@Transient
	private String ownershipsName;
	
	@Transient
	private Long businessSizesId;
	
	@Transient
	private String businessSizeName;
	
	@Transient
	private Long leadsId;
	
	@Transient
	private Long customersId;
	
	@Transient
	private String customersFullname;
	
	@Transient
	private Long linkedPersonsId;
	
	@Transient
	private String linkedPersonsName;
	
	@Transient
	private List<BusinessDocumentDetails> businessDocumentDetails;
	
	@Transient
	private List<BusinessRiskDetails> businessRiskDetails;
	
	@Transient
	private List<BusinessAssetDetails> businessAssetDetails;
	
	@Transient
	private List<BusinessEmploymentDetails> businessEmploymentDetails;
	
	@Transient
	private List<BusinessClearanceDetails> businessClearanceDetails;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public CommonList getOwnership() {
		return ownership;
	}

	public void setOwnership(CommonList ownership) {
		this.ownership = ownership;
	}

	public BusinessSubType getBusinessSubType() {
		return businessSubType;
	}

	public void setBusinessSubType(BusinessSubType businessSubType) {
		this.businessSubType = businessSubType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public CommonList getBusinessSize() {
		return businessSize;
	}

	public void setBusinessSize(CommonList businessSize) {
		this.businessSize = businessSize;
	}

	public String getNoOfYearsInBusiness() {
		return noOfYearsInBusiness;
	}

	public void setNoOfYearsInBusiness(String noOfYearsInBusiness) {
		this.noOfYearsInBusiness = noOfYearsInBusiness;
	}

	public Timestamp getBusiOpertaionStartDate() {
		return busiOpertaionStartDate;
	}

	public void setBusiOpertaionStartDate(Timestamp busiOpertaionStartDate) {
		this.busiOpertaionStartDate = busiOpertaionStartDate;
	}

	public String getBusinessRegiNo() {
		return businessRegiNo;
	}

	public void setBusinessRegiNo(String businessRegiNo) {
		this.businessRegiNo = businessRegiNo;
	}

	public Timestamp getBusinessRegiDate() {
		return businessRegiDate;
	}

	public void setBusinessRegiDate(Timestamp businessRegiDate) {
		this.businessRegiDate = businessRegiDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(double profitMargin) {
		this.profitMargin = profitMargin;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public SourceTypeEnum getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceTypeEnum sourceType) {
		this.sourceType = sourceType;
	}

	public Long getNoOfBranches() {
		return noOfBranches;
	}

	public void setNoOfBranches(Long noOfBranches) {
		this.noOfBranches = noOfBranches;
	}

	public String getSkillsOfKeyPerson() {
		return skillsOfKeyPerson;
	}

	public void setSkillsOfKeyPerson(String skillsOfKeyPerson) {
		this.skillsOfKeyPerson = skillsOfKeyPerson;
	}

	public String getPreviousBusiHistory() {
		return previousBusiHistory;
	}

	public void setPreviousBusiHistory(String previousBusiHistory) {
		this.previousBusiHistory = previousBusiHistory;
	}

	public String getBusiContinuityPlan() {
		return busiContinuityPlan;
	}

	public void setBusiContinuityPlan(String busiContinuityPlan) {
		this.busiContinuityPlan = busiContinuityPlan;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public LeadInfo getLead() {
		return lead;
	}

	public void setLead(LeadInfo lead) {
		this.lead = lead;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LinkedPerson getLinkedPerson() {
		return linkedPerson;
	}

	public void setLinkedPerson(LinkedPerson linkedPerson) {
		this.linkedPerson = linkedPerson;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Long getLeadsId() {
		return leadsId;
	}

	public void setLeadsId(Long leadsId) {
		this.leadsId = leadsId;
	}

	public Long getCustomersId() {
		return customersId;
	}

	public void setCustomersId(Long customersId) {
		this.customersId = customersId;
	}

	public String getCustomersFullname() {
		return customersFullname;
	}

	public void setCustomersFullname(String customersFullname) {
		this.customersFullname = customersFullname;
	}

	public Long getLinkedPersonsId() {
		return linkedPersonsId;
	}

	public void setLinkedPersonsId(Long linkedPersonsId) {
		this.linkedPersonsId = linkedPersonsId;
	}

	public String getLinkedPersonsName() {
		return linkedPersonsName;
	}

	public void setLinkedPersonsName(String linkedPersonsName) {
		this.linkedPersonsName = linkedPersonsName;
	}

	public List<BusinessDocumentDetails> getBusinessDocumentDetails() {
		return businessDocumentDetails;
	}

	public void setBusinessDocumentDetails(List<BusinessDocumentDetails> businessDocumentDetails) {
		this.businessDocumentDetails = businessDocumentDetails;
	}

	public List<BusinessRiskDetails> getBusinessRiskDetails() {
		return businessRiskDetails;
	}

	public void setBusinessRiskDetails(List<BusinessRiskDetails> businessRiskDetails) {
		this.businessRiskDetails = businessRiskDetails;
	}

	public List<BusinessAssetDetails> getBusinessAssetDetails() {
		return businessAssetDetails;
	}

	public void setBusinessAssetDetails(List<BusinessAssetDetails> businessAssetDetails) {
		this.businessAssetDetails = businessAssetDetails;
	}

	public List<BusinessEmploymentDetails> getBusinessEmploymentDetails() {
		return businessEmploymentDetails;
	}

	public void setBusinessEmploymentDetails(List<BusinessEmploymentDetails> businessEmploymentDetails) {
		this.businessEmploymentDetails = businessEmploymentDetails;
	}

	public List<BusinessClearanceDetails> getBusinessClearanceDetails() {
		return businessClearanceDetails;
	}

	public void setBusinessClearanceDetails(List<BusinessClearanceDetails> businessClearanceDetails) {
		this.businessClearanceDetails = businessClearanceDetails;
	}

	public Long getBusinessTypesId() {
		return businessTypesId;
	}

	public void setBusinessTypesId(Long businessTypesId) {
		this.businessTypesId = businessTypesId;
	}

	public String getBusinessTypesName() {
		return businessTypesName;
	}

	public void setBusinessTypesName(String businessTypesName) {
		this.businessTypesName = businessTypesName;
	}

	public Long getBusinessSubTypesId() {
		return businessSubTypesId;
	}

	public void setBusinessSubTypesId(Long businessSubTypesId) {
		this.businessSubTypesId = businessSubTypesId;
	}

	public String getBusinessSubTypesName() {
		return businessSubTypesName;
	}

	public void setBusinessSubTypesName(String businessSubTypesName) {
		this.businessSubTypesName = businessSubTypesName;
	}

	public Long getOwnershipsId() {
		return ownershipsId;
	}

	public void setOwnershipsId(Long ownershipsId) {
		this.ownershipsId = ownershipsId;
	}

	public String getOwnershipsName() {
		return ownershipsName;
	}

	public void setOwnershipsName(String ownershipsName) {
		this.ownershipsName = ownershipsName;
	}

	public Long getBusinessSizesId() {
		return businessSizesId;
	}

	public void setBusinessSizesId(Long businessSizesId) {
		this.businessSizesId = businessSizesId;
	}

	public String getBusinessSizeName() {
		return businessSizeName;
	}

	public void setBusinessSizeName(String businessSizeName) {
		this.businessSizeName = businessSizeName;
	}
	
}
