package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.core.MasterPropertyBase;

import lombok.Data;

/**
 * 	Approval Level 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021                 FX-6419       RavishikaS     Created
 *    
 ********************************************************************************************************
*/

@Entity
@Data
@Table(name = "approval_level")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class ApprovalLevel extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 9204576036907573339L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "limit_val_required")
	private String limitValRequired;
	
	@Column(name = "sequence")
	private Long sequence;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "sync_ts")
	private Timestamp syncTs;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getLimitValRequired() {
		return limitValRequired;
	}

	public void setLimitValRequired(String limitValRequired) {
		this.limitValRequired = limitValRequired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Timestamp syncTs) {
		this.syncTs = syncTs;
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
