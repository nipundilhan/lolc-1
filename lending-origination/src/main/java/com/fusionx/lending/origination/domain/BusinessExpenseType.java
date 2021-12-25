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
 * Business Expense Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020      		     FX-5271	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_expense_type")
@Data
public class BusinessExpenseType extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_type_id", nullable = false)
	private BusinessType businessType;
	
	@Transient
    private Long businessTypesId;
	
	@Transient
	private String businessTypesCode;
	
	@Transient
	private String businessTypesName;
	
	@Transient
	private String businessTypesDescription;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable = false)
	private ExpenseType expenseType;
	
	@Transient
    private Long expenseTypesId;
	
	@Transient
	private String expenseTypesCode;
	
	@Transient
	private String expenseTypesName;
	
	@Transient
	private String expenseTypesDescription;
	
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

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
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

	public Long getBusinessTypesId() {
		return businessType.getId();
	}
	
	public String getBusinessTypesCode() {
		return businessType.getCode();
	}

	public String getBusinessTypesName() {
		return businessType.getName();
	}

	public String getBusinessTypesDescription() {
		return businessType.getDescription();
	}
	
	public Long getExpenseTypesId() {
		return expenseType.getId();
	}

	public String getExpenseTypesCode() {
		return expenseType.getCode();
	}

	public String getExpenseTypesName() {
		return expenseType.getName();
	}

	public String getExpenseTypesDescription() {
		return expenseType.getDescription();
	}
	
}