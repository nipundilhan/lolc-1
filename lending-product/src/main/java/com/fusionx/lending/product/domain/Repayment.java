package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Repayment Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "repayment")
@Data
public class Repayment extends BaseEntity implements Serializable {
	
	
	private static final long serialVersionUID = 7800030053528144960L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_type_id", nullable = false)
	private RepaymentType repaymentType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_frequency_id", nullable = false)
	private RepaymentFrequency repaymentFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_amount_type_id", nullable = false)
	private RepaymentAmountType repaymentAmountType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
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
	
	@Transient
	private Long repaymentTypeId;
	
	@Transient
	private Long RepaymentFrequencyId;
	
	@Transient
	private String RepaymentFrequencyName;
	
	@Transient
	private String repaymentTypeName;

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

	public RepaymentType getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}

	public RepaymentFrequency getRepaymentFrequency() {
		return repaymentFrequency;
	}

	public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
		this.repaymentFrequency = repaymentFrequency;
	}

	public RepaymentAmountType getRepaymentAmountType() {
		return repaymentAmountType;
	}

	public void setRepaymentAmountType(RepaymentAmountType repaymentAmountType) {
		this.repaymentAmountType = repaymentAmountType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
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

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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

	public Long getRepaymentTypeId() {
		if(repaymentType != null) {
			return repaymentType.getId();
		} else {
			return null;
		}
	}

	public Long getRepaymentFrequencyId() {
		if(repaymentFrequency != null) {
			return repaymentFrequency.getId();
		} else {
			return null;
		}
	}

	public String getRepaymentFrequencyName() {
		if(repaymentFrequency != null) {
			return repaymentFrequency.getName();
		} else {
			return null;
		}
	}

	public String getRepaymentTypeName() {
		if(repaymentType != null) {
			return repaymentType.getName();
		} else {
			return null;
		}
	}
	
	
}	
