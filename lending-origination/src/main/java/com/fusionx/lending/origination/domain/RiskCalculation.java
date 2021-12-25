package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


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
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.resource.RiskMainCriteriaResponse;

@Entity
@Table(name = "risk_calculation")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RiskCalculation  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2756827696056699314L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_info_id", nullable=false)
	private LeadInfo leadInfo;
	
	@Column(name = "total_score",  nullable=false)
	private BigDecimal totalScore;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_grading_detail_id", nullable=false)
	private RiskGradingDetail riskGradingDetail;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_template_id", nullable=false)
	private RiskTemplate riskTemplate;

	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=true)
	private CommonStatus status;
	
	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	
	@Transient
	private List<RiskCalculationDetail> riskCalculationDetailsList;
	
	@Transient
	private Long riskGradingDetailId;
	
	@Transient
	private String riskGradingDetailName;
	
	@Transient
	private List<RiskMainCriteriaResponse> riskMainCriteriaResponseList;
	
	public List<RiskMainCriteriaResponse> getRiskMainCriteriaResponseList() {
		return riskMainCriteriaResponseList;
	}

	public void setRiskMainCriteriaResponseList(List<RiskMainCriteriaResponse> riskMainCriteriaResponseList) {
		this.riskMainCriteriaResponseList = riskMainCriteriaResponseList;
	}






	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfo) {
		this.leadInfo = leadInfo;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public RiskGradingDetail getRiskGradingDetail() {
		return riskGradingDetail;
	}

	public void setRiskGradingDetail(RiskGradingDetail riskGradingDetail) {
		this.riskGradingDetail = riskGradingDetail;
	}

	public RiskTemplate getRiskTemplate() {
		return riskTemplate;
	}

	public void setRiskTemplate(RiskTemplate riskTemplate) {
		this.riskTemplate = riskTemplate;
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

	public List<RiskCalculationDetail> getRiskCalculationDetailsList() {
		return riskCalculationDetailsList;
	}
	
	

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public void setRiskCalculationDetailsList(List<RiskCalculationDetail> riskCalculationDetailsList) {
		this.riskCalculationDetailsList = riskCalculationDetailsList;
	}
	
	
	public Long getRiskGradingDetailId() {
		if(riskGradingDetail != null) {
			return riskGradingDetail.getId();
		} else {
			return null;
		}
	}

	public void setRiskGradingDetailId(Long riskGradingDetailId) {
		this.riskGradingDetailId = riskGradingDetailId;
	}

	public String getRiskGradingDetailName() {
		if(riskGradingDetail != null) {
			return riskGradingDetail.getGrading();
		} else {
			return null;
		}
	}

	public void setRiskGradingDetailName(String riskGradingDetailName) {
		this.riskGradingDetailName = riskGradingDetailName;
	}
	
	
	

}
