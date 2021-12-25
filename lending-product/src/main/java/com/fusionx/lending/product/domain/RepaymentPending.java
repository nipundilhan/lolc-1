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
 * Repayment Pending Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "repayment_pending")
@Data
public class RepaymentPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -9213213095081037762L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_id", nullable=true)
	private Repayment repayment;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_type_id", nullable = false)
	private RepaymentType repaymentType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_frequency_id", nullable = false)
	private RepaymentFrequency repaymentFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "repayment_amount_type_id", nullable = false)
	private RepaymentAmountType repaymentAmountType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "pcreated_user", length=255, nullable=false)
	private String pcreatedUser;
	
	@Column(name = "pcreated_date", nullable=false)
	private Timestamp pcreatedDate;
	
	@Transient
    private Long workflowId;
	
	@Transient
    private Long workflowProcessId;
	
	@Transient
	private String workflowStatus;
	
	@Transient
	private Long repaymentTypeId;
	
	@Transient
	private Long RepaymentFrequencyId;
	
	@Transient
	private String RepaymentFrequencyName;
	
	@Transient
	private String repaymentTypeName;
	
	@Transient
	private String approveStatus;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Repayment getRepayment() {
		return repayment;
	}

	public void setRepayment(Repayment repayment) {
		this.repayment = repayment;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RepaymentType getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(RepaymentType repaymentType) {
		this.repaymentType = repaymentType;
	}

	public RepaymentFrequency getRepaymentFrequency() {
		return repaymentFrequency;
	}

	public void setRepaymentFrequency(RepaymentFrequency repaymentFrequency) {
		this.repaymentFrequency = repaymentFrequency;
	}

	public RepaymentAmountType getRepaymentAmountType() {
		return repaymentAmountType;
	}

	public void setRepaymentAmountType(RepaymentAmountType repaymentAmountType) {
		this.repaymentAmountType = repaymentAmountType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public String getPcreatedUser() {
		return pcreatedUser;
	}

	public void setPcreatedUser(String pcreatedUser) {
		this.pcreatedUser = pcreatedUser;
	}

	public Timestamp getPcreatedDate() {
		return pcreatedDate;
	}

	public void setPcreatedDate(Timestamp pcreatedDate) {
		this.pcreatedDate = pcreatedDate;
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

	public Long getRepaymentTypeId() {
		if(repaymentType != null) {
			return repaymentType.getId();
		} else {
			return null;
		}
	}

	public Long getRepaymentFrequencyId() {
		if(repaymentFrequency != null) {
			return repaymentFrequency.getId();
		} else {
			return null;
		}
	}

	public String getRepaymentFrequencyName() {
		if(repaymentFrequency != null) {
			return repaymentFrequency.getName();
		} else {
			return null;
		}
	}

	public String getRepaymentTypeName() {
		if(repaymentType != null) {
			return repaymentType.getName();
		} else {
			return null;
		}
	}

	public String getApproveStatus() {
		if(repayment != null && repayment.getApproveStatus() != null) {
			return repayment.getApproveStatus().toString();
		} else {
			return null;
		}
	}
	
}	
