package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class PerCommonList {

	private Long id;
	
	private String pcmlsReferenceCode;
	
	private String pcmlsCode;
	
	private String cmlsDesc;
	
	private String pcmlsStatus;
	
	private ServiceStatus serviceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPcmlsReferenceCode() {
		return pcmlsReferenceCode;
	}

	public void setPcmlsReferenceCode(String pcmlsReferenceCode) {
		this.pcmlsReferenceCode = pcmlsReferenceCode;
	}

	public String getPcmlsCode() {
		return pcmlsCode;
	}

	public void setPcmlsCode(String pcmlsCode) {
		this.pcmlsCode = pcmlsCode;
	}

	public String getCmlsDesc() {
		return cmlsDesc;
	}

	public void setCmlsDesc(String cmlsDesc) {
		this.cmlsDesc = cmlsDesc;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getPcmlsStatus() {
		return pcmlsStatus;
	}

	public void setPcmlsStatus(String pcmlsStatus) {
		this.pcmlsStatus = pcmlsStatus;
	}
	
}
