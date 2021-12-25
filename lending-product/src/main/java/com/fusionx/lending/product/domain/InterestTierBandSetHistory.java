package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Interest Tier Band Set History
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "interest_tier_band_set_history")
@Data
public class InterestTierBandSetHistory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -8683276814127002760L;
	
	@Column(name = "interest_tier_band_set_id", nullable=false)
	private Long interestTierBandSetHistoryId;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "tier_band_method_id", nullable=false)
	private Long tierBandMethodId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "calculation_method_id", nullable=false)
	private Long calculationMethodId;
	
	@Column(name = "interest_template_id", nullable=false)
	private Long interestTemplateId;
	
	@Column(name = "note", length=255, nullable=true)
	private String note;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Column(name="history_created_user" , nullable=true, length=255)
	private String historyCreatedUser;
	
	@Column(name="history_created_date" , nullable=true)
	private Timestamp historyCreatedDate;
	
	public Long getInterestTierBandSetHistoryId() {
		return interestTierBandSetHistoryId;
	}

	public void setInterestTierBandSetHistoryId(Long interestTierBandSetHistoryId) {
		this.interestTierBandSetHistoryId = interestTierBandSetHistoryId;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public Long getTierBandMethodId() {
		return tierBandMethodId;
	}

	public void setTierBandMethodId(Long tierBandMethodId) {
		this.tierBandMethodId = tierBandMethodId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Long getCalculationMethodId() {
		return calculationMethodId;
	}

	public void setCalculationMethodId(Long calculationMethodId) {
		this.calculationMethodId = calculationMethodId;
	}
	
	public Long getInterestTemplateId() {
		return interestTemplateId;
	}

	public void setInterestTemplateId(Long interestTemplateId) {
		this.interestTemplateId = interestTemplateId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}
	
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

