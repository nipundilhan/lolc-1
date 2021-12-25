package com.fusionx.lending.origination.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.RiskCalculationDetail;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RiskMainCriteriaResponse {
	
	
	private Long mainCriteriaId;
	
	private String mainCriteriaName;
	
	private BigDecimal totalPercentage;
	
	private BigDecimal totalScore;

	private List<RiskCalculationDetail> riskCalculationDetailsList;
	
	



	public List<RiskCalculationDetail> getRiskCalculationDetailsList() {
		return riskCalculationDetailsList;
	}

	public void setRiskCalculationDetailsList(List<RiskCalculationDetail> riskCalculationDetailsList) {
		this.riskCalculationDetailsList = riskCalculationDetailsList;
	}

	public Long getMainCriteriaId() {
		return mainCriteriaId;
	}

	public void setMainCriteriaId(Long mainCriteriaId) {
		this.mainCriteriaId = mainCriteriaId;
	}

	public String getMainCriteriaName() {
		return mainCriteriaName;
	}

	public void setMainCriteriaName(String mainCriteriaName) {
		this.mainCriteriaName = mainCriteriaName;
	}

	public BigDecimal getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(BigDecimal totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	
	

}