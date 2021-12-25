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
 * Feature Benifit Template Item Eligibility History Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		     			NipunDilhan      Created
 *    
 ********************************************************************************************************
 */ 

@Entity
@Table(name = "featur_bene_templ_item_eli_his")
@Data
public class FeatureBenefitTemplateItemEligibilityHistory  extends BaseEntity implements Serializable {
	

	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
		
	@Column(name = "feature_bene_templ_item_eli_id", nullable=false)
	private Long featureBenifitTemplateEligibilityId;
	
	@Column(name = "feat_ben_temp_item_eli_pend_id", nullable=true)
	private Long featureBenifitTemplateItemEligibilityPendingId;
	
	@Column(name = "feature_benefit_eligibility_id", nullable=false)
	private Long featureBenifitEligibilityId;
	
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

	public Long getFeatureBenifitTemplateEligibilityId() {
		return featureBenifitTemplateEligibilityId;
	}

	public void setFeatureBenifitTemplateEligibilityId(Long featureBenifitTemplateEligibilityId) {
		this.featureBenifitTemplateEligibilityId = featureBenifitTemplateEligibilityId;
	}

	public Long getFeatureBenifitTemplateItemEligibilityPendingId() {
		return featureBenifitTemplateItemEligibilityPendingId;
	}

	public void setFeatureBenifitTemplateItemEligibilityPendingId(Long featureBenifitTemplateItemEligibilityPendingId) {
		this.featureBenifitTemplateItemEligibilityPendingId = featureBenifitTemplateItemEligibilityPendingId;
	}

	public Long getFeatureBenifitEligibilityId() {
		return featureBenifitEligibilityId;
	}

	public void setFeatureBenifitEligibilityId(Long featureBenifitEligibilityId) {
		this.featureBenifitEligibilityId = featureBenifitEligibilityId;
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
