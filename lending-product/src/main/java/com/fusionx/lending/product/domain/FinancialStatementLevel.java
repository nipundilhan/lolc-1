package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Financial Statement Level Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021      	FXL-356	  FXL-591	Ishan        Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "financial_statement_level")
public class FinancialStatementLevel extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2515241000554962901L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=255, nullable=false)
	private String name;
	
	@Column(name = "description", length=255, nullable=true)
	private String description;
	
	@Column(name = "sequence", length=255, nullable=false)
	private Long sequence;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=255, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	
	
	public String getTenantId() {
		return tenantId;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Long getSequence() {
		return sequence;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
