package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CollectionTypeEnum;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

/**
 * Other Fee Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "other_fee_type")
public class OtherFeeType extends BaseEntity implements Serializable{


	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fee_category_id")
	private Long feeCategoryId;
	
	@Column(name = "transaction_code_id")
	private Long transactionCodeId;
	
	@Column(name = "transaction_sub_code_id")
	private Long transactionSubCodeId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "refundable", nullable=false, length=20)
	private YesNo refundable;
	
	@Column(name="refundable_percentage", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal refundablePercentage;	
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "collection_type", nullable=false, length=20)
	private CollectionTypeEnum collectionType;
	
	@Transient
	private String feeCategoryName;
	
	@Transient
	private String transactionCode;
	
	@Transient
	private String transactionCodeDesc;
	
	@Transient
	private String transactionSubCode;
	
	@Transient
	private String transactionSubCodeDesc;

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

	public Long getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(Long feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public Long getTransactionCodeId() {
		return transactionCodeId;
	}

	public void setTransactionCodeId(Long transactionCodeId) {
		this.transactionCodeId = transactionCodeId;
	}

	public Long getTransactionSubCodeId() {
		return transactionSubCodeId;
	}

	public void setTransactionSubCodeId(Long transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
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

	public String getFeeCategoryName() {
		return feeCategoryName;
	}

	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionCodeDesc() {
		return transactionCodeDesc;
	}

	public void setTransactionCodeDesc(String transactionCodeDesc) {
		this.transactionCodeDesc = transactionCodeDesc;
	}

	public String getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	public String getTransactionSubCodeDesc() {
		return transactionSubCodeDesc;
	}

	public void setTransactionSubCodeDesc(String transactionSubCodeDesc) {
		this.transactionSubCodeDesc = transactionSubCodeDesc;
	}
	
	public YesNo getRefundable() {
		return refundable;
	}

	public void setRefundable(YesNo refundable) {
		this.refundable = refundable;
	}

	public BigDecimal getRefundablePercentage() {
		return refundablePercentage;
	}

	public void setRefundablePercentage(BigDecimal refundablePercentage) {
		this.refundablePercentage = refundablePercentage;
	}
	
	
	public CollectionTypeEnum getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionTypeEnum collectionType) {
		this.collectionType = collectionType;
	}
	
}
