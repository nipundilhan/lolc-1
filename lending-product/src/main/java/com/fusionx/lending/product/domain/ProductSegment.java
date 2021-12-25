package com.fusionx.lending.product.domain;

/**
 * Product Details Service -ProductSegment Domain entity
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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

@Entity
@Table(name = "product_segment")
@Transactional
public class ProductSegment extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1988L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "product_id", nullable=false)
	private Product product;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "segment_id", nullable=true)
	private Segments segments;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	private Long producstId ;
	
	@Transient
	private String productName ;
	
	@Transient
	private String segmentName ;
	
	@Transient
	private String segmentCode ;
	
	@Transient
	private Long segmentId ;
	
	public ProductSegment() {
		super();
	}

	public Long getProducstId() {
		if(product!=null) {
			return producstId = product.getId();
		}else 
			return null;
	}

	public void setProducstId(Long producstId) {
		this.producstId = producstId;
	}

	public String getProductName() {
		if(product!=null) {
			return productName = product.getProductName();
		}else 
			return null;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Segments getSegments() {
		return segments;
	}

	public void setSegments(Segments segments) {
		this.segments = segments;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Long getSegmentId() {
		if(segments!=null) {
			return segmentId=segments.getId();
		}else 
			return null;
	}

	public void setSegmentId(Long segmentId) {
		this.segmentId = segmentId;
	}
	
	public String getSegmentCode() {
		if(segments!=null) {
			return segmentCode=segments.getCode();
		}else 
			return null;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getSegmentName() {
		if(segments!=null) {
			return segmentName=segments.getName();
		}else 
			return null;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}


	
}
