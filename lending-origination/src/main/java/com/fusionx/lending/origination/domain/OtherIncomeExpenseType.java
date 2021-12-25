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
 * Other Income Expense Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "other_income_expense_type")
@Data
public class OtherIncomeExpenseType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6055052591023732853L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_income_category_id", nullable = false)
	private OtherIncomeCategory otherIncomeCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable=false)
	private ExpenseType expenseType;
	
//	@Transient
//	private Long otherIncomeCatId;
//	
//	@Transient
//	private String otherIncomeCatName;
	
	@Transient
	private Long expenseTypeId;
	
//	@Transient
//	@JsonInclude(Include.NON_NULL)
//	private List<OtherIncomeExpenseTypeDetail> otherIncomeExpenseTypeDetailList;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
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

	public OtherIncomeCategory getOtherIncomeCategory() {
		return otherIncomeCategory;
	}

	public void setOtherIncomeCategory(OtherIncomeCategory otherIncomeCategory) {
		this.otherIncomeCategory = otherIncomeCategory;
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
//	public Long getOtherIncomeCatId() {
//        return otherIncomeCatId;
//    }
//
//    public void setOtherIncomeCatId(Long otherIncomeCatId) {
//        this.otherIncomeCatId = otherIncomeCatId;
//    }
//    
//    public String getOtherIncomeCatName() {
//        return otherIncomeCatName;
//    }

//    public List<OtherIncomeExpenseTypeDetail> getOtherIncomeExpenseTypeDetailList() {
//		return otherIncomeExpenseTypeDetailList;
//	}
//
//	public void setOtherIncomeExpenseTypeDetailList(List<OtherIncomeExpenseTypeDetail> otherIncomeExpenseTypeDetailList) {
//		this.otherIncomeExpenseTypeDetailList = otherIncomeExpenseTypeDetailList;
//	}

//	public void setOtherIncomeCatName(String otherIncomeCatName) {
//        this.otherIncomeCatName = otherIncomeCatName;
//    }
	
	public Long getExpenseTypeId() {
        return expenseType.getId();
    }
	
	public String getExpenseTypeName() {
		return expenseType.getName();
	}
	
	public Long getOtherIncomeCategoryId() {
		return otherIncomeCategory.getId();
	}
	
	public String getOtherIncomeCategoryName() {
		return otherIncomeCategory.getName();
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
    
//    public List<OtherIncomeExpenseTypeDetail> getOtherIncomeExpenseTypeDetail() {
//        return otherIncomeExpenseTypeDetailList;
//    }
//
//    public void setOtherIncomeExpenseTypeDetail(List<OtherIncomeExpenseTypeDetail> otherIncomeExpenseTypeDetailList) {
//        this.otherIncomeExpenseTypeDetailList = otherIncomeExpenseTypeDetailList;
//    }
    
    
}