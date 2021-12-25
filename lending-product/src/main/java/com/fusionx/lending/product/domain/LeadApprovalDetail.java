package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.LeadStatusEnum;
import com.fusionx.lending.product.enums.PostingTypeEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Lending Account Detail
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-12-2021      		                Achini      Created
 *
 ********************************************************************************************************
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="lead_approval_details")
@NamedQuery(name="LeadApprovalDetail.findAll", query="SELECT l FROM LeadApprovalDetail l")
public class LeadApprovalDetail extends BaseEntity  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "action", length=10, nullable=false)
	private LeadStatusEnum action;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="action_taken_date")
	private Date actionTakenDate;

	@Column(name="acton_taken_by")
	private String actonTakenBy;

	@Column(name="approval_group_id")
	private Long approvalGroupId;

	@Column(name="approval_group_user_id")
	private Long approvalGroupUserId;

	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="created_user")
	private String createdUser;

	private int cycle;

	@Column(name="lead_id")
	private Long leadId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(name="modified_user")
	private String modifiedUser;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;

	@Column(name="tenant_id")
	private String tenantId;

	

	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LeadStatusEnum getAction() {
		return this.action;
	}

	public void setAction(LeadStatusEnum action) {
		this.action = action;
	}

	public Date getActionTakenDate() {
		return this.actionTakenDate;
	}

	public void setActionTakenDate(Date actionTakenDate) {
		this.actionTakenDate = actionTakenDate;
	}

	public String getActonTakenBy() {
		return this.actonTakenBy;
	}

	public void setActonTakenBy(String actonTakenBy) {
		this.actonTakenBy = actonTakenBy;
	}

	public Long getApprovalGroupId() {
		return this.approvalGroupId;
	}

	public void setApprovalGroupId(Long approvalGroupId) {
		this.approvalGroupId = approvalGroupId;
	}

	public Long getApprovalGroupUserId() {
		return this.approvalGroupUserId;
	}

	public void setApprovalGroupUserId(Long approvalGroupUserId) {
		this.approvalGroupUserId = approvalGroupUserId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return this.createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public int getCycle() {
		return this.cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public Long getLeadId() {
		return this.leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedUser() {
		return this.modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	

}
