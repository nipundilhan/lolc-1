package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Master Currency History
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "master_currency_history")
@Data
public class MasterCurrencyHistory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -6922975435389826861L;
	
	@Column(name = "master_currency_id", nullable = false)
	private Long masterCurrencyId;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@Column(name = "master_definition_id", nullable = false)
	private Long masterDefinitionId;
	
	@Column(name = "currency_id", nullable=false)
	private Long currencyId;
	
	@Column(name = "currency_name", nullable=false)
    private String currencyName;
	
	@Column(name = "num_decimal_places", nullable=false)
	private Long numOfDecimalPlaces;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
//	@Enumerated(value = EnumType.STRING)
//	@Column(name = "approve_status",  nullable=true, length=30)
//	private CommonApproveStatus approveStatus;
	
	@Column(name = "created_user", nullable=false, length=255)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

//	@Column(name = "pen_approved_user", nullable=true, length=255)
//	private String penApprovedUser;
//	
//	@Column(name = "pen_approved_date", nullable=true)
//	private Timestamp penApprovedDate;
//	
//	@Column(name = "pen_rejected_user", nullable=true, length=255)
//	private String penRejectedUser;
//	
//	@Column(name = "pen_rejected_date", nullable=true)
//	private Timestamp penRejectedDate;
	
	@Column(name="history_created_user")
	private String historyCreatedUser;
	
	@Column(name="history_created_date")
	private Timestamp historyCreatedDate;

	
	public Long getMasterCurrencyId() {
		return masterCurrencyId;
	}

	public void setMasterCurrencyId(Long masterCurrencyId) {
		this.masterCurrencyId = masterCurrencyId;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public Long getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(Long masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
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

//	public CommonApproveStatus getApproveStatus() {
//		return approveStatus;
//	}
//
//	public void setApproveStatus(CommonApproveStatus approveStatus) {
//		this.approveStatus = approveStatus;
//	}

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

//	public String getPenApprovedUser() {
//		return penApprovedUser;
//	}
//
//	public void setPenApprovedUser(String penApprovedUser) {
//		this.penApprovedUser = penApprovedUser;
//	}
//
//	public Timestamp getPenApprovedDate() {
//		return penApprovedDate;
//	}
//
//	public void setPenApprovedDate(Timestamp penApprovedDate) {
//		this.penApprovedDate = penApprovedDate;
//	}
//
//	public String getPenRejectedUser() {
//		return penRejectedUser;
//	}
//
//	public void setPenRejectedUser(String penRejectedUser) {
//		this.penRejectedUser = penRejectedUser;
//	}
//
//	public Timestamp getPenRejectedDate() {
//		return penRejectedDate;
//	}
//
//	public void setPenRejectedDate(Timestamp penRejectedDate) {
//		this.penRejectedDate = penRejectedDate;
//	}
	
	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}


}
