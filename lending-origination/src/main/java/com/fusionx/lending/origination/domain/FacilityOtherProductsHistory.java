package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "facility_other_products_his")
@Data
public class FacilityOtherProductsHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4537474747L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "lead_info_id", nullable=false)
	private Long leadInfoId;
	
	@Column(name = "product_category_code_id", nullable=false)
	private Long productCategoryCodeId;
	
	@Column(name = "facility_other_products_id", nullable=false)
	private Long facilityOtherProductsId;
	
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
	
	@Column(name = "hcreated_user")
	private String hcreatedUser;
	
	@Column(name = "hcreated_date")
	private Timestamp hcreatedDate;
	
	@Column(name = "onboard_product_id")
	private Long onboardProductId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getProductCategoryCodeId() {
		return productCategoryCodeId;
	}

	public void setProductCategoryCodeId(Long productCategoryCodeId) {
		this.productCategoryCodeId = productCategoryCodeId;
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
	public Long getFacilityOtherProductsId() {
		return facilityOtherProductsId;
	}

	public void setFacilityOtherProductsId(Long facilityOtherProductsId) {
		this.facilityOtherProductsId = facilityOtherProductsId;
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

	public Long getLeadInfoId() {
		return leadInfoId;
	}

	public void setLeadInfoId(Long leadInfoId) {
		this.leadInfoId = leadInfoId;
	}

	public Long getOnboardProductId() {
		return onboardProductId;
	}

	public void setOnboardProductId(Long onboardProductId) {
		this.onboardProductId = onboardProductId;
	}
	
	
}
