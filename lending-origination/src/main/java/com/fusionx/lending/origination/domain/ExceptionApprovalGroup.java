package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

/**
 * 	Exception Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Entity
@Data
@Table(name = "exception_approval_group")
public class ExceptionApprovalGroup extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2254312199358241395L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code",length=4, nullable=false)
	private String code;
	
	@Column(name = "name",length=100, nullable=false)
	private String name;
	
	@Column(name = "description", length=100)
	private String description;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
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
	
	

}
