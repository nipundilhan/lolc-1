package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class DocumentTypeResponse {

	private String documentTypeName;
	private String status;
	private ServiceStatus serviceStatus;
	public String getDocumentTypeName() {
		return documentTypeName;
	}
	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
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
