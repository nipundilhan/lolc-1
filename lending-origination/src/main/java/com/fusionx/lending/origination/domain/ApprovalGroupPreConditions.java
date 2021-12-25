package com.fusionx.lending.origination.domain;

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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "approval_group_pre_conditions")
@Data
public class ApprovalGroupPreConditions extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5176954117509121838L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_category_id", nullable = true)
	private ApprovalCategoryDetails approvalCategoryId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pre_approval_group_id", nullable = true)
	private ApprovalGroup approvalGroupId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_level_id", nullable = true)
	private ApprovalCategoryLevels approvalLevelId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_mandatory", length = 20, nullable = false)
	private CommonStatus isMandatory;

	@Column(name = "sequence", nullable = false)
	private Long sequence;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public ApprovalCategoryDetails getApprovalCategoryId() {
		return approvalCategoryId;
	}

	public void setApprovalCategoryId(ApprovalCategoryDetails approvalCategoryId) {
		this.approvalCategoryId = approvalCategoryId;
	}

	public ApprovalGroup getApprovalGroupId() {
		return approvalGroupId;
	}

	public void setApprovalGroupId(ApprovalGroup approvalGroupId) {
		this.approvalGroupId = approvalGroupId;
	}

	public ApprovalCategoryLevels getApprovalLevelId() {
		return approvalLevelId;
	}

	public void setApprovalLevelId(ApprovalCategoryLevels approvalLevelId) {
		this.approvalLevelId = approvalLevelId;
	}

	public CommonStatus getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(CommonStatus isMandatory) {
		this.isMandatory = isMandatory;
	}

}
