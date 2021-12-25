package com.fusionx.lending.origination.domain;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.core.MasterPropertyBase;

import lombok.Data;

/**
 * financial Statement detail Domain
 * @author 	NipunDilhan
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-09-2021  						    NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "financial_statement_detail")
//@Data
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class FinancialStatementDetail   extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5460467684037752132L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "financial_statement_id", nullable=true)
	private FinancialStatement financialStatement;	
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fin_stmnt_temp_det_id", nullable=true)
	private FinancialStatementTemplateDetails financialStatementTemplateDetail;	
	
	
	
	public FinancialStatement getFinancialStatement() {
		return financialStatement;
	}

	public void setFinancialStatement(FinancialStatement financialStatement) {
		this.financialStatement = financialStatement;
	}

	public FinancialStatementTemplateDetails getFinancialStatementTemplateDetail() {
		return financialStatementTemplateDetail;
	}

	public void setFinancialStatementTemplateDetail(FinancialStatementTemplateDetails financialStatementTemplateDetail) {
		this.financialStatementTemplateDetail = financialStatementTemplateDetail;
	}

	@Column(name = "amount",  nullable=false)
	private BigDecimal amount;
	
//	@Column(name = "comment", length=350, nullable=true)
//	private String comment;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

//	public String getComment() {
//		return comment;
//	}
//
//	public void setComment(String comment) {
//		this.comment = comment;
//	}

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
	
	
	

}
