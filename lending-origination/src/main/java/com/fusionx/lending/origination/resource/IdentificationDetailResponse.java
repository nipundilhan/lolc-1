package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class IdentificationDetailResponse {

	private String idtpDesc;
	private String idtpStatus;
	private String idtpVisualFormt;
	private ServiceStatus serviceStatus;
	public String getIdtpVisualFormt() {
		return idtpVisualFormt;
	}
	public void setIdtpVisualFormt(String idtpVisualFormt) {
		this.idtpVisualFormt = idtpVisualFormt;
	}
	public String getIdtpStatus() {
		return idtpStatus;
	}
	public void setIdtpStatus(String idtpStatus) {
		this.idtpStatus = idtpStatus;
	}
	public String getIdtpDesc() {
		return idtpDesc;
	}
	public void setIdtpDesc(String idtpDesc) {
		this.idtpDesc = idtpDesc;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
