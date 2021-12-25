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
/**
 * Approval Category Level Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-09-2021    		 	 FXL-840 	Dilki        Created
 *    
 ********************************************************************************************************
 */

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "app_cat_level_details")
@Data
public class ApprovalCategoryLevelDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1488791796285119682L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_category_details_id", nullable = true)
	private ApprovalCategoryDetails approvalCategoryDetailsId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_category_levels_id", nullable = true)
	private ApprovalCategoryLevels approvalCategoryLevelsId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

	@Column(name = "name", length = 70, nullable = false)
	private String name;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "mandatory", length = 20, nullable = true)
	private CommonStatus mandatory;

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

	public CommonStatus getMandatory() {
		return mandatory;
	}

	public void setMandatory(CommonStatus mandatory) {
		this.mandatory = mandatory;
	}

	public ApprovalCategoryDetails getApprovalCategoryDetailsId() {
		return approvalCategoryDetailsId;
	}

	public void setApprovalCategoryDetailsId(ApprovalCategoryDetails approvalCategoryDetailsId) {
		this.approvalCategoryDetailsId = approvalCategoryDetailsId;
	}

	public ApprovalCategoryLevels getApprovalCategoryLevelsId() {
		return approvalCategoryLevelsId;
	}

	public void setApprovalCategoryLevelsId(ApprovalCategoryLevels approvalCategoryLevelsId) {
		this.approvalCategoryLevelsId = approvalCategoryLevelsId;
	}

}
