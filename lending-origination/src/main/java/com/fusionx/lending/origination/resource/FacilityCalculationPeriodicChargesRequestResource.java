package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FacilityCalculationPeriodicChargesRequestResource {

	@NotBlank(message = "{chargeTypeCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String chargeTypeCode;
	
	@NotBlank(message = "{chargeTypeName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String chargeTypeName;
	
	@NotBlank(message = "{criteriaCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String criteriaCode;
	
	@NotBlank(message = "{criteriaName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String criteriaName;
	
	@NotBlank(message = "{frequencyCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String frequencyCode;
	
	@NotBlank(message = "{frequencyName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String frequencyName;
	
	@NotNull(message = "{chargesDetail.not-null}")
	@Valid
	private List<FacilityCalculationPeriodicChargesDetailRequestResource> chargeDetails;
	
	private String chargeDetail;

	public String getChargeTypeCode() {
		return chargeTypeCode;
	}

	public void setChargeTypeCode(String chargeTypeCode) {
		this.chargeTypeCode = chargeTypeCode;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public String getCriteriaCode() {
		return criteriaCode;
	}

	public void setCriteriaCode(String criteriaCode) {
		this.criteriaCode = criteriaCode;
	}

	public String getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public List<FacilityCalculationPeriodicChargesDetailRequestResource> getChargeDetails() {
		return chargeDetails;
	}

	public void setChargeDetails(List<FacilityCalculationPeriodicChargesDetailRequestResource> chargeDetails) {
		this.chargeDetails = chargeDetails;
	}

	public String getChargeDetail() {
		return chargeDetail;
	}

	public void setChargeDetail(String chargeDetail) {
		this.chargeDetail = chargeDetail;
	}
}
