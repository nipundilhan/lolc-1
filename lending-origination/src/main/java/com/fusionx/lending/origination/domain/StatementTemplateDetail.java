package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "statement_template_detail")
@Data
public class StatementTemplateDetail extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 169841751295311047L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "statement_template_id")
	private StatementTemplate statementTemplate;

	@Column(name = "level_id", nullable = false)
	private Long parentId;

	@Column(name = "parent_id", nullable = false)
	private Long levelId;

	@Column(name = "formula", length = 300, nullable = true)
	private String formula;

	@Column(name = "total_value_required", nullable = false)
	private String totalValueRequired;

	@Column(name = "formula_enabled", length = 20, nullable = false)
	private String formulaEnabled;

	@Column(name = "additional_note_required", length = 20, nullable = false)
	private String additionalNoteRequired;

	@Column(name = "status")
    private String status;

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

	public StatementTemplate getStatementTemplate() {
		return statementTemplate;
	}

	public void setStatementTemplate(StatementTemplate statementTemplate) {
		this.statementTemplate = statementTemplate;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getTotalValueRequired() {
		return totalValueRequired;
	}

	public void setTotalValueRequired(String totalValueRequired) {
		this.totalValueRequired = totalValueRequired;
	}

	public String getFormulaEnabled() {
		return formulaEnabled;
	}

	public void setFormulaEnabled(String formulaEnabled) {
		this.formulaEnabled = formulaEnabled;
	}

	public String getAdditionalNoteRequired() {
		return additionalNoteRequired;
	}

	public void setAdditionalNoteRequired(String additionalNoteRequired) {
		this.additionalNoteRequired = additionalNoteRequired;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
	
	

}
