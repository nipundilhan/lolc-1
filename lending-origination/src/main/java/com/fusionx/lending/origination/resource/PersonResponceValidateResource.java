package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class PersonResponceValidateResource{

	private String value;
	
	private String pcmlsStatus;
	
	private ServiceStatus serviceStatus;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPcmlsStatus() {
		return pcmlsStatus;
	}

	public void setPcmlsStatus(String pcmlsStatus) {
		this.pcmlsStatus = pcmlsStatus;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	
	
}
