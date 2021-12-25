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
/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Entity
@Table(name = "eligibility_legal_struct_pend")
public class EligibilityTemplateLegalStructurePending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8899815222620763190L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_legal_structure_id", nullable = true)
	private EligibilityTemplateLegalStructure eligibilityLegalStructureId;

//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Long eligibilityId;

	@Column(name = "eligibility_name", nullable = true, length = 20)
	private String eligibilityName;

	@Column(name = "legal_structure_id", nullable = false)
	private String legalStructureId;

	@Column(name = "legal_structure_name", nullable = true, length = 20)
	private String legalStructureName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private CommonStatus status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable = true)
	private LendingWorkflow lendingWorkflow;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String pcreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp pcreatedDate;

	@Transient
	private Long workflowId;

	@Transient
	private Long workflowProcessId;

	@Transient
	private String workflowStatus;

	@Transient
	private String approveStatus;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
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

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public Long getWorkflowId() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowProcessId() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowProcessId();
		} else {
			return null;
		}
	}

	public String getWorkflowStatus() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowStatus().toString();
		} else {
			return null;
		}
	}

	public String getApproveStatus() {
		if (eligibilityLegalStructureId != null && eligibilityLegalStructureId.getApproveStatus() != null) {
			return eligibilityLegalStructureId.getApproveStatus().toString();
		} else {
			return null;
		}
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public void setWorkflowProcessId(Long workflowProcessId) {
		this.workflowProcessId = workflowProcessId;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public EligibilityTemplateLegalStructure getEligibilityLegalStructureId() {
		return eligibilityLegalStructureId;
	}

	public void setEligibilityLegalStructureId(EligibilityTemplateLegalStructure eligibilityLegalStructureId) {
		this.eligibilityLegalStructureId = eligibilityLegalStructureId;
	}

	public Long getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public String getEligibilityName() {
		return eligibilityName;
	}

	public void setEligibilityName(String eligibilityName) {
		this.eligibilityName = eligibilityName;
	}

	public String getLegalStructureId() {
		return legalStructureId;
	}

	public void setLegalStructureId(String legalStructureId) {
		this.legalStructureId = legalStructureId;
	}

	public String getLegalStructureName() {
		return legalStructureName;
	}

	public void setLegalStructureName(String legalStructureName) {
		this.legalStructureName = legalStructureName;
	}

}
