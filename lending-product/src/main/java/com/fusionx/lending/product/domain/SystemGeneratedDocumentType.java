package com.fusionx.lending.product.domain;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.core.MasterPropertyBase;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;
/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "system_gen_doc_type")
@Data
public class SystemGeneratedDocumentType extends BaseEntity implements Serializable {
 
	private static final long serialVersionUID = -2713278937299917512L;

	@Column(name = "tenant_id", length = 20, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sub_product_id", nullable = false)
	private SubProduct subProduct;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicable_level", nullable = false)
	private CommonListItem applicableLevel;

	@Column(name = "code", length = 4, nullable = false)
	private String code; 

	@Column(name = "sub_product_name", length=350, nullable=false)
	private String subProductName;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "product_name", length=350, nullable=false)
	private String productName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", length = 255, nullable = true)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<SystemGeneratedDocumentTypeDetails> documentDetails;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SubProduct getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(SubProduct subProduct) {
		this.subProduct = subProduct;
	}

	public CommonListItem getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(CommonListItem applicableLevel) {
		this.applicableLevel = applicableLevel;
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

	public Long getProductId() {
		return product.getId();
	}

	public Long getSubProductId() {
		return subProduct.getId();
	}

	public Long getApplicableLevelId() {
		return applicableLevel.getId();
	}

	public String getApplicableLevelName() {
		return applicableLevel.getName();
	}

	public List<SystemGeneratedDocumentTypeDetails> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<SystemGeneratedDocumentTypeDetails> documentDetails) {
		this.documentDetails = documentDetails;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
