package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class FacilityStructureRequestResource {

	@NotBlank(message = "{sequence.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String sequence;
	
	@NotBlank(message = "{period.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String period;
	
	@NotBlank(message = "{installment.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String installment;
	
	@NotBlank(message = "{rate.not-null}")
	@Pattern(regexp = "^$|\\\\d{1,20}\\\\.\\\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String rate;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalTaxes;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalCharges;
	
	@NotBlank(message = "{totalInstallment.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
	private String totalInstallment;
	
	@Valid
	private List<FacilityCalculationTaxRequestResource> taxes;
	
	@Valid
	private List<FacilityCalculationFixedChargesRequestResource> fixedCharges;
	
	@Valid
	private List<FacilityCalculationOptionalChargesRequestResource> optionalCharges;
	
	@Valid
	private List<FacilityCalculationPeriodicChargesRequestResource> periodicCharges;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getInstallment() {
		return installment;
	}

	public void setInstallment(String installment) {
		this.installment = installment;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(String totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public String getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}

	public String getTotalInstallment() {
		return totalInstallment;
	}

	public void setTotalInstallment(String totalInstallment) {
		this.totalInstallment = totalInstallment;
	}

	public List<FacilityCalculationTaxRequestResource> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<FacilityCalculationTaxRequestResource> taxes) {
		this.taxes = taxes;
	}

	public List<FacilityCalculationFixedChargesRequestResource> getFixedCharges() {
		return fixedCharges;
	}

	public void setFixedCharges(List<FacilityCalculationFixedChargesRequestResource> fixedCharges) {
		this.fixedCharges = fixedCharges;
	}

	public List<FacilityCalculationOptionalChargesRequestResource> getOptionalCharges() {
		return optionalCharges;
	}

	public void setOptionalCharges(List<FacilityCalculationOptionalChargesRequestResource> optionalCharges) {
		this.optionalCharges = optionalCharges;
	}

	public List<FacilityCalculationPeriodicChargesRequestResource> getPeriodicCharges() {
		return periodicCharges;
	}

	public void setPeriodicCharges(List<FacilityCalculationPeriodicChargesRequestResource> periodicCharges) {
		this.periodicCharges = periodicCharges;
	}
}
