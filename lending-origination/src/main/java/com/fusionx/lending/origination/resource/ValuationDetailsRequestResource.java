package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationDetailsRequestResource {
	
	private String tenantId;	
	private String id;
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
	private String creAppFinalValuation;	
	
	private String approveStatus;
	private String createdUser;
	private String createdDate;
	private String modifiedUser;
	private String modifiedDate;
	private String approvedUser;
	private String approvedDate;
	private String rejectedUser;
	private String rejectedDate;
	private String revaluationStatus;
	private String revaluationRenewedDate;
	private String revaluationRenewedUser;
	private String assetEntityId;
	private String inspectionOfficerId;
	private String finalValuation;
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCreAppFinalValuation() {
		return creAppFinalValuation;
	}
	public void setCreAppFinalValuation(String creAppFinalValuation) {
		this.creAppFinalValuation = creAppFinalValuation;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedUser() {
		return approvedUser;
	}
	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRejectedUser() {
		return rejectedUser;
	}
	public void setRejectedUser(String rejectedUser) {
		this.rejectedUser = rejectedUser;
	}
	public String getRejectedDate() {
		return rejectedDate;
	}
	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}
	public String getRevaluationStatus() {
		return revaluationStatus;
	}
	public void setRevaluationStatus(String revaluationStatus) {
		this.revaluationStatus = revaluationStatus;
	}
	public String getRevaluationRenewedDate() {
		return revaluationRenewedDate;
	}
	public void setRevaluationRenewedDate(String revaluationRenewedDate) {
		this.revaluationRenewedDate = revaluationRenewedDate;
	}
	public String getRevaluationRenewedUser() {
		return revaluationRenewedUser;
	}
	public void setRevaluationRenewedUser(String revaluationRenewedUser) {
		this.revaluationRenewedUser = revaluationRenewedUser;
	}
	public String getAssetEntityId() {
		return assetEntityId;
	}
	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}
	public String getInspectionOfficerId() {
		return inspectionOfficerId;
	}
	public void setInspectionOfficerId(String inspectionOfficerId) {
		this.inspectionOfficerId = inspectionOfficerId;
	}
	public String getFinalValuation() {
		return finalValuation;
	}
	public void setFinalValuation(String finalValuation) {
		this.finalValuation = finalValuation;
	}

}
