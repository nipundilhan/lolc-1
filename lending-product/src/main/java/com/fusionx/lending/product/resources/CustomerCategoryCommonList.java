package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

public class CustomerCategoryCommonList {

	private String pcmlsReferenceCode;
	private String pcmlsCode;
	private String cmlsDesc;
	private ServiceStatus serviceStatus;
	private String pcmlsStatus;

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

}
