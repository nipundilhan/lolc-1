package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * EligibilityResidencyEligibilityHistory
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_2  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_residency_eligibility_history")
public class EligibilityResidencyEligibilityHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2756226005982683974L;

	@JoinColumn(name = "eli_residency_eligibility_id", nullable = false)
	private Long eliResidencyEligibilityId;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Long eligibilityId;
	
	@JoinColumn(name = "residency_eligibility_id", nullable = false)
	private Long residencyEligibilityId;
	
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
	
	public Long getEliResidencyEligibilityId() {
		return eliResidencyEligibilityId;
	}

	public void setEliResidencyEligibilityId(Long eliResidencyEligibilityId) {
		this.eliResidencyEligibilityId = eliResidencyEligibilityId;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public Long getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public Long getResidencyEligibilityId() {
		return residencyEligibilityId;
	}

	public void setResidencyEligibilityId(Long residencyEligibilityId) {
		this.residencyEligibilityId = residencyEligibilityId;
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
