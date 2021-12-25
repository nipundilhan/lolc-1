package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.core.BaseEntity;

@Entity
@Table(name = "risk_calculation_detail")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RiskCalculationDetail   extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2756827696056699314L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;
	
	@Column(name = "score",  nullable=false)
	private BigDecimal score;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_calculation_id", nullable=false)
	private RiskCalculation riskCalculation;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_template_detail_id", nullable=false)
	private RiskTemplateDetail riskTemplateDetail;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;
	
	@Transient
	private Long riskMainCriteriaId;
	
	@Transient
	private String riskMainCriteriaName;

	@Transient
	private Long riskSubCriteriaId;
	
	@Transient
	private String riskSubCriteriaName;
	
	@Transient
	private double percentage;
	
	public double getPercentage() {
		if(riskTemplateDetail != null) {
			return riskTemplateDetail.getWeightagePercentage();

		} else {
			return 0;
		}
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public RiskCalculation getRiskCalculation() {
		return riskCalculation;
	}

	public void setRiskCalculation(RiskCalculation riskCalculation) {
		this.riskCalculation = riskCalculation;
	}

	public RiskTemplateDetail getRiskTemplateDetail() {
		return riskTemplateDetail;
	}

	public void setRiskTemplateDetail(RiskTemplateDetail riskTemplateDetail) {
		this.riskTemplateDetail = riskTemplateDetail;
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
	
	public String getRiskMainCriteriaName() {
		if(riskTemplateDetail != null) {
			if(riskTemplateDetail.getRiskParameterTemplates() != null) {
				return  riskTemplateDetail.getRiskParameterTemplates().getRiskMainCriterias() != null ?
							riskTemplateDetail.getRiskParameterTemplates().getRiskMainCriterias().getName() : null;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setRiskMainCriteriaName(String riskMainCriteriaName) {
		this.riskMainCriteriaName = riskMainCriteriaName;
	}
	
	public String getRiskSubCriteriaName() {
		if(riskTemplateDetail != null) {
			if(riskTemplateDetail.getRiskParameterTemplates() != null) {
				return  riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias() != null ?
							riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias().getName() : null;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void setRiskSubCriteriaName(String riskMainCriteriaName) {
		this.riskMainCriteriaName = riskMainCriteriaName;
	}

	public Long getRiskMainCriteriaId() {
		
		if(riskTemplateDetail != null) {
			if(riskTemplateDetail.getRiskParameterTemplates() != null) {
				return  riskTemplateDetail.getRiskParameterTemplates().getRiskMainCriterias() != null ?
							riskTemplateDetail.getRiskParameterTemplates().getRiskMainCriterias().getId() : null;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setRiskMainCriteriaId(Long riskMainCriteriaId) {
		this.riskMainCriteriaId = riskMainCriteriaId;
	}
	
	
	public void setRiskSubCriteriaId(Long riskSubCriteriaId) {
		this.riskSubCriteriaId = riskSubCriteriaId;
	}
	
	public Long getRiskSubCriteriaId() {
		
		if(riskTemplateDetail != null) {
			if(riskTemplateDetail.getRiskParameterTemplates() != null) {
				return  riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias() != null ?
							riskTemplateDetail.getRiskParameterTemplates().getRiskSubCriterias().getId() : null;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
	

}
