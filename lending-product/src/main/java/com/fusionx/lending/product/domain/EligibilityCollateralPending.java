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
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Eligibility Collateral Pending Domain
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6889	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_collateral_pending")
@Data
public class EligibilityCollateralPending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1021919940658291446L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_collateral_id", nullable = false)
	private EligibilityCollateral eligibilityCollaterals;
	
	@Transient
    private Long eligibilityCollateralId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Transient
    private Long workflowId;
	
	@Transient
    private Long workflowProcessId;
	
	@Transient
	private String workflowStatus;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Eligibility eligibilitys;
	
	@Transient
    private Long eligibilityId;
	
	@Transient
	private String eligibilityCode; //

	@Transient
	private String eligibilityName; //
	
	@Column(name = "asset_type_id", nullable=false)
	private Long assetTypeId;
	
	@Transient
    private String assetTypeCode; //

    @Transient
    private String assetTypeName; //
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user", nullable=false, length=255)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

	public EligibilityCollateral getEligibilityCollaterals() {
		return eligibilityCollaterals;
	}

	public void setEligibilityCollaterals(EligibilityCollateral eligibilityCollaterals) {
		this.eligibilityCollaterals = eligibilityCollaterals;
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

	public Eligibility getEligibilitys() {
		return eligibilitys;
	}

	public void setEligibilitys(Eligibility eligibilitys) {
		this.eligibilitys = eligibilitys;
	}

	public Long getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
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
	
	public Long getEligibilityCollateralId() {
		if(eligibilityCollaterals != null) {
			return eligibilityCollaterals.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowId() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowProcessId() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowProcessId();
		} else {
			return null;
		}
	}

	public String getWorkflowStatus() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowStatus().toString();
		} else {
			return null;
		}
	}

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

	public String getAssetTypeCode() {
		return assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}
	
}
