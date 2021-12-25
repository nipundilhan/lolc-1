package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class CommonListRemote {

	private String id;
	private String cmlsReferenceCode;
	private String cmlsCode;
	private String cmlsDesc;
	private String cmlsStatus;
	private String pcmlsStatus;
	
	private ServiceStatus serviceStatus;
	public String getCmlsReferenceCode() {
		return cmlsReferenceCode;
	}
	public void setCmlsReferenceCode(String cmlsReferenceCode) {
		this.cmlsReferenceCode = cmlsReferenceCode;
	}
	public String getCmlsCode() {
		return cmlsCode;
	}
	public void setCmlsCode(String cmlsCode) {
		this.cmlsCode = cmlsCode;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCmlsStatus() {
		return cmlsStatus;
	}
	public void setCmlsStatus(String cmlsStatus) {
		this.cmlsStatus = cmlsStatus;
	}
	
}
