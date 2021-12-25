package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.MobileNotebookStatusEnum;

import lombok.Data;

/**
 * Mobile Notebook Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "mobile_notebook")
public class MobileNotebook extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "identification_type_id")
	private Long identificationTypeId;
	
	@Column(name = "nic_no")
	private String nicNo;
	
	@Column(name = "customer_full_name")
	private String customerFullName;
	
	@Column(name = "required_loan_amount")
	private BigDecimal requiredLoanAmount;
	
	@Column(name = "contact_type_id")
	private Long contactTypeId;
	
	@Column(name = "contact_number")
	private Long contactNumber;
	
	@Column(name = "customer_living_area")
	private String customerLivingArea;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "security_type_id")
	private Long securityTypeId;
	
	@Column(name = "security_sub_type_id")
	private Long securitySubTypeId;
	
	@Column(name = "spouse_profession_id")
	private Long spouseProfessionId;
	
	@Column(name = "priority_level_id")
	private Long priorityLevelId;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "next_meeting_date")
	private Date nextMeetingDate;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private MobileNotebookStatusEnum status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "onboarding_status")
	private MobileNotebookStatusEnum onboardingStatus;
	
	@Column(name = "deleted_user")
	private String deletedUser;
	
	@Column(name = "deleted_date")
	private Timestamp deletedDate;
	
	@Column(name = "deleted_note")
	private String deletedNote;
	
	@Transient
	private String identificationTypeDescription;
	
	@Transient
	private String identificationTypeCode;
	
	@Transient
	private String contactTypeDescription;
	
	@Transient
	private String contactTypeCode;
	
	@Transient
	private String securityTypeName;
	
	@Transient
	private String securitySubTypeName;
	
	@Transient
	private String spouseProfessionName;
	
	@Transient
	private String priorityLevelName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getIdentificationTypeId() {
		return identificationTypeId;
	}

	public void setIdentificationTypeId(Long identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}

	public String getNicNo() {
		return nicNo;
	}

	public void setNicNo(String nicNo) {
		this.nicNo = nicNo;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public BigDecimal getRequiredLoanAmount() {
		return requiredLoanAmount;
	}

	public void setRequiredLoanAmount(BigDecimal requiredLoanAmount) {
		this.requiredLoanAmount = requiredLoanAmount;
	}

	public Long getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(Long contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCustomerLivingArea() {
		return customerLivingArea;
	}

	public void setCustomerLivingArea(String customerLivingArea) {
		this.customerLivingArea = customerLivingArea;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getSecurityTypeId() {
		return securityTypeId;
	}

	public void setSecurityTypeId(Long securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public Long getSecuritySubTypeId() {
		return securitySubTypeId;
	}

	public void setSecuritySubTypeId(Long securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	public Long getSpouseProfessionId() {
		return spouseProfessionId;
	}

	public void setSpouseProfessionId(Long spouseProfessionId) {
		this.spouseProfessionId = spouseProfessionId;
	}

	public Long getPriorityLevelId() {
		return priorityLevelId;
	}

	public void setPriorityLevelId(Long priorityLevelId) {
		this.priorityLevelId = priorityLevelId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(Date nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
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

	public MobileNotebookStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MobileNotebookStatusEnum status) {
		this.status = status;
	}

	public String getIdentificationTypeDescription() {
		return identificationTypeDescription;
	}

	public void setIdentificationTypeDescription(String identificationTypeDescription) {
		this.identificationTypeDescription = identificationTypeDescription;
	}

	public String getContactTypeDescription() {
		return contactTypeDescription;
	}

	public void setContactTypeDescription(String contactTypeDescription) {
		this.contactTypeDescription = contactTypeDescription;
	}

	public String getSecurityTypeName() {
		return securityTypeName;
	}

	public void setSecurityTypeName(String securityTypeName) {
		this.securityTypeName = securityTypeName;
	}

	public String getSecuritySubTypeName() {
		return securitySubTypeName;
	}

	public void setSecuritySubTypeName(String securitySubTypeName) {
		this.securitySubTypeName = securitySubTypeName;
	}

	public String getSpouseProfessionName() {
		return spouseProfessionName;
	}

	public void setSpouseProfessionName(String spouseProfessionName) {
		this.spouseProfessionName = spouseProfessionName;
	}

	public String getDeletedUser() {
		return deletedUser;
	}

	public void setDeletedUser(String deletedUser) {
		this.deletedUser = deletedUser;
	}

	public Timestamp getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Timestamp deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getDeletedNote() {
		return deletedNote;
	}

	public void setDeletedNote(String deletedNote) {
		this.deletedNote = deletedNote;
	}

	public MobileNotebookStatusEnum getOnboardingStatus() {
		return onboardingStatus;
	}

	public void setOnboardingStatus(MobileNotebookStatusEnum onboardingStatus) {
		this.onboardingStatus = onboardingStatus;
	}

	public String getIdentificationTypeCode() {
		return identificationTypeCode;
	}

	public void setIdentificationTypeCode(String identificationTypeCode) {
		this.identificationTypeCode = identificationTypeCode;
	}

	public String getContactTypeCode() {
		return contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}

	public String getPriorityLevelName() {
		return priorityLevelName;
	}

	public void setPriorityLevelName(String priorityLevelName) {
		this.priorityLevelName = priorityLevelName;
	}

}
