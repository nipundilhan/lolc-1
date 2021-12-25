package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Eligibility History Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2021      		     			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "eligibility_history")
@Data
public class EligibilityHistory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name="eligibility_id", nullable=false)
	private Long eligibilityId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "age_eligibility_id")
	private Long ageEligiId;
	
	@Column(name = "guarantor_eligibility", nullable=false)
	private Long guarantorEligibility;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	@Column(name = "pen_approved_user", nullable=true, length=255)
	private String penApprovedUser;
	
	@Column(name = "pen_approved_date", nullable=true)
	private Timestamp penApprovedDate;
	
	@Column(name = "pen_rejected_user", nullable=true, length=255)
	private String penRejectedUser;
	
	@Column(name = "pen_rejected_date", nullable=true)
	private Timestamp penRejectedDate;
	
	@Column(name="history_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="history_created_date", nullable=false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAgeEligiId() {
		return ageEligiId;
	}

	public void setAgeEligiId(Long ageEligiId) {
		this.ageEligiId = ageEligiId;
	}

	public Long getGuarantorEligibility() {
		return guarantorEligibility;
	}

	public void setGuarantorEligibility(Long guarantorEligibility) {
		this.guarantorEligibility = guarantorEligibility;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getPenApprovedUser() {
		return penApprovedUser;
	}

	public void setPenApprovedUser(String penApprovedUser) {
		this.penApprovedUser = penApprovedUser;
	}

	public Timestamp getPenApprovedDate() {
		return penApprovedDate;
	}

	public void setPenApprovedDate(Timestamp penApprovedDate) {
		this.penApprovedDate = penApprovedDate;
	}

	public String getPenRejectedUser() {
		return penRejectedUser;
	}

	public void setPenRejectedUser(String penRejectedUser) {
		this.penRejectedUser = penRejectedUser;
	}

	public Timestamp getPenRejectedDate() {
		return penRejectedDate;
	}

	public void setPenRejectedDate(Timestamp penRejectedDate) {
		this.penRejectedDate = penRejectedDate;
	}

	public Long getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}
}	
