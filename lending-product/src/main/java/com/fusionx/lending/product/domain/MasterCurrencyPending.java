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
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Master Def Currency Eligibility Pending
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "master_currency_pending")
@Data
public class MasterCurrencyPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -3054612846459528551L;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_currency_id", nullable = false)
	private MasterCurrency masterCurrency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable = false)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_id", nullable = false)
	private MasterDefinition masterDefinition;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_pending_id", nullable = false)
	private MasterDefinitionPending masterDefinitionPending;
	
	@Column(name = "currency_id", nullable=false)
	private Long currencyId;
	
	@Column(name = "currency_name", nullable=false)
    private String currencyName;
	
	@Column(name = "num_decimal_places", nullable=false)
	private Long numOfDecimalPlaces;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "created_user", nullable=false, length=255)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "pen_approved_user", nullable=true, length=255)
	private String penApprovedUser;
	
	@Column(name = "pen_approved_date", nullable=true)
	private Timestamp penApprovedDate;
	
	@Column(name = "pen_rejected_user", nullable=true, length=255)
	private String penRejectedUser;
	
	@Column(name = "pen_rejected_date", nullable=true)
	private Timestamp penRejectedDate;
	
	public MasterCurrency getMasterCurrency() {
		return masterCurrency;
	}

	public void setMasterCurrency(MasterCurrency masterCurrency) {
		this.masterCurrency = masterCurrency;
	}
	
	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public MasterDefinition getMasterDefinition() {
		return masterDefinition;
	}

	public void setMasterDefinition(MasterDefinition masterDefinition) {
		this.masterDefinition = masterDefinition;
	}
	
	public MasterDefinitionPending getMasterDefinitionPending() {
		return masterDefinitionPending;
	}

	public void setMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending) {
		this.masterDefinitionPending = masterDefinitionPending;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public Long getNumOfDecimalPlaces() {
		return numOfDecimalPlaces;
	}

	public void setNumOfDecimalPlaces(Long numOfDecimalPlaces) {
		this.numOfDecimalPlaces = numOfDecimalPlaces;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	
	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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
	
	public String getPenApprovedUser() {
		return penApprovedUser;
	}

	public void setPenApprovedUser(String penApprovedUser) {
		this.penApprovedUser = penApprovedUser;
	}

	public Timestamp getPenApprovedDate() {
		return penApprovedDate;
	}

	public void setPenApprovedDate(Timestamp penApprovedDate) {
		this.penApprovedDate = penApprovedDate;
	}

	public String getPenRejectedUser() {
		return penRejectedUser;
	}

	public void setPenRejectedUser(String penRejectedUser) {
		this.penRejectedUser = penRejectedUser;
	}

	public Timestamp getPenRejectedDate() {
		return penRejectedDate;
	}

	public void setPenRejectedDate(Timestamp penRejectedDate) {
		this.penRejectedDate = penRejectedDate;
	}

}
