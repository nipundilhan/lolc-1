package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
import com.fusionx.lending.product.enums.MasterDefinitionBusinessPrinciple;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.MasterDefinitionModule;


/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2021   FXL-1         FXL-5      Dilki        Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "master_definition_pending")
public class MasterDefinitionPending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -4661520710319310636L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_id", nullable = true)
	private MasterDefinition masterDefinition;

	@Column(name = "code", length = 20, nullable = false)
	private String code;

	@Column(name = "name", length = 350, nullable = false)
	private String name;

	@Column(name = "description", length = 350, nullable = true)
	private String description;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "business_principle", length = 20, nullable = false)
	private MasterDefinitionBusinessPrinciple businessPrinciple;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "module", length = 20, nullable = false)
	private MasterDefinitionModule module;

	@Column(name = "sub_module_code", length = 20, nullable = false)
	private String subModuleCode;

	@Column(name = "sub_module_name", length = 350, nullable = false)
	private String subModuleName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status", nullable = true, length = 30)
	private CommonApproveStatus approveStatus;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "master_definition_category", nullable = true, length = 30)
	private MasterDefinitionCategory masterDefinitionCategory;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable = true)
	private LendingWorkflow lendingWorkflow;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String penCreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp penCreatedDate;

	@Column(name = "max_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal maxInterestRate;

	@Column(name = "mini_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal miniInterestRate;

	@Column(name = "max_penal_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal maxPenalInterestRate;

	@Column(name = "mini_penal_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal miniPenalInterestRate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_id", nullable = true)
	private PenalInterest penalInterest;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "due_date_template_id")
	private DueDateTemplates dueDateTemplate;

	@Column(name = "due_date_template_remark", length = 1000)
	private String dueDateTemplateRemark;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = CommonStatus.valueOf(status);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MasterDefinitionBusinessPrinciple getBusinessPrinciple() {
		return businessPrinciple;
	}

	public void setBusinessPrinciple(MasterDefinitionBusinessPrinciple businessPrinciple) {
		this.businessPrinciple = businessPrinciple;
	}

	public MasterDefinitionModule getModule() {
		return module;
	}

	public void setModule(MasterDefinitionModule module) {
		this.module = module;
	}

	public String getSubModuleCode() {
		return subModuleCode;
	}

	public void setSubModuleCode(String subModuleCode) {
		this.subModuleCode = subModuleCode;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public MasterDefinitionCategory getMasterDefinitionCategory() {
		return masterDefinitionCategory;
	}

	public void setMasterDefinitionCategory(MasterDefinitionCategory masterDefinitionCategory) {
		this.masterDefinitionCategory = masterDefinitionCategory;
	}

	public String getPenCreatedUser() {
		return penCreatedUser;
	}

	public void setPenCreatedUser(String penCreatedUser) {
		this.penCreatedUser = penCreatedUser;
	}

	public Timestamp getPenCreatedDate() {
		return penCreatedDate;
	}

	public void setPenCreatedDate(Timestamp penCreatedDate) {
		this.penCreatedDate = penCreatedDate;
	}

	public MasterDefinition getMasterDefinition() {
		return masterDefinition;
	}

	public void setMasterDefinition(MasterDefinition masterDefinition) {
		this.masterDefinition = masterDefinition;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public BigDecimal getMaxInterestRate() {
		return maxInterestRate;
	}

	public void setMaxInterestRate(BigDecimal maxInterestRate) {
		this.maxInterestRate = maxInterestRate;
	}

	public BigDecimal getMiniInterestRate() {
		return miniInterestRate;
	}

	public void setMiniInterestRate(BigDecimal miniInterestRate) {
		this.miniInterestRate = miniInterestRate;
	}

	public BigDecimal getMaxPenalInterestRate() {
		return maxPenalInterestRate;
	}

	public void setMaxPenalInterestRate(BigDecimal maxPenalInterestRate) {
		this.maxPenalInterestRate = maxPenalInterestRate;
	}

	public BigDecimal getMiniPenalInterestRate() {
		return miniPenalInterestRate;
	}

	public void setMiniPenalInterestRate(BigDecimal miniPenalInterestRate) {
		this.miniPenalInterestRate = miniPenalInterestRate;
	}

	public PenalInterest getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(PenalInterest penalInterest) {
		this.penalInterest = penalInterest;
	}

	public Long getPenalInterestId() {
		return penalInterest != null ? penalInterest.getId() : null;
	}

	public String getPenalInterestName() {
		return penalInterest != null ? penalInterest.getName() : null;

	}

	public DueDateTemplates getDueDateTemplate() {
		return dueDateTemplate;
	}

	public void setDueDateTemplate(DueDateTemplates dueDateTemplate) {
		this.dueDateTemplate = dueDateTemplate;
	}

	public String getDueDateTemplateRemark() {
		return dueDateTemplateRemark;
	}

	public void setDueDateTemplateRemark(String dueDateTemplateRemark) {
		this.dueDateTemplateRemark = dueDateTemplateRemark;
	}

}
