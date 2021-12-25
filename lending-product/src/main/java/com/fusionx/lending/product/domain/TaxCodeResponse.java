package com.fusionx.lending.product.domain;

import com.fusionx.lending.product.enums.ServiceStatus;

public class TaxCodeResponse {

	private Long id;
	private String taxCode;
	private String taxCodeName;
	private String taxCodeStatus;
	private ServiceStatus serviceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getTaxCodeName() {
		return taxCodeName;
	}

	public void setTaxCodeName(String taxCodeName) {
		this.taxCodeName = taxCodeName;
	}

	public String getTaxCodeStatus() {
		return taxCodeStatus;
	}

	public void setTaxCodeStatus(String taxCodeStatus) {
		this.taxCodeStatus = taxCodeStatus;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
