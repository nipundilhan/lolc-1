package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

/**
 * Expense Type mapping with Household Expense Categories Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-04-2021     FX-5238     FX-6136    RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "expense_type_expense_category")
@Data
public class ExpenseTypeHouseholdExpenseCategories extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 438841639845446354L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "house_hold_expense_category_id", nullable=false)
	private HouseHoldExpenseCategory houseHoldExpenseCategory;
	
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
	private Long houseHoldExpenseCatId;
	
	@Transient
	private Long expenseTypeId;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<ExpenseTypeHouseholdExpenseCategoriesDetails> expenseTypeHouseholdExpenseCategoriesDetails;
	
//	@Transient
//	@JsonInclude(Include.NON_NULL)
//	private ExpenseType expenseTypeDetails;
//	
//	@Transient
//	@JsonInclude(Include.NON_NULL)
//	private HouseHoldExpenseCategory houseHoldExpenseCategoryDetails;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public HouseHoldExpenseCategory getHouseHoldExpenseCategory() {
		return houseHoldExpenseCategory;
	}

	public void setHouseHoldExpenseCategory(HouseHoldExpenseCategory houseHoldExpenseCategory) {
		this.houseHoldExpenseCategory = houseHoldExpenseCategory;
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

	public Long getHouseHoldExpenseCatId() {
		return houseHoldExpenseCategory.getId();
	}

	public void setHouseHoldExpenseCatId(Long houseHoldExpenseCatId) {
		this.houseHoldExpenseCatId = houseHoldExpenseCatId;
	}

	public List<ExpenseTypeHouseholdExpenseCategoriesDetails> getExpenseTypeHouseholdExpenseCategoriesDetails() {
		return expenseTypeHouseholdExpenseCategoriesDetails;
	}

	public void setExpenseTypeHouseholdExpenseCategoriesDetails(
			List<ExpenseTypeHouseholdExpenseCategoriesDetails> expenseTypeHouseholdExpenseCategoriesDetails) {
		this.expenseTypeHouseholdExpenseCategoriesDetails = expenseTypeHouseholdExpenseCategoriesDetails;
	}
	
	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public String getHouseHoldExpenseCatName() {
		return houseHoldExpenseCategory.getName();
		
	}
	
	public Long getExpenseTypeId() {
		return expenseType.getId();
	}
	
	public String getExpenseTypeName() {
		return expenseType.getName();
	}
}
