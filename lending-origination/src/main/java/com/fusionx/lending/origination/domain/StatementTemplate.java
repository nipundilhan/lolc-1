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
import javax.persistence.Transient;

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
@Table(name = "statement_template")
@Data
public class StatementTemplate extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

    @Column(name = "tenant_id")
    private String tenantId;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "statement_type_id", nullable=false)
    private FinancialStatementType statementType;
    
    @Transient
    private Long statementTypeId;
    
    @Transient
    private String statementTypeCode;
    
    @Transient
    private String statementTypeName;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public FinancialStatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(FinancialStatementType statementType) {
		this.statementType = statementType;
	}

	public Long getStatementTypeId() {
		if(statementType != null) {
			return  statementType.getId() ;
		}else {
			return null;
		}
	}

	public String getStatementTypeCode() {
		return statementTypeCode;
	}

	public void setStatementTypeCode(String statementTypeCode) {
		this.statementTypeCode = statementTypeCode;
	}

	public String getStatementTypeName() {
		return statementTypeName;
	}

	public void setStatementTypeName(String statementTypeName) {
		this.statementTypeName = statementTypeName;
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

	public void setDescription(String description) {
		this.description = description;
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
