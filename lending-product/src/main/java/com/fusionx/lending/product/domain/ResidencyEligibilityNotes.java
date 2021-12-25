package com.fusionx.lending.product.domain;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "residency_eligibility_notes")
@Data
public class ResidencyEligibilityNotes extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 632551114;

	@Column(name="tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "residency_eligibility_id", nullable=true)
	private ResidencyEligibility residencyEligi;
	
	@Transient
	private Long residencyEligibilityTd;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="status")
	private String status;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="modified_user")
	private String modifiedUser;
	
	@Column(name="modified_date")
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public ResidencyEligibility getResidencyEligi() {
		return residencyEligi;
	}

	public void setResidencyEligi(ResidencyEligibility residencyEligi) {
		this.residencyEligi = residencyEligi;
	}

	public Long getResidencyEligibilityTd() {
		return residencyEligi.getId();
	}

	public void setResidencyEligibilityTd(Long residencyEligibilityTd) {
		this.residencyEligibilityTd = residencyEligibilityTd;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

}
