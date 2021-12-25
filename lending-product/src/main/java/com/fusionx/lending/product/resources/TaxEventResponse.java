package com.fusionx.lending.product.resources;

import java.util.List;

import com.fusionx.lending.product.enums.ServiceStatus;

public class TaxEventResponse {
	
	private Long id;
	private String code;
	private String status;
	private ServiceStatus serviceStatus;
	
	private List<TaxEventDetailResponse> taxEventDetails;
	
	public List<TaxEventDetailResponse> getTaxEventDetails() {
		return taxEventDetails;
	}
	public void setTaxEventDetails(List<TaxEventDetailResponse> taxEventDetails) {
		this.taxEventDetails = taxEventDetails;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	

}
