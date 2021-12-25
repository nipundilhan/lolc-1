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
 * Risk Parameter Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "risk_parameter_template")
@Data
public class RiskParameterTemplate extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "calculation_method")
	private String calculationMethod;

	@Column(name = "equation")
	private String equation;
	
	@JsonIgnore   // Foreign key with risk main criteria
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_main_criteria_id", nullable = false)
	private RiskMainCriteria riskMainCriterias;
	
	@Transient
    private Long riskMainCriteriaId;
	
	@JsonIgnore  // Foreign key with risk sub criteria
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_sub_criteria_id", nullable = false)
	private RiskSubCriteria riskSubCriterias;
	
	@Transient
    private Long riskSubCriteriaId;
	
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
	private List<RiskParaTempCalFeild> riskParaTempCalFeild;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}

	public RiskMainCriteria getRiskMainCriterias() {
		return riskMainCriterias;
	}

	public void setRiskMainCriterias(RiskMainCriteria riskMainCriterias) {
		this.riskMainCriterias = riskMainCriterias;
	}

	public RiskSubCriteria getRiskSubCriterias() {
		return riskSubCriterias;
	}

	public void setRiskSubCriterias(RiskSubCriteria riskSubCriterias) {
		this.riskSubCriterias = riskSubCriterias;
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

	public List<RiskParaTempCalFeild> getRiskParaTempCalFeild() {
		return riskParaTempCalFeild;
	}

	public void setRiskParaTempCalFeild(List<RiskParaTempCalFeild> riskParaTempCalFeild) {
		this.riskParaTempCalFeild = riskParaTempCalFeild;
	}
	
	//@transient columns

	public Long getRiskMainCriteriaId() {
		return riskMainCriterias.getId();
	}
	
	public Long getRiskSubCriteriaId() {
		return riskSubCriterias.getId();
	}
	

}
