package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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

import lombok.Data;

@Entity
@Table(name = "fin_stmt_template")
@Data
public class FinancialStatementTemplate extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5460467684037752132L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "template_code", length = 4, nullable = false)
	private String code;

	@Column(name = "template_name", length = 70, nullable = false)
	private String name;

	@Column(name = "description", length = 300, nullable = true)
	private String description;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "statement_type_id")
	private FinancialStatementType financialStatementType;
	
	@Transient
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
	
	@Transient
	private List<FinancialStatementTemplateDetails> financialStatementTemplateDetails;

	public String getTenantId() {
		return tenantId;
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

	public String getDescription() {
		return description;
	}

	/*public Long getStatementTypeId() {
		return statementTypeId;
	}*/

	public String getStatementType() {
		return statementType;
	}

	

	public FinancialStatementType getFinancialStatementType() {
		return financialStatementType;
	}

	public void setFinancialStatementType(FinancialStatementType financialStatementType) {
		this.financialStatementType = financialStatementType;
	}
	
	

	public Long getStatementTypeId() {
		if(financialStatementType!=null) {
			return statementTypeId = financialStatementType.getId();
		}else {
			return null;
		}
	}

	public void setStatementTypeId(Long statementTypeId) {
		this.statementTypeId = statementTypeId;
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

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public void setStatementTypeId(Long statementTypeId) {
		this.statementTypeId = statementTypeId;
	}*/

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

	public List<FinancialStatementTemplateDetails> getFinancialStatementTemplateDetails() {
		return financialStatementTemplateDetails;
	}

	public void setFinancialStatementTemplateDetails(
			List<FinancialStatementTemplateDetails> financialStatementTemplateDetails) {
		this.financialStatementTemplateDetails = financialStatementTemplateDetails;
	}

}
