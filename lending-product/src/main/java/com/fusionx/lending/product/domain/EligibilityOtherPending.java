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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * Eligibility Other Pending
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_other_pending")
public class EligibilityOtherPending extends BaseEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -7743931522922804452L;
	

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_other_id", nullable = true)
	private EligibilityOther eligibilityOther;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Eligibility eligibilitys;
	
	@Transient
    private Long eligibilityId;
	
	@Transient
    private String eligibilityCode;
	
	@Transient
    private String eligibilityName;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_pending_id", nullable = false)
	private EligibilityPending eligibilityPending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_eligibility_type_id", nullable = false)
	private OtherEligibilityType otherEligibilityTypes;
	
	@Transient
    private Long otherEligibilityTypeId;
	
	@Transient
    private String otherEligibilityTypeCode;
	
	@Transient
    private String otherEligibilityTypeName;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;	
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public EligibilityOther getEligibilityOther() {
		return eligibilityOther;
	}

	public void setEligibilityOther(EligibilityOther eligibilityOther) {
		this.eligibilityOther = eligibilityOther;
	}
	
	public Eligibility getEligibilitys() {
		return eligibilitys;
	}

	public void setEligibilitys(Eligibility eligibilitys) {
		this.eligibilitys = eligibilitys;
	}
	
	public EligibilityPending getEligibilityPending() {
		return eligibilityPending;
	}

	public void setEligibilityPending(EligibilityPending eligibilityPending) {
		this.eligibilityPending = eligibilityPending;
	}	

	public OtherEligibilityType getOtherEligibilityTypes() {
		return otherEligibilityTypes;
	}

	public void setOtherEligibilityTypes(OtherEligibilityType otherEligibilityTypes) {
		this.otherEligibilityTypes = otherEligibilityTypes;
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

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}
	
	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	//@transient columns
	public Long getEligibilityId() {
		if(eligibilitys != null) {
			return eligibilitys.getId();
		} else {
			return null;
		}
	}
	
	public String getEligibilityCode() {
		if(eligibilitys != null) {
			return eligibilitys.getCode();
		} else {
			return null;
		}
	}

	public String getEligibilityName() {
		if(eligibilitys != null) {
			return eligibilitys.getName();
		} else {
			return null;
		}
	}

	public Long getOtherEligibilityTypeId() {
		if(otherEligibilityTypes != null) {
			return otherEligibilityTypes.getId();
		} else {
			return null;
		}
	}

	public String getOtherEligibilityTypeCode() {
		if(otherEligibilityTypes != null) {
			return otherEligibilityTypes.getCode();
		} else {
			return null;
		}
	}

	public String getOtherEligibilityTypeName() {
		if(otherEligibilityTypes != null) {
			return otherEligibilityTypes.getName();
		} else {
			return null;
		}
	}

	
	
		
}
