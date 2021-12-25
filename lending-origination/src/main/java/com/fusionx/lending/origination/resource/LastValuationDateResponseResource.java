package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LastValuationDateResponseResource {
	
	private String message;
	
	private Long id;
	
	private String assetsEntityId;

	private String valuationDate;

	private Date lastValuationDate;
	
	private String reValuationDate;
	
	private String approveStatus;
	 
	private String status;
	
	Boolean valuationsExists = Boolean.FALSE;
	
	private ServiceStatus serviceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetsEntityId() {
		return assetsEntityId;
	}

	public void setAssetsEntityId(String assetsEntityId) {
		this.assetsEntityId = assetsEntityId;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}


	public Date getLastValuationDate() {
		return lastValuationDate;
	}

	public void setLastValuationDate(Date lastValuationDate) {
		this.lastValuationDate = lastValuationDate;
	}

	public String getReValuationDate() {
		return reValuationDate;
	}

	public void setReValuationDate(String reValuationDate) {
		this.reValuationDate = reValuationDate;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getValuationsExists() {
		return valuationsExists;
	}

	public void setValuationsExists(Boolean valuationsExists) {
		this.valuationsExists = valuationsExists;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
