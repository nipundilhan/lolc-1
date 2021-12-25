package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "master_def_account_eligibility_pending")
@Data
public class MasterDefAccountEligibilityHistory   extends BaseEntity implements Serializable {
	

	private static final long serialVersionUID = 961297319632868080L;
	
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name="master_def_account_eligibility_id", nullable=false)
	private Long masterDefAccountEligibilityId;
	
	@Column(name = "master_definition_id" ,nullable = false)
	private Long masterDefinitionId;
	
	@Column(name = "account_standard_id" ,nullable = false)
	private Long accountStandardId;	
	
	@Column(name = "setof_account_id" ,nullable = false)
	private Long setofAccountId;	
	
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

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setMasterDefinitionId(Long masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}

	public Long getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setAccountStandardId(Long accountStandardId) {
		this.accountStandardId = accountStandardId;
	}

	public Long getAccountStandardId() {
		return accountStandardId;
	}
	
	public void setSetofAccountId(Long setofAccountId) {
		this.setofAccountId = setofAccountId;
	}

	public Long getSetofAccountId() {
		return setofAccountId;
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

}
