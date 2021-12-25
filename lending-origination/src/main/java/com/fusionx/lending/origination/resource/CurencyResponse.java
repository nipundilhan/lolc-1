package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class CurencyResponse {

	private Long currencyId;
	
	private String currencyCode;
	
	private String currencyName;
	
	private String codeNumeric;
	
	private String currencyStatus;
	
	private ServiceStatus serviceStatus;

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyStatus() {
		return currencyStatus;
	}

	public void setCurrencyStatus(String currencyStatus) {
		this.currencyStatus = currencyStatus;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
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
}
