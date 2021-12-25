package com.fusionx.lending.transaction.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   03-12-2021      		     FXL-2111	    Ishan      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "waive_off_approval_group")
public class WaiveOffApprovalGroup extends BaseEntity {

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length = 4, nullable = false)
    private String code;
	
	@Column(name = "name", length = 70, nullable = false)
	private String name;
	
	@Column(name = "description", length = 350, nullable = true)
	private String description;

	@Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CommonStatus status;
	
	@Column(name = "authority_limit",nullable = false)
	private BigDecimal authorityLimit;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "future_outst_allow",nullable = false)
    private CommonStatus futureOutstAllow;

    @Column(name = "created_user" , length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date",nullable = false)
    private Date createdDate;

    @Column(name = "modified_user", length = 255, nullable = true)
    private String modifiedUser;

    @Column(name = "modified_date",nullable = true)
    private Date modifiedDate;

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

	public BigDecimal getAuthorityLimit() {
		return authorityLimit;
	}

	public void setAuthorityLimit(BigDecimal authorityLimit) {
		this.authorityLimit = authorityLimit;
	}

	public CommonStatus getFutureOutstAllow() {
		return futureOutstAllow;
	}

	public void setFutureOutstAllow(CommonStatus futureOutstAllow) {
		this.futureOutstAllow = futureOutstAllow;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
