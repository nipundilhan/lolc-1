package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class NewFacilityUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "NEW|TOPUP", message = "{facility-type.pattern}")
	private String facilityType;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalDueAmount;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalSettlementAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String newLoanAmount;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String availableBalance;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<FacilityContractDetailsResource> contractDetails;

	@Valid
	private List<FacilityOtherProductsResource> otherProducts;

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getTotalDueAmount() {
		return totalDueAmount;
	}

	public void setTotalDueAmount(String totalDueAmount) {
		this.totalDueAmount = totalDueAmount;
	}

	public String getTotalSettlementAmount() {
		return totalSettlementAmount;
	}

	public void setTotalSettlementAmount(String totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}

	public String getNewLoanAmount() {
		return newLoanAmount;
	}

	public void setNewLoanAmount(String newLoanAmount) {
		this.newLoanAmount = newLoanAmount;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FacilityContractDetailsResource> getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(List<FacilityContractDetailsResource> contractDetails) {
		this.contractDetails = contractDetails;
	}

	public List<FacilityOtherProductsResource> getOtherProducts() {
		return otherProducts;
	}

	public void setOtherProducts(List<FacilityOtherProductsResource> otherProducts) {
		this.otherProducts = otherProducts;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
