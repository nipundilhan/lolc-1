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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
/**
 * Financial Statement  Document Domain
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
@Table(name = "financial_statement_document")
//@Data
public class FinancialStatementDocument  extends BaseEntity implements Serializable {

			private static final long serialVersionUID = 5460467684037752132L;

			@Column(name = "tenant_id", length = 10, nullable = false)
			private String tenantId;
			
			@JsonIgnore
			@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
			@JoinColumn(name = "financial_statement_id", nullable=false)
			private FinancialStatement financialStatement;
	

			@Column(name = "document_id",  nullable = false)
			private Long documentId;

			@Column(name = "document_name", length = 350, nullable = false)
			private String documentName;
			
			@Column(name = "document_ref_id",  nullable = true)
			private Long documentRefId;

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

			public FinancialStatement getFinancialStatement() {
				return financialStatement;
			}

			public void setFinancialStatement(FinancialStatement financialStatement) {
				this.financialStatement = financialStatement;
			}

			public Long getDocumentId() {
				return documentId;
			}

			public void setDocumentId(Long documentId) {
				this.documentId = documentId;
			}

			public String getDocumentName() {
				return documentName;
			}

			public void setDocumentName(String documentName) {
				this.documentName = documentName;
			}

			public Long getDocumentRefId() {
				return documentRefId;
			}

			public void setDocumentRefId(Long documentRefId) {
				this.documentRefId = documentRefId;
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

