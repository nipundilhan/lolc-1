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
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 *Penal Interest Type Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-08-2020  	                        Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "penal_interest_type_details")
public class PenalInterestTypeDetails extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -378813810499184309L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_type_id", nullable=false)
	private PenalInterestType penalInterestType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "sub_trans_type_id")
	private Long subTransTypeId;
	
	@Column(name = "subTransTypeCode", length=4, nullable=false)
	private String subTransTypeCode;
	
	@Column(name = "sub_trans_type_description", length=255, nullable=false)
	private String subTransTypeDesc;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public PenalInterestType getPenalInterestType() {
		return penalInterestType;
	}

	public void setPenalInterestType(PenalInterestType penalInterestType) {
		this.penalInterestType = penalInterestType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Long getSubTransTypeId() {
		return subTransTypeId;
	}

	public void setSubTransTypeId(Long subTransTypeId) {
		this.subTransTypeId = subTransTypeId;
	}

	public String getSubTransTypeCode() {
		return subTransTypeCode;
	}

	public void setSubTransTypeCode(String subTransTypeCode) {
		this.subTransTypeCode = subTransTypeCode;
	}

	public String getSubTransTypeDesc() {
		return subTransTypeDesc;
	}

	public void setSubTransTypeDesc(String subTransTypeDesc) {
		this.subTransTypeDesc = subTransTypeDesc;
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
