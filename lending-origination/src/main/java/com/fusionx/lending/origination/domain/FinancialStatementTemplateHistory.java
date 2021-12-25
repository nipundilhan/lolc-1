package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "fin_stmt_template_history")
@Data
public class FinancialStatementTemplateHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5460467684037752132L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "financial_statement_id", nullable = false)
	private Long financialStatementId;

	@Column(name = "template_code", length = 4, nullable = false)
	private String templateCode;

	@Column(name = "template_name", length = 70, nullable = false)
	private String templateName;

	@Column(name = "description", length = 300, nullable = true)
	private String description;

	@Column(name = "statement_type_id", nullable = false)
	private Long statementTypeId;

//	@Transient
	@Column(name = "statement_type_name", length = 70, nullable = false)
	private String statementType;

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

	@Column(name = "history_created_user", length = 255, nullable = false)
	private String historyCreatedUser;

	@Column(name = "history_created_date", nullable = false)
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getDescription() {
		return description;
	}

	public Long getStatementTypeId() {
		return statementTypeId;
	}

	public Long getFinacialStatementId() {
		return financialStatementId;
	}

	public String getStatementType() {
		return statementType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatementTypeId(Long statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

	public void setFinancialStatementId(Long financialStatementId) {
		this.financialStatementId = financialStatementId;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
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
