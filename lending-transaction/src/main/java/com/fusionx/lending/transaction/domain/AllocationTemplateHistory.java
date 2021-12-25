package com.fusionx.lending.transaction.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

import lombok.Data;

@Entity
@Data
@Table(name = "allocation_template_history")
public class AllocationTemplateHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3634097749926327380L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "allocation_template_id",nullable=false)
	private Long allocationTemplateId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "loan_account_status_id", nullable=true)
	private Long loanAccountStatusId;
	
	@Column(name = "loan_account_status_code", nullable=true)
	private String loanAccountStatusCode;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name="his_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="his_created_date", nullable=false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getAllocationTemplateId() {
		return allocationTemplateId;
	}

	public void setAllocationTemplateId(Long allocationTemplateId) {
		this.allocationTemplateId = allocationTemplateId;
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

	public Long getLoanAccountStatusId() {
		return loanAccountStatusId;
	}

	public void setLoanAccountStatusId(Long loanAccountStatusId) {
		this.loanAccountStatusId = loanAccountStatusId;
	}

	public String getLoanAccountStatusCode() {
		return loanAccountStatusCode;
	}

	public void setLoanAccountStatusCode(String loanAccountStatusCode) {
		this.loanAccountStatusCode = loanAccountStatusCode;
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
