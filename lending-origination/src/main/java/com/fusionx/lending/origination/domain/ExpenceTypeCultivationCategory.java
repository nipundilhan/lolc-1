package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Expence Type Cultivation Category  Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "expence_type_culti_cat")
@Data
public class ExpenceTypeCultivationCategory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4537474747L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cultivation_category_id", nullable=false)
	private CultivationCategory cultivationCategory;
	
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
	private Long cultivationCatId;
	
	@Transient
	private Long expenseTypeId;
	
//	@Transient
//	@JsonInclude(Include.NON_NULL)
//	private List<ExpenceTypeCultivationCategoryDetails> expenceTypeCultivationCategoryDetails;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public CultivationCategory getCultivationCategory() {
		return cultivationCategory;
	}

	public void setCultivationCategory(CultivationCategory cultivationCategory) {
		this.cultivationCategory = cultivationCategory;
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

	public Long getCultivationCatId() {
		return cultivationCategory.getId();
	}
	
	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	
	public String getCultivationCatCode() {
		return cultivationCategory.getCode();
	}
	
	public String getCultivationCatName() {
		return cultivationCategory.getName();
	}
	
	public Long getExpenseTypeId() {
		return expenseType.getId();
	}
	
	public String getExpenseTypeName() {
		return expenseType.getName();
	}
	
	public String getExpenseTypeCode() {
		return expenseType.getCode();
	}
}
