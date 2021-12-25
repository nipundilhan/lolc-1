package com.fusionx.lending.transaction.domain;

import java.io.Serializable;
import java.util.Date;

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
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   07-12-2021      		     FXL-2113	    Ishan      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "waive_off_approval_users")
public class WaiveOffApprovalUsers extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1733170237224283733L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;

	@Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private CommonStatus status;

    @Column(name = "created_user" , length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date",nullable = false)
    private Date createdDate;

    @Column(name = "modified_user", length = 255, nullable = true)
    private String modifiedUser;

    @Column(name = "modified_date",nullable = true)
    private Date modifiedDate;
    
    @Column(name = "user_table_id",nullable = false)
    private Long userTableId;
    
    @Column(name = "user_id",nullable = false)
    private String userId;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "waive_off_approval_group_id", nullable = false)
    private WaiveOffApprovalGroup waiveOffApprovalGroup;

    @Column(name = "user_name", nullable = true)
    private String userName;
    
    @Transient
    private Long approvalGroupId;
    
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Long getUserTableId() {
		return userTableId;
	}

	public void setUserTableId(Long userTableId) {
		this.userTableId = userTableId;
	}

	public WaiveOffApprovalGroup getWaiveOffApprovalGroup() {
		return waiveOffApprovalGroup;
	}

	public void setWaiveOffApprovalGroup(WaiveOffApprovalGroup waiveOffApprovalGroup) {
		this.waiveOffApprovalGroup = waiveOffApprovalGroup;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getApprovalGroupId() {
		return waiveOffApprovalGroup.getId();
	}

	public void setApprovalGroupId(Long approvalGroupId) {
		this.approvalGroupId = approvalGroupId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	} 
	
}
