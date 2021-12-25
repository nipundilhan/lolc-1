package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationDetailsResponseResource {
	
	private Long id;
	
	private String valuationDetailsId;
	
	private String assetsEntity;

	private String valuerId;

	private String valuerRefCode;

	private String valuerType;

	private String valuationDate;

	private String lastValuationDate;
	
	private String reValuationDate;

	private String marketValue;

	private String forceSaleValue;
	
	private String remark;
	
	private String status;
	
	private String approveStatus;
	
	private String modifiedUser;
	
	private String createdUser;
	
	private ServiceStatus serviceStatus;

	public String getValuationDetailsId() {
		return valuationDetailsId;
	}

	public void setValuationDetailsId(String valuationDetailsId) {
		this.valuationDetailsId = valuationDetailsId;
	}

	public String getAssetsEntity() {
		return assetsEntity;
	}

	public void setAssetsEntity(String assetsEntity) {
		this.assetsEntity = assetsEntity;
	}

	public String getValuerId() {
		return valuerId;
	}

	public void setValuerId(String valuerId) {
		this.valuerId = valuerId;
	}

	public String getValuerRefCode() {
		return valuerRefCode;
	}

	public void setValuerRefCode(String valuerRefCode) {
		this.valuerRefCode = valuerRefCode;
	}

	public String getValuerType() {
		return valuerType;
	}

	public void setValuerType(String valuerType) {
		this.valuerType = valuerType;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getLastValuationDate() {
		return lastValuationDate;
	}

	public void setLastValuationDate(String lastValuationDate) {
		this.lastValuationDate = lastValuationDate;
	}

	public String getReValuationDate() {
		return reValuationDate;
	}

	public void setReValuationDate(String reValuationDate) {
		this.reValuationDate = reValuationDate;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getForceSaleValue() {
		return forceSaleValue;
	}

	public void setForceSaleValue(String forceSaleValue) {
		this.forceSaleValue = forceSaleValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
