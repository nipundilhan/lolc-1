package com.fusionx.lending.product.domain;

/**
 * Penal Interest Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-08-2021      		                Dilhan      Created
 *    
 ********************************************************************************************************
 */

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "penal_interest")
@Data
public class PenalInterest extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -1969905430945195400L;

	@Column(name = "template_code",  nullable=true)
	private String code;
	
	@Column(name = "template_name", length=70, nullable=false)
	private String name;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	private Timestamp syncTs;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<PenalTierBandSet> penalTierBandSet;

	public PenalInterest() {
		super();
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<PenalTierBandSet> getPenalTierBandSet() {
		return penalTierBandSet;
	}

	public void setPenalTierBandSet(List<PenalTierBandSet> penalTierBandSet) {
		this.penalTierBandSet = penalTierBandSet;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Timestamp syncTs) {
		this.syncTs = syncTs;
	}
}
