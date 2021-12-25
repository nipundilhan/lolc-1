package com.fusionx.lending.product.domain;

/**
 * Product History Service - Domain entity
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fusionx.lending.product.core.BaseEntity;

@Entity
@Table(name = "product_his")
@Transactional
public class ProductHistory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1988L;

	@Column(name = "product_id",  nullable=false)
	private Long productId;
	
	@Column(name = "brand_id", nullable=true)
	private Long brandId;
	
	@Column(name = "product_group_id", nullable=true)
	private Long productGroupId;
	
	@Column(name = "product_code", length=20, nullable=false)
	private String productCode;
	
	@Column(name = "product_name", length=350,  nullable=false)
	private String productName;
	
	@Column(name = "description", length=2500, nullable=true)
	private String description;
	
//	@Column(name = "on_sale_indicator",  nullable=true)
//	private Boolean onSaleIndicator;
//	
//	@Column(name = "fee_free_length",  nullable=true)
//	private Long feeFreeLength;
//	
//	@Column(name = "fee_free_length_perid_id",  nullable=true)
//	private Long feeFreeLengthPeridId;
//	
//	@Column(name = "product_category_id", nullable=false)
//	private Long productCategoryId;
//	
//	@Column(name = "product_group_id",  nullable=true)
//	private Long productGroupId;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "history_created_user", length=20, nullable=false)
	private String hcreatedUser;
	
	@Column(name = "history_created_date", nullable=false)
	private Timestamp hcreatedDate;
	
	public ProductHistory() {
		super();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

//	public Boolean getOnSaleIndicator() {
//		return onSaleIndicator;
//	}
//
//	public void setOnSaleIndicator(Boolean onSaleIndicator) {
//		this.onSaleIndicator = onSaleIndicator;
//	}
//
//	public Long getFeeFreeLength() {
//		return feeFreeLength;
//	}
//
//	public void setFeeFreeLength(Long feeFreeLength) {
//		this.feeFreeLength = feeFreeLength;
//	}
//
//	public Long getFeeFreeLengthPeridId() {
//		return feeFreeLengthPeridId;
//	}
//
//	public void setFeeFreeLengthPeridId(Long feeFreeLengthPeridId) {
//		this.feeFreeLengthPeridId = feeFreeLengthPeridId;
//	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getHcreatedUser() {
		return hcreatedUser;
	}

	public void setHcreatedUser(String hcreatedUser) {
		this.hcreatedUser = hcreatedUser;
	}

	public Timestamp getHcreatedDate() {
		return hcreatedDate;
	}

	public void setHcreatedDate(Timestamp hcreatedDate) {
		this.hcreatedDate = hcreatedDate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(Long productGroupId) {
		this.productGroupId = productGroupId;
	}


//	public Long getProductCategoryId() {
//		return productCategoryId;
//	}
//
//	public void setProductCategoryId(Long productCategoryId) {
//		this.productCategoryId = productCategoryId;
//	}
//
//	public Long getProductGroupId() {
//		return productGroupId;
//	}
//
//	public void setProductGroupId(Long productGroupId) {
//		this.productGroupId = productGroupId;
//	}
	
	
}
