package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ConditionType;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

/**
 * Disbursement Conditions
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-09-2021      		     FXL-788    Dilhan      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "disbursement_conditions")
@Data
public class DisbursementConditions extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -4749520000430111887L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "condition_type", length=20, nullable=false)
	private ConditionType conditionType;
	
	@Column(name = "texual", length=350, nullable=true)
	private String texual;
	
	@Column(name = "period_id",nullable=true)
	private Long periodId;
	
	@Column(name = "period_code", nullable=true)
	private String periodCode;
	
	@Column(name = "period_number" ,nullable=false)
	private Integer periodNumber;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "indicator_flag", nullable=false, length=20)
	private YesNo indicatorFlag;
	
	@Column(name="amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal amount;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public ConditionType getConditionType() {
		return conditionType;
	}

	public void setConditionType(ConditionType conditionType) {
		this.conditionType = conditionType;
	}

	public String getTexual() {
		return texual;
	}

	public void setTexual(String texual) {
		this.texual = texual;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	public Integer getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	public YesNo getIndicatorFlag() {
		return indicatorFlag;
	}

	public void setIndicatorFlag(YesNo indicatorFlag) {
		this.indicatorFlag = indicatorFlag;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
