package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class LanguageResponse {
	
	private Long id;
	private String langCode;
	private String langDesc;
	private String langStatus;
	private ServiceStatus serviceStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getLangDesc() {
		return langDesc;
	}
	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	public String getLangStatus() {
		return langStatus;
	}
	public void setLangStatus(String langStatus) {
		this.langStatus = langStatus;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
