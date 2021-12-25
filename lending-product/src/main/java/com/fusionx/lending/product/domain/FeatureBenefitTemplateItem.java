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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;


/**
 * Feature Benifit Template Item Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-07-2021      		     			NipunDilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "feature_benefit_template_item")
@Data
public class FeatureBenefitTemplateItem   extends BaseEntity implements Serializable {


	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benefit_template_id", nullable = false)
	private FeatureBenifitTemplate featureBenefitTemplate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "feature_benefit_item_id", nullable = false)
	private FeatureBenefitItem featureBenefitItem;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	private Long featureBenifitItemId;
	
	@Transient
	private String featureBenifitItemName;
	
	
	

	public Long getFeatureBenifitItemId() {		
		if(featureBenefitItem != null) {
			return featureBenefitItem.getId();
		} else {
			return null;
		}
	}


	public String getFeatureBenifitItemName() {
		if(featureBenefitItem != null) {
			return featureBenefitItem.getName();
		} else {
			return null;
		}
	}



	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
	public FeatureBenifitTemplate getFeatureBenefitTemplate() {
		return featureBenefitTemplate;
	}

	public void setFeatureBenefitTemplate(FeatureBenifitTemplate featureBenefitTemplate) {
		this.featureBenefitTemplate = featureBenefitTemplate;
	}

	public FeatureBenefitItem getFeatureBenefitItem() {
		return featureBenefitItem;
	}

	public void setFeatureBenefitItem(FeatureBenefitItem featureBenefitItem) {
		this.featureBenefitItem = featureBenefitItem;
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
