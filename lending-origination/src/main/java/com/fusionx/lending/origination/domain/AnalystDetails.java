package com.fusionx.lending.origination.domain;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;


/**
 * 	Analyst Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@Data
@Entity
@Table(name = "analyst_details")
public class AnalystDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2254312199358241395L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "analyst_type",length=20 , nullable=false)
	private String analystType;
	
	@Column(name = "analyst_user_id", nullable=false)
	private String analystId;
	
	@Column(name = "observation", nullable=false)
	private String observation;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id", nullable=false)
	private LeadInfo leadInfo;
	
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

	@Transient
	private Long leadId;
	
	@Transient
	private List<AnalystExceptionDetails> analystExceptionDetails;
	
	@Transient
	private String analystUserName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAnalystType() {
		return analystType;
	}

	public void setAnalystType(String analystType) {
		this.analystType = analystType;
	}

	public String getAnalystUserId() {
		return analystId;
	}

	public void setAnalystUserId(String analystId) {
		this.analystId = analystId;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String description) {
		this.observation = description;
	}
	
	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfor) {
		this.leadInfo = leadInfor;
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
	
	public List<AnalystExceptionDetails> getAnalystExceptionDetails() {
		return analystExceptionDetails;
	}

	public void setAnalystExceptionDetails(List<AnalystExceptionDetails> analystExceptionDetails) {
		this.analystExceptionDetails = analystExceptionDetails;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	
	public String getAnalystUserName() {
		return analystUserName;
	}

	public void setAnalystUserName(String analystUserName) {
		this.analystUserName = analystUserName;
	}

}
