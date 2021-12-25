package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * DA Limit Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "da_limit")
@Data
public class DaLimit extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "authority_group_id", nullable=false)
	private DelegationAuthorityGroup delegationAuthorityGroup;
	
	@Column(name = "da_level")
	private String daLevel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_category_id", nullable=false)
	private ApprovalCategory approvalCategory;
	
	@Column(name = "da_limit_value")
	private BigDecimal daLimitValue;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private String authorityGroupId;
	
	@Transient
	private String authorityGroupCode;
	
	@Transient
	private String authorityGroupName;
	
	@Transient
	private String approvalCatId;
	
	@Transient
	private String approvalCatName;
	

	public String getAuthorityGroupCode() {
		return authorityGroupCode;
	}

	public void setAuthorityGroupCode(String authorityGroupCode) {
		this.authorityGroupCode = authorityGroupCode;
	}

	public String getAuthorityGroupId() {
		return authorityGroupId;
	}

	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}

	public String getAuthorityGroupName() {
		return authorityGroupName;
	}

	public void setAuthorityGroupName(String authorityGroupName) {
		this.authorityGroupName = authorityGroupName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public DelegationAuthorityGroup getDelegationAuthorityGroup() {
		return delegationAuthorityGroup;
	}

	public void setDelegationAuthorityGroup(DelegationAuthorityGroup delegationAuthorityGroup) {
		this.delegationAuthorityGroup = delegationAuthorityGroup;
	}


	public String getDaLevel() {
		return daLevel;
	}

	public void setDaLevel(String daLevel) {
		this.daLevel = daLevel;
	}

	public ApprovalCategory getApprovalCategory() {
		return approvalCategory;
	}

	public void setApprovalCategory(ApprovalCategory approvalCategory) {
		this.approvalCategory = approvalCategory;
	}

	public BigDecimal getDaLimitValue() {
		return daLimitValue;
	}

	public void setDaLimitValue(BigDecimal daLimitValue) {
		this.daLimitValue = daLimitValue;
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

	public Long getApprovalCatId() {
		if(approvalCategory!=null)
			return approvalCategory.getId();
		else
			return null;
	}

	public void setApprovalCatId(String approvalCatId) {
		this.approvalCatId = approvalCatId;
	}

	public String getApprovalCatName() {
		if(approvalCategory!=null)
			return approvalCategory.getName();
		else
			return null;
	}

	public void setApprovalCatName(String approvalCatName) {
		this.approvalCatName = approvalCatName;
	}

	
}
