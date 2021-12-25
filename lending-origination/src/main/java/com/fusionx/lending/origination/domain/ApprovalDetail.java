package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.core.MasterPropertyBase;

import lombok.Data;

@Entity
@Data
@Table(name = "approval_details")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class ApprovalDetail extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "da_group_id", nullable=true)
	private DelegationAuthorityGroup daGroup;
	

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_level_id", nullable=true)
	private ApprovalLevel approvalLevel;
	
	@Column(name = "tenant_id", length=10, nullable=true)
	private String tenantId;
	
	@Column(name = "group_credit_exposure",  nullable=true)
	private BigDecimal groupCreditExposure;
	
	@Column(name = "individual_credit_exposure",  nullable=true)
	private BigDecimal individualCreditExposure;
	
	@Column(name = "dscr",  nullable=true)
	private BigDecimal DSCR;
	
	@Column(name = "credit_score",  nullable=true)
	private BigDecimal creditScore;
	
	@Column(name = "overall_credit_risk",  nullable=true)
	private BigDecimal overallCreditRisk;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_category_id", nullable=true)
	private ApprovalCategory approvalCategory;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_Id", nullable=true)
	private LeadInfo Lead;
	
	@Column(name = "notes",  nullable=true)
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "comment_group_id", nullable=true)
	private CommonList commentGrop;
	
	@Column(name = "approval_comment",  nullable=true)
	private String comment;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	public DelegationAuthorityGroup getDaGroup() {
		return daGroup;
	}

	public void setDaGroup(DelegationAuthorityGroup daGroup) {
		this.daGroup = daGroup;
	}

	public ApprovalLevel getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(ApprovalLevel approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BigDecimal getGroupCreditExposure() {
		return groupCreditExposure;
	}

	public void setGroupCreditExposure(BigDecimal groupCreditExposure) {
		this.groupCreditExposure = groupCreditExposure;
	}

	public BigDecimal getIndividualCreditExposure() {
		return individualCreditExposure;
	}

	public void setIndividualCreditExposure(BigDecimal individualCreditExposure) {
		this.individualCreditExposure = individualCreditExposure;
	}

	public BigDecimal getDSCR() {
		return DSCR;
	}

	public void setDSCR(BigDecimal dSCR) {
		DSCR = dSCR;
	}

	public BigDecimal getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(BigDecimal creditScore) {
		this.creditScore = creditScore;
	}

	public BigDecimal getOverallCreditRisk() {
		return overallCreditRisk;
	}

	public void setOverallCreditRisk(BigDecimal overallCreditRisk) {
		this.overallCreditRisk = overallCreditRisk;
	}

	public ApprovalCategory getApprovalCategory() {
		return approvalCategory;
	}

	public void setApprovalCategory(ApprovalCategory approvalCategory) {
		this.approvalCategory = approvalCategory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CommonList getCommentGrop() {
		return commentGrop;
	}

	public void setCommentGrop(CommonList commentGrop) {
		this.commentGrop = commentGrop;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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


	public LeadInfo getLead() {
		return Lead;
	}

	public void setLead(LeadInfo lead) {
		Lead = lead;
	}

}
