package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;


/**
 * Risk Template detail. 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "risk_template_detail")
@Data
public class RiskTemplateDetail extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "weightage_percentage")
	private Double weightagePercentage;
	
	@Column(name = "indicator")
	private String indicator;

	@JsonIgnore   // Foreign key with risk parameter template
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_parameter_template_id", nullable = false)
	private RiskParameterTemplate riskParameterTemplates;
	
	@Transient
    private Long riskParameterTemplateId;
	
	@JsonIgnore  // Foreign key with risk template detail variance
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_template_id", nullable = false)
	private RiskTemplate riskTemplates;
	
	@Transient
    private Long riskTemplateId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	

	@Transient
	private List<RiskTemplateDetailVariance> riskTemplateDetailVariance;
	
	@Transient
	private List<RiskTemplateDetailValuePaire> riskTemplateDetailValuePaire;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Double getWeightagePercentage() {
		return weightagePercentage;
	}

	public void setWeightagePercentage(Double weightagePercentage) {
		this.weightagePercentage = weightagePercentage;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public RiskParameterTemplate getRiskParameterTemplates() {
		return riskParameterTemplates;
	}

	public void setRiskParameterTemplates(RiskParameterTemplate riskParameterTemplates) {
		this.riskParameterTemplates = riskParameterTemplates;
	}

	public RiskTemplate getRiskTemplates() {
		return riskTemplates;
	}

	public void setRiskTemplates(RiskTemplate riskTemplates) {
		this.riskTemplates = riskTemplates;
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
	
	public List<RiskTemplateDetailVariance> getRiskTemplateDetailVariance() {
		return riskTemplateDetailVariance;
	}

	public void setRiskTemplateDetailVariance(List<RiskTemplateDetailVariance> riskTemplateDetailVariance) {
		this.riskTemplateDetailVariance = riskTemplateDetailVariance;
	}
		
	public List<RiskTemplateDetailValuePaire> getRiskTemplateDetailValuePaire() {
		return riskTemplateDetailValuePaire;
	}

	public void setRiskTemplateDetailValuePaire(List<RiskTemplateDetailValuePaire> riskTemplateDetailValuePaire) {
		this.riskTemplateDetailValuePaire = riskTemplateDetailValuePaire;
	}

	//@transient columns
	public Long getRiskParameterTemplateId() {
		return riskParameterTemplates.getId();
	}

	public Long getRiskTemplateId() {
		return riskTemplates.getId();
	}
}
