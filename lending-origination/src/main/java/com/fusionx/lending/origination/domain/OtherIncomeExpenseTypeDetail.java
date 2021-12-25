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
 * Other Income Expense Type Detail
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "other_income_expense_type_detail")
@Data
public class OtherIncomeExpenseTypeDetail extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4522610539116920797L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_income_expense_type_id", nullable=false)
	private OtherIncomeExpenseType otherIncomeExpenseType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable=false)
	private ExpenseType expenseType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=30, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private Long expenseTypesId;
	
	@Transient
	private String expenseTypesName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public OtherIncomeExpenseType getOtherIncomeExpenseType() {
		return otherIncomeExpenseType;
	}

	public void setOtherIncomeExpenseType(
			OtherIncomeExpenseType otherIncomeExpenseType) {
		this.otherIncomeExpenseType = otherIncomeExpenseType;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
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

	public Long getExpenseTypesId() {
		return expenseTypesId;
	}

	public void setExpenseTypesId(Long expenseTypesId) {
		this.expenseTypesId = expenseTypesId;
	}
	
	public String getExpenseTypesName() {
		return expenseTypesName;
	}

	public void setExpenseTypesName(String expenseTypesName) {
		this.expenseTypesName = expenseTypesName;
	}
	
	

}
