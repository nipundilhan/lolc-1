package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class BankBranchResponseResource {
	
	private Long id;
	private String bbrhCode;
	private String bbrhName;
	private String bbrhStatus;
	private String bbrhBankId;
	private ServiceStatus serviceStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBbrhCode() {
		return bbrhCode;
	}
	public void setBbrhCode(String bbrhCode) {
		this.bbrhCode = bbrhCode;
	}
	public String getBbrhName() {
		return bbrhName;
	}
	public void setBbrhName(String bbrhName) {
		this.bbrhName = bbrhName;
	}
	public String getBbrhStatus() {
		return bbrhStatus;
	}
	public void setBbrhStatus(String bbrhStatus) {
		this.bbrhStatus = bbrhStatus;
	}
	public String getBbrhBankId() {
		return bbrhBankId;
	}
	public void setBbrhBankId(String bbrhBankId) {
		this.bbrhBankId = bbrhBankId;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	
	

}
