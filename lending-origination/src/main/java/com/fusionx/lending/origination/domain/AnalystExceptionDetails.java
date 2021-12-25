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
import com.fusionx.lending.origination.enums.AnalystExceptionApprovalStatus;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * 	Analyst Exception Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Entity
@Table(name = "analyst_exception_details")
public class AnalystExceptionDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5516358116626370547L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "analyst_detail_id", nullable=false)
	private AnalystDetails analystDetail;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "exception_type_id", nullable=false)
	private ExceptionType exceptionType;
	
	@Column(name = "exception_authority_id", nullable=false)
	private Long exceptionAuthorityId;

	@Column(name = "exception_details", nullable=false)
	private String exceptionDetails;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approval_status", length=20)
	private AnalystExceptionApprovalStatus approvalStatus;
	
	@Column(name = "approval_user")
	private String approvalUser;
	
	@Column(name = "approval_date")
	private Timestamp approvalDate;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private Long exceptionTypesId;
	
	@Transient
	private String exceptionTypesName;
	
	@Transient
	private String exceptionAuthorityUserName;
	
	@Transient
	private List<AnalystExceptionDocuments> analystExceptionDocuments;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public AnalystDetails getAnalystDetail() {
		return analystDetail;
	}

	public void setAnalystDetail(AnalystDetails analystDetail) {
		this.analystDetail = analystDetail;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Long getExceptionAuthorityId() {
		return exceptionAuthorityId;
	}

	public void setExceptionAuthorityId(Long exceptionAuthorityId) {
		this.exceptionAuthorityId = exceptionAuthorityId;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}
	
	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public AnalystExceptionApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(AnalystExceptionApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Timestamp getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Timestamp approvalDate) {
		this.approvalDate = approvalDate;
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
	
	public Long getExceptionTypesId() {
		return exceptionTypesId;
	}

	public void setExceptionTypesId(Long exceptionTypesId) {
		this.exceptionTypesId = exceptionTypesId;
	}
	
	public String getExceptionTypesName() {
		return exceptionTypesName;
	}

	public void setExceptionTypesName(String exceptionTypesName) {
		this.exceptionTypesName = exceptionTypesName;
	}
	
	public List<AnalystExceptionDocuments> getAnalystExceptionDocuments() {
		return analystExceptionDocuments;
	}

	public void setAnalystExceptionDocuments(List<AnalystExceptionDocuments> analystExceptionDocuments) {
		this.analystExceptionDocuments = analystExceptionDocuments;
	}
	
	public String getExceptionAuthorityUserName() {
		return exceptionAuthorityUserName;
	}

	public void setExceptionAuthorityUserName(String exceptionAuthorityUserName) {
		this.exceptionAuthorityUserName = exceptionAuthorityUserName;
	}

}
