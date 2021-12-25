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
 * Document Check List  Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   11-07-2021      		            	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "document_checklist")
@Data
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class DocumentCheckList extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8458849928278154630L;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@JsonIgnore	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", nullable=false)
	private Product product;
	
	@JsonIgnore	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sub_product_id", nullable=false)
	private SubProduct subProduct;
	
	@JsonIgnore	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicable_level", nullable=false)
	private CommonListItem applicableLevel;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;

	@Column(name = "sub_product_name", length=350, nullable=false)
	private String subProductName;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "product_name", length=350, nullable=false)
	private String productName;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<DocumentCheckListDetails> documentCheckListDetails;

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

	public List<DocumentCheckListDetails> getDocumentCheckListDetails() {
		return documentCheckListDetails;
	}

	public void setDocumentCheckListDetails(List<DocumentCheckListDetails> documentCheckListDetails) {
		this.documentCheckListDetails = documentCheckListDetails;
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
	
}
