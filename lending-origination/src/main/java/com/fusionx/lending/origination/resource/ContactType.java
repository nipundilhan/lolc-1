package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class ContactType {

	private String cntpCode;
	private String cntpDesc;
	private ServiceStatus serviceStatus;
	
	// added by Senitha
	private Long id;
	private String cntpStatus;
	private String cntpVisualFormtValidtion;
	
	public String getCntpCode() {
		return cntpCode;
	}
	public void setCntpCode(String cntpCode) {
		this.cntpCode = cntpCode;
	}
	public String getCntpDesc() {
		return cntpDesc;
	}
	public void setCntpDesc(String cntpDesc) {
		this.cntpDesc = cntpDesc;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCntpStatus() {
		return cntpStatus;
	}
	public void setCntpStatus(String cntpStatus) {
		this.cntpStatus = cntpStatus;
	}
	public String getCntpVisualFormtValidtion() {
		return cntpVisualFormtValidtion;
	}
	public void setCntpVisualFormtValidtion(String cntpVisualFormtValidtion) {
		this.cntpVisualFormtValidtion = cntpVisualFormtValidtion;
	}
	
}
