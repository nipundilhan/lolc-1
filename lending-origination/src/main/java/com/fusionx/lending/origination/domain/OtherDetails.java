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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Other Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Data
@Table(name = "other_details")
public class OtherDetails   extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id")  
	private LeadInfo leadInfo;
	
	@Transient
	private Long leadId;
	
	@Column(name = "sector_id")
	private Long sectorId;
	
	@Column(name = "sector_code")
	private String sectorCode;
	
	@Transient
	private String sectorName;
	
	@Column(name = "sub_sector_id")
	private Long subSectorId;
	
	@Column(name = "sub_sector_code")
	private String subSectorCode;
	
	@Transient
	private String subSectorName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "purpose_id", nullable=false)
	private CommonList purpose;
	
	@Transient
	private Long purposeId;
	
	@Column(name = "purpose_code")
	private String purposeCode;
	
	@Transient
	private String purposeName;
	
	@Column(name = "savings_acc_required")
	private String savingsAccRequired;
	
	@Column(name = "comments")
	private String comment;
	
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
	
	
	
	

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfo) {
		this.leadInfo = leadInfo;
	}
	
	public Long getLeadId() {
		return leadInfo.getId();
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public Long getSubSectorId() {
		return subSectorId;
	}

	public void setSubSectorId(Long subSectorId) {
		this.subSectorId = subSectorId;
	}

	public String getSubSectorCode() {
		return subSectorCode;
	}

	public void setSubSectorCode(String subSectorCode) {
		this.subSectorCode = subSectorCode;
	}



	public CommonList getPurpose() {
		return purpose;
	}

	public void setPurpose(CommonList purpose) {
		this.purpose = purpose;
	}
	
	public Long getPurposeId() {
		return purpose.getId();
	}

	public String getPurposeCode() {
		return purposeCode;
	}

	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}
	
	

	public String getPurposeName() {
		return purposeName;
	}

	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	public String getSavingsAccRequired() {
		return savingsAccRequired;
	}

	public void setSavingsAccRequired(String savingsAccRequired) {
		this.savingsAccRequired = savingsAccRequired;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSubSectorName() {
		return subSectorName;
	}

	public void setSubSectorName(String subSectorName) {
		this.subSectorName = subSectorName;
	}
	
	

}
