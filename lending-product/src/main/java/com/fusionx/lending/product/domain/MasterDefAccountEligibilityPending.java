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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "master_def_account_eligibility_pending")
@Data
public class MasterDefAccountEligibilityPending   extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_account_eligibility_id", nullable=true)
	private MasterDefAccountRule masterDefAccountEligibility;
	
	@Column(name = "master_definition_id" ,nullable = false)
	private Long masterDefinitionId;
	
	@Column(name = "account_standard_id" ,nullable = false)
	private Long accountStandardId;	
	
	@Column(name = "setof_account_id" ,nullable = false)
	private Long setofAccountId;	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
		
	@Column(name = "pcreated_user", length=255, nullable=false)
	private String pcreatedUser;
	
	@Column(name = "pcreated_date", nullable=false)
	private Timestamp pcreatedDate;

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
	public String getPcreatedUser() {
		return pcreatedUser;
	}

	public void setPcreatedUser(String pcreatedUser) {
		this.pcreatedUser = pcreatedUser;
	}

	public Timestamp getPcreatedDate() {
		return pcreatedDate;
	}

	public void setpCreatedDate(Timestamp pcreatedDate) {
		this.pcreatedDate = pcreatedDate;
	}

}
