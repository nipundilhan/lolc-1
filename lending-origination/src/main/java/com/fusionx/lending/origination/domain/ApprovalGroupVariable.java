package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "approval_group_variable")
@Data
public class ApprovalGroupVariable extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5165279073454724571L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pre_approval_group_id", nullable = true)
	private ApprovalGroup approvalGroupId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "app_category_variable_id", nullable = true)
	private ApprovalCategoryApplicableVariables approvalCategoryVariableId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

	@Column(name = "value", length = 70, nullable = false)
	private String value;

	@Column(name = "from_date", nullable = true)
	private Timestamp fromDate;

	@Column(name = "to_date", nullable = true)
	private Timestamp toDate;

	@Column(name = "from_amount", nullable = false)
	private BigDecimal fromAmount;

	@Column(name = "to_amount", nullable = false)
	private BigDecimal toAmount;

	@Column(name = "eqaul_string", nullable = true, length = 255)
	private String eqaulString;

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

	public ApprovalGroup getApprovalGroupId() {
		return approvalGroupId;
	}

	public void setApprovalGroupId(ApprovalGroup approvalGroupId) {
		this.approvalGroupId = approvalGroupId;
	}

	public ApprovalCategoryApplicableVariables getApprovalCategoryVariableId() {
		return approvalCategoryVariableId;
	}

	public void setApprovalCategoryVariableId(ApprovalCategoryApplicableVariables approvalCategoryVariableId) {
		this.approvalCategoryVariableId = approvalCategoryVariableId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
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

	public String getEqaulString() {
		return eqaulString;
	}

	public void setEqaulString(String eqaulString) {
		this.eqaulString = eqaulString;
	}
}
