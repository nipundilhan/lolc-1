package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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
import com.fusionx.lending.origination.enums.CommonYesNoStatus;


@Entity
@Table(name = "guarantor")
public class Guarantor  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name="tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id", nullable=false)
	private LeadInfo leadId;
    
  	@Column(name = "guarantor_id")
  	private Long guarantorId;
  	
  	@Column(name = "pending_guarantor_id")
  	private Long pendingGuarantorId;
  	
  	@Column(name = "first_name")
  	private String firstName;
    
  	@Column(name = "middle_name")
  	private String middleName;
    
  	@Column(name = "last_name")
  	private String lastName;
    
  	@Column(name = "full_name")
  	private String fullName;
    
  	@Column(name = "initials")
  	private String initials;
    
  	@Column(name = "date_of_birth")
  	private Date dateOfBirth;
    
  	@Column(name = "relationship_to_cus")
  	private String relationshipToCus;
    
  	@Column(name = "marital_status")
  	private String maritalStatus;
    
//
//    
//  	@Column(name = "internal_crib_status")
//  	private String internalCribStatus;
//    
//  	@Column(name = "external_crib_status")
//  	private String externalCribStatus;
    
  	@Column(name = "reference_no")
  	private String referenceNo;
    
  	@Column(name = "status")
  	private String status;
  	@Column(name = "created_user")
  	private String createdUser;
  	
  	@Column(name = "created_date")
  	private Timestamp createdDate;
	
  	// Added by Senitha
	@Enumerated(value = EnumType.STRING)
	@Column(name = "based_on_net_income")
	private CommonYesNoStatus basedOnNetIncome;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "based_on_net_worth")
	private CommonYesNoStatus basedOnNetWorth;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "as_an_additional_security")
	private CommonYesNoStatus asAnAdditionalSecurity;
	
	@Column(name = "total_asset_value")
	private BigDecimal totalAssetValue;
	
	@Column(name = "total_liability_value")
	private BigDecimal totalLiabilityValue;
	
	@Column(name = "networth")
	private BigDecimal networth;
  	
  	@Column(name = "modified_user")
  	private String modifiedUser;
  	
  	@Column(name = "modified_date")
  	private Timestamp modifiedDate;
  	// Ended by Senitha
  	
	@Column(name = "pen_per_id")
	private String penPerId;

	@Column(name = "per_id")
	private String perId;

	@Column(name = "per_code")
	private String perCode;
	
	@Column(name = "gender_id")
	private Long genderId;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "title_id")
	private Long titleId;

	@Column(name = "title")
	private String title;
	
	// Added by Senitha
	@Transient
	private List<NetWorthAsset> assets;

	@Transient
	private List<NetWorthLiability> liabilities;
  	// Ended by Senitha
  	
//	@Column(name="blacklst_approve_reject_remark")
//	private String blacklstApproveRejectRemark;
	
	@Column(name="sup_reference_code")
	private String supReferenceCode;
  	
  	@Transient
	private List<IdentificationDetail> identificationDetails;
  	
	@Transient
	private List<BlacklistDetail> blacklistDetails;
	
//	@Column(name="internal_crib_status_id")
//	private Long internalCribStatusId;
//	
//	@Column(name="external_crib_status_id")
//	private Long externalCribStatusId;

	@Transient
	private List<ContactDetail> contactDetails;
	
	@Transient
	private List<AddressDetail> addressDetails;
	
	@Transient
	private List<BusinessIncomeExpense> businessIncomeExpenses;
	
	@Transient
	private List<SalaryIncome> salaryIncomes;
	
	@Transient
	private List<OtherIncome> otherIncomes;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLeadId() {
		return leadId;
	}

	public void setLeadId(LeadInfo leadId) {
		this.leadId = leadId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelationshipToCus() {
		return relationshipToCus;
	}

	public void setRelationshipToCus(String relationshipToCus) {
		this.relationshipToCus = relationshipToCus;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}



//	public String getInternalCribStatus() {
//		return internalCribStatus;
//	}
//
//	public void setInternalCribStatus(String internalCribStatus) {
//		this.internalCribStatus = internalCribStatus;
//	}
//
//	public String getExternalCribStatus() {
//		return externalCribStatus;
//	}
//
//	public void setExternalCribStatus(String externalCribStatus) {
//		this.externalCribStatus = externalCribStatus;
//	}


	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public CommonYesNoStatus getBasedOnNetIncome() {
		return basedOnNetIncome;
	}

	public void setBasedOnNetIncome(CommonYesNoStatus basedOnNetIncome) {
		this.basedOnNetIncome = basedOnNetIncome;
	}

	public CommonYesNoStatus getBasedOnNetWorth() {
		return basedOnNetWorth;
	}

	public void setBasedOnNetWorth(CommonYesNoStatus basedOnNetWorth) {
		this.basedOnNetWorth = basedOnNetWorth;
	}

	public CommonYesNoStatus getAsAnAdditionalSecurity() {
		return asAnAdditionalSecurity;
	}

	public void setAsAnAdditionalSecurity(CommonYesNoStatus asAnAdditionalSecurity) {
		this.asAnAdditionalSecurity = asAnAdditionalSecurity;
	}

	public BigDecimal getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(BigDecimal totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public BigDecimal getTotalLiabilityValue() {
		return totalLiabilityValue;
	}

	public void setTotalLiabilityValue(BigDecimal totalLiabilityValue) {
		this.totalLiabilityValue = totalLiabilityValue;
	}

	public BigDecimal getNetworth() {
		return networth;
	}

	public void setNetworth(BigDecimal networth) {
		this.networth = networth;
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

	public List<NetWorthAsset> getAssets() {
		return assets;
	}

	public void setAssets(List<NetWorthAsset> assets) {
		this.assets = assets;
	}

	public List<NetWorthLiability> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<NetWorthLiability> liabilities) {
		this.liabilities = liabilities;
	}

	public Long getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(Long guarantorId) {
		this.guarantorId = guarantorId;
	}

	public Long getPendingGuarantorId() {
		return pendingGuarantorId;
	}

	public void setPendingGuarantorId(Long pendingGuarantorId) {
		this.pendingGuarantorId = pendingGuarantorId;
	}

	public List<IdentificationDetail> getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(List<IdentificationDetail> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}

	public List<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<AddressDetail> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetail> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public List<BlacklistDetail> getBlacklistDetails() {
		return blacklistDetails;
	}

	public void setBlacklistDetails(List<BlacklistDetail> blacklistDetails) {
		this.blacklistDetails = blacklistDetails;
	}

	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public List<BusinessIncomeExpense> getBusinessIncomeExpenses() {
		return businessIncomeExpenses;
	}

	public void setBusinessIncomeExpenses(List<BusinessIncomeExpense> businessIncomeExpenses) {
		this.businessIncomeExpenses = businessIncomeExpenses;
	}

	public List<SalaryIncome> getSalaryIncomes() {
		return salaryIncomes;
	}

	public void setSalaryIncomes(List<SalaryIncome> salaryIncomes) {
		this.salaryIncomes = salaryIncomes;
	}

	public List<OtherIncome> getOtherIncomes() {
		return otherIncomes;
	}

	public void setOtherIncomes(List<OtherIncome> otherIncomes) {
		this.otherIncomes = otherIncomes;
	}

	public String getSupReferenceCode() {
		return supReferenceCode;
	}

	public void setSupReferenceCode(String supReferenceCode) {
		this.supReferenceCode = supReferenceCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

}
