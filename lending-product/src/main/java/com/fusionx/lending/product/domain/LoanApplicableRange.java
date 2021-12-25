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

import lombok.Data;

@Entity
@Data
@Table(name = "loan_applicable_range")
public class LoanApplicableRange extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6596435981963640705L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;

	@Column(name = "code", length=4, nullable=false)
	private String code;

	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name="minimum_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal minimumAmount;
	
	@Column(name = "maximum_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal maximumAmount;
	
	@Column(name = "minimum_rate", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal minimumRate;
	
	@Column(name = "maximum_rate", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal maximumRate;
	
	@Column(name = "default_rate", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal defaultRate;
	
	@Column(name = "default_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal defaultAmount;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

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

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public BigDecimal getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(BigDecimal maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public BigDecimal getMaximumRate() {
		return maximumRate;
	}

	public void setMaximumRate(BigDecimal maximumRate) {
		this.maximumRate = maximumRate;
	}

	public BigDecimal getMinimumRate() {
		return minimumRate;
	}

	public void setMinimumRate(BigDecimal minimumRate) {
		this.minimumRate = minimumRate;
	}

	public BigDecimal getDefaultRate() {
		return defaultRate;
	}

	public void setDefaultRate(BigDecimal defaultRate) {
		this.defaultRate = defaultRate;
	}

	public BigDecimal getdefaultAmount() {
		return defaultAmount;
	}

	public void setDefaultAmount(BigDecimal defaultAmount) {
		this.defaultAmount = defaultAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
