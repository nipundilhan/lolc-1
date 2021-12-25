package com.fusionx.lending.origination.domain;

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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

/**
 * Financial Statement Domain
 * @author 	Nipun Dilan
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-09-2021  						    Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "financial_statement")
//@Data
public class FinancialStatement  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5460467684037752132L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=true)
	private Customer customer;	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fin_stmt_type_id", nullable=true)
	private FinancialStatementType financialStatementType;	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fin_stmt_template_id", nullable=true)
	private FinancialStatementTemplate financialStatementTemplate;	
	
	

	public FinancialStatementType getFinancialStatementType() {
		return financialStatementType;
	}

	public void setFinancialStatementType(FinancialStatementType financialStatementType) {
		this.financialStatementType = financialStatementType;
	}

	public FinancialStatementTemplate getFinancialStatementTemplate() {
		return financialStatementTemplate;
	}

	public void setFinancialStatementTemplate(FinancialStatementTemplate financialStatementTemplate) {
		this.financialStatementTemplate = financialStatementTemplate;
	}
	
	@Transient
	private Long customerValId;
    
	public Long getCustomerValId() {
		if(customer != null) {
			return customer.getId();
		} else {
			return null;
		}
	}
	
	
	@Transient
	private Long financialStatementTypeId;
    
	public Long getFinancialStatementTypeId() {
		if(financialStatementType != null) {
			return financialStatementType.getId();
		} else {
			return null;
		}
	}
	
	@Transient
	private Long financialStatementTemplateId;
    
	public Long getFinancialStatementTemplateId() {
		if(financialStatementTemplate != null) {
			return financialStatementTemplate.getId();
		} else {
			return null;
		}
	}
	
//	@Column(name = "audited_by_user_id", length = 500, nullable = true)
//	private String auditedByUserId;

	@Column(name = "audited_by", length = 500, nullable = true)
	private String auditedBy;
	
	@Column(name = "from_date", nullable = false)
	private Timestamp fromDate;
	
	@Column(name = "to_date", nullable = false)
	private Timestamp toDate;
	
	@Column(name = "no_of_reports", nullable = true)
	private Long noOfReports;
	
	@Column(name = "note", length = 350, nullable = true)
	private String note;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAuditedBy() {
		return auditedBy;
	}

	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

//	public String getAuditedByUserId() {
//		return auditedByUserId;
//	}
//
//	public void setAuditedByUserId(String auditedByUserId) {
//		this.auditedByUserId = auditedByUserId;
//	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public Long getNoOfReports() {
		return noOfReports;
	}

	public void setNoOfReports(Long noOfReports) {
		this.noOfReports = noOfReports;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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



}
