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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.core.MasterPropertyBase;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.MasterDefinitionBusinessPrinciple;
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
@Table(name = "master_definition")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class MasterDefinition extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5026489486229118381L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

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

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", length = 255, nullable = true)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Column(name = "pen_approved_user", nullable = true, length = 255)
	private String penApprovedUser;

	@Column(name = "pen_approved_date", nullable = true)
	private Timestamp penApprovedDate;

	@Column(name = "pen_rejected_user", nullable = true, length = 255)
	private String penRejectedUser;

	@Column(name = "pen_rejected_date", nullable = true)
	private Timestamp penRejectedDate;

	@Column(name = "max_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal maxInterestRate;

	@Column(name = "mini_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal miniInterestRate;

	@Column(name = "max_penal_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal maxPenalInterestRate;

	@Column(name = "mini_penal_interest_rate", columnDefinition = "Decimal(25,5)", nullable = true)
	private BigDecimal miniPenalInterestRate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_id", nullable = true)
	private PenalInterest penalInterest;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "due_date_template_id")
	private DueDateTemplates dueDateTemplate;

	@Column(name = "due_date_template_remark", length = 1000)
	private String dueDateTemplateRemark;

	@Column(name = "next_contract_number")
	private Long nextContractNumber;

	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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

	public MasterDefinitionModule getModule() {
		return module;
	}

	public void setModule(MasterDefinitionModule module) {
		this.module = module;
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

	public Long getNextContractNumber() {
		return nextContractNumber;
	}

	public void setNextContractNumber(Long nextContractNumber) {
		this.nextContractNumber = nextContractNumber;
	}
}
