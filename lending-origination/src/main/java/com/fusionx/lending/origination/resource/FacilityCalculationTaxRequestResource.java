package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FacilityCalculationTaxRequestResource {

	@NotBlank(message = "{taxTypeCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String taxTypeCode;
	
	@NotBlank(message = "{taxTypeName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String taxTypeName;
	
	@NotBlank(message = "{taxApplicableOnCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String taxApplicableOnCode;
	
	@NotBlank(message = "{taxApplicableOnName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String taxApplicableOnName;

	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
    private String taxRate;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String taxAmount;
    
	@NotBlank(message = "{totalTaxAmount.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String totalTaxAmount;

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public String getTaxApplicableOnCode() {
		return taxApplicableOnCode;
	}

	public void setTaxApplicableOnCode(String taxApplicableOnCode) {
		this.taxApplicableOnCode = taxApplicableOnCode;
	}

	public String getTaxApplicableOnName() {
		return taxApplicableOnName;
	}

	public void setTaxApplicableOnName(String taxApplicableOnName) {
		this.taxApplicableOnName = taxApplicableOnName;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(String totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}
}
