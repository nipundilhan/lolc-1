package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Risk Parameter Template calculate field
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "risk_para_temp_cal_feild")
@Data
public class RiskParaTempCalFeild extends BaseEntity implements Serializable{
	
private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore   // Foreign key with Risk parameter template
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_parameter_template_id", nullable = false)
	private RiskParameterTemplate riskParameterTemplates;
	
	@Transient
    private Long riskParameterTemplateId;
	
	@JsonIgnore  // Foreign key with field setup
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "field_setup_id", nullable = false)
	private FieldSetup fieldSetups;
	
	@Transient
    private Long fieldSetupId;
	
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public RiskParameterTemplate getRiskParameterTemplates() {
		return riskParameterTemplates;
	}

	public void setRiskParameterTemplates(RiskParameterTemplate riskParameterTemplates) {
		this.riskParameterTemplates = riskParameterTemplates;
	}

	public FieldSetup getFieldSetups() {
		return fieldSetups;
	}

	public void setFieldSetups(FieldSetup fieldSetups) {
		this.fieldSetups = fieldSetups;
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
	
	public Long getRiskParameterTemplateId() {
		return riskParameterTemplates.getId();
	}
	

	public Long getFieldSetupId() {
		return fieldSetups.getId();
	}
}
