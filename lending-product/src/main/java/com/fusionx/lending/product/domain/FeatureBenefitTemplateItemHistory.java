package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Feature Benifit Template Item History Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		     			NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "feature_benefit_temp_item_hist")
@Data
public class FeatureBenefitTemplateItemHistory    extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
		
	@Column(name = "feature_benef_template_item_id", nullable=false)
	private Long featureBenifitTemplateItemId;
	
	@Column(name = "featur_benef_temp_item_pend_id", nullable=true)
	private Long featureBenifitTemplateItemPendingId;
	
	@Column(name = "feature_benefit_item_id", nullable=false)
	private Long featureBenifitItemId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getFeatureBenifitTemplateItemId() {
		return featureBenifitTemplateItemId;
	}

	public void setFeatureBenifitTemplateItemId(Long featureBenifitTemplateItemId) {
		this.featureBenifitTemplateItemId = featureBenifitTemplateItemId;
	}

	public Long getFeatureBenifitTemplateItemPendingId() {
		return featureBenifitTemplateItemPendingId;
	}

	public void setFeatureBenifitTemplateItemPendingId(Long featureBenifitTemplateItemPendingId) {
		this.featureBenifitTemplateItemPendingId = featureBenifitTemplateItemPendingId;
	}

	public Long getFeatureBenifitItemId() {
		return featureBenifitItemId;
	}

	public void setFeatureBenifitItemId(Long featureBenifitItemId) {
		this.featureBenifitItemId = featureBenifitItemId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	


}
