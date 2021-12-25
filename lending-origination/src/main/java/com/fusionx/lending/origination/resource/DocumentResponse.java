package com.fusionx.lending.origination.resource;
/**
 * 	Document Response
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/
import com.fusionx.lending.origination.enums.ServiceStatus;

public class DocumentResponse {
	
	private Long id;
	private String documentName;
	private String documentStatus;
	private ServiceStatus serviceStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
