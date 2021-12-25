package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

public class Currency {

	private String currencyCode;
	private String currencyName;
	private String codeNumeric;
	private String currencyStatus;
	private ServiceStatus serviceStatus;
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCodeNumeric() {
		return codeNumeric;
	}
	public void setCodeNumeric(String codeNumeric) {
		this.codeNumeric = codeNumeric;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getCurrencyStatus() {
		return currencyStatus;
	}
	public void setCurrencyStatus(String currencyStatus) {
		this.currencyStatus = currencyStatus;
	}
	
}
