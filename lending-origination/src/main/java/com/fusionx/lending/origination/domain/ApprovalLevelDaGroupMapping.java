package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.core.MasterPropertyBase;

import lombok.Data;


@Entity
@Data
@Table(name = "approval_level_da_group_map")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class ApprovalLevelDaGroupMapping  extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 0000000000001;

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "da_group_id", nullable=true)
	private DelegationAuthorityGroup daGroup;
	

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "approval_level_id", nullable=true)
	private ApprovalLevel approvalLevel;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	/*@Transient
	private ApprovalLevel level;
	
	@Transient
	private DelegationAuthorityGroup delegationAuthorityGroup;*/

	public DelegationAuthorityGroup getDaGroup() {
		return daGroup;
	}

	public void setDaGroup(DelegationAuthorityGroup daGroup) {
		this.daGroup = daGroup;
	}

	public ApprovalLevel getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(ApprovalLevel approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

/*	public ApprovalLevel getLevel() {
		return level;
	}

	public void setLevel(ApprovalLevel level) {
		this.level = level;
	}*/

/*	public DelegationAuthorityGroup getDelegationAuthorityGroup() {
		return delegationAuthorityGroup;
	}

	public void setDelegationAuthorityGroup(DelegationAuthorityGroup delegationAuthorityGroup) {
		this.delegationAuthorityGroup = delegationAuthorityGroup;
	}*/

	
	
}
