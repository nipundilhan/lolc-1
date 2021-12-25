package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class IdentificationType {

	private String idtpCode;
	private String idtpDesc;
	private ServiceStatus serviceStatus;
	private String idtpVisualFormtValidtion;
	
	// added by Senitha
	private Long id;
	private String idtpStatus;
	
	public String getIdtpCode() {
		return idtpCode;
	}
	public void setIdtpCode(String idtpCode) {
		this.idtpCode = idtpCode;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdtpStatus() {
		return idtpStatus;
	}
	public void setIdtpStatus(String idtpStatus) {
		this.idtpStatus = idtpStatus;
	}
	public String getIdtpVisualFormtValidtion() {
		return idtpVisualFormtValidtion;
	}
	public void setIdtpVisualFormtValidtion(String idtpVisualFormtValidtion) {
		this.idtpVisualFormtValidtion = idtpVisualFormtValidtion;
	}
}
