package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
/**
 * Financial Statement Level Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-08-2021      	FXL-338	  FXL-655	Dilki        Created
 *    
 ********************************************************************************************************
 */
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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CommonYesNoStatus;

import lombok.Data;

@Entity
@Table(name = "fin_stmt_template_details")
//@Data
public class FinancialStatementTemplateDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 169841751295311047L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fin_stmt_template_id")
	private FinancialStatementTemplate financialStatement;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "financial_statement_level_id")
	private FinancialStatementLevel financialStatementLevels;
	
	@Transient
	private Long financialStatementLevelId;

	@Column(name = "parent_id", nullable = true)
	private String parentId;

	@Column(name = "field_name", length = 70, nullable = false)
	private String fieldName;

	@Column(name = "formula", length = 300, nullable = true)
	private String formula;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "total_value_required", nullable = false)
	private CommonYesNoStatus totalValueRequired;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "formula_enabled", length = 20, nullable = false)
	private CommonYesNoStatus formulaEnabled;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "additional_note_required", length = 20, nullable = false)
	private CommonYesNoStatus additionalNoteRequired;

	@Column(name = "sequence", length = 255, nullable = true)
	private Long sequence;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 255, nullable = false)
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public FinancialStatementTemplate getFinancialStatement() {
		return financialStatement;
	}

	public void setFinancialStatement(FinancialStatementTemplate financialStatement) {
		this.financialStatement = financialStatement;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public CommonYesNoStatus getTotalValueRequired() {
		return totalValueRequired;
	}

	public void setTotalValueRequired(CommonYesNoStatus totalValueRequired) {
		this.totalValueRequired = totalValueRequired;
	}

	public CommonYesNoStatus getFormulaEnabled() {
		return formulaEnabled;
	}

	public void setFormulaEnabled(CommonYesNoStatus formulaEnabled) {
		this.formulaEnabled = formulaEnabled;
	}

	public CommonYesNoStatus getAdditionalNoteRequired() {
		return additionalNoteRequired;
	}

	public void setAdditionalNoteRequired(CommonYesNoStatus additionalNoteRequired) {
		this.additionalNoteRequired = additionalNoteRequired;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
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

	public FinancialStatementLevel getFinancialStatementLevels() {
		return financialStatementLevels;
	}

	public void setFinancialStatementLevels(FinancialStatementLevel financialStatementLevels) {
		this.financialStatementLevels = financialStatementLevels;
	}

	public Long getFinancialStatementLevelId() {
		return financialStatementLevels.getId();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
