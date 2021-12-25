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


@Entity
@Data
@Table(name = "delegation_authority_group")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class DelegationAuthorityGroup  extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=20, nullable=false)
	private String code;
	
	@Column(name = "name", length=250, nullable=false)
	private String name;
	
	@Column(name = "description", length=2500, nullable=false)
	private String description;

	@Column(name = "status", length=30, nullable=false)
	private String status;
	
	//no of minimum users to be approved
	@Column(name = "min_users_to_be_approved" , nullable=false)
	private Long noOfMinUsersToBeApproved;
	
	@Column(name = "da_order", nullable=false)
	private Long daOrder;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
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

	public Long getNoOfMinUsersToBeApproved() {
		return noOfMinUsersToBeApproved;
	}

	public void setNoOfMinUsersToBeApproved(Long noOfMinUsersToBeApproved) {
		this.noOfMinUsersToBeApproved = noOfMinUsersToBeApproved;
	}

	public Long getDaOrder() {
		return daOrder;
	}

	public void setDaOrder(Long daOrder) {
		this.daOrder = daOrder;
	}


	
}
