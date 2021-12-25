package com.fusionx.lending.product.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Feature Benifit Template Item Eligibility Pending Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		     			NipunDilhan      Created
 *    
 ********************************************************************************************************
 */ 
@Entity
@Table(name = "feature_ben_temp_item_eli_pend")
@Data
public class FeatureBenefitTemplateItemEligibilityPending     extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4476957009090669105L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benef_template_pend_id", nullable = false)
	private FeatureBenifitTemplatePending featureBenefitTemplatePending;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "featur_benef_temp_item_pend_id", nullable = true)
	private FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_bene_templ_item_eli_id", nullable = false)
	private FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benefit_eligibility_id", nullable = false)
	private FeatureBenefitEligibility featureBenefitEligibility;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benef_template_item_id", nullable = false)
	private FeatureBenefitTemplateItem featureBenefitTemplateItem;

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


	public FeatureBenifitTemplatePending getFeatureBenefitTemplatePending() {
		return featureBenefitTemplatePending;
	}

	public void setFeatureBenefitTemplatePending(FeatureBenifitTemplatePending featureBenefitTemplatePending) {
		this.featureBenefitTemplatePending = featureBenefitTemplatePending;
	}
	
	public FeatureBenefitTemplateItemPending getFeatureBenefitTemplateItemPending() {
		return featureBenefitTemplateItemPending;
	}

	public void setFeatureBenefitTemplateItemPending(FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending) {
		this.featureBenefitTemplateItemPending = featureBenefitTemplateItemPending;
	}
	
	public FeatureBenefitTemplateItemEligibility getFeatureBenefitTemplateItemEligibility() {
		return featureBenefitTemplateItemEligibility;
	}

	public void setFeatureBenefitTemplateItemEligibility(
			FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility) {
		this.featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibility;
	}


	public FeatureBenefitEligibility getFeatureBenefitEligibility() {
		return featureBenefitEligibility;
	}

	public void setFeatureBenefitEligibility(FeatureBenefitEligibility featureBenefitEligibility) {
		this.featureBenefitEligibility = featureBenefitEligibility;
	}
	
	public FeatureBenefitTemplateItem getFeatureBenefitTemplateItem() {
		return featureBenefitTemplateItem;
	}

	public void setFeatureBenefitTemplateItem(FeatureBenefitTemplateItem featureBenefitTemplateItem) {
		this.featureBenefitTemplateItem = featureBenefitTemplateItem;
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
