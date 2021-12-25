package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

public class BankTransactionCodeResponse {
	
	private Long id;
	private String bankTransCode;
	private String description;
	private String status;
	private ServiceStatus serviceStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankTransCode() {
		return bankTransCode;
	}
	public void setBankTransCode(String bankTransCode) {
		this.bankTransCode = bankTransCode;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
