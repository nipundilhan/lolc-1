package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadInfoRequestResponseResource implements Serializable {

    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String facilityType;
    private String totalDueAmount;
    private String totalSettlementAmount;
    private String newLoanAmount;
    private String availableBalance;
    private String status;
    private String createdUser;
    private String createdDate;
    private String modifiedUser;
    private String modifiedDate;
    private String branchId;
    private String branchName;
    private String onboardRequestId;
    private String leadStatus;
    private String marketingOfficerId;
    private List<CustomerRequestResponseResource> customers;
    private List<FacilityContractDetailsRequestResponseResource> facilityContractDetails;
    private List<FacilityOtherProductsRequestResponseResource> facilityOtherProducts;
    private List<Object> guarantors;
    private List<Object> additionalDocuments;
    private List<Object> disbursementDetails;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSyncTs() {
		return syncTs;
	}
	public void setSyncTs(String syncTs) {
		this.syncTs = syncTs;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	public String getTotalDueAmount() {
		return totalDueAmount;
	}
	public void setTotalDueAmount(String totalDueAmount) {
		this.totalDueAmount = totalDueAmount;
	}
	public String getTotalSettlementAmount() {
		return totalSettlementAmount;
	}
	public void setTotalSettlementAmount(String totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}
	public String getNewLoanAmount() {
		return newLoanAmount;
	}
	public void setNewLoanAmount(String newLoanAmount) {
		this.newLoanAmount = newLoanAmount;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getOnboardRequestId() {
		return onboardRequestId;
	}
	public void setOnboardRequestId(String onboardRequestId) {
		this.onboardRequestId = onboardRequestId;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public String getMarketingOfficerId() {
		return marketingOfficerId;
	}
	public void setMarketingOfficerId(String marketingOfficerId) {
		this.marketingOfficerId = marketingOfficerId;
	}
	public List<CustomerRequestResponseResource> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerRequestResponseResource> customers) {
		this.customers = customers;
	}
	public List<FacilityContractDetailsRequestResponseResource> getFacilityContractDetails() {
		return facilityContractDetails;
	}
	public void setFacilityContractDetails(List<FacilityContractDetailsRequestResponseResource> facilityContractDetails) {
		this.facilityContractDetails = facilityContractDetails;
	}
	public List<FacilityOtherProductsRequestResponseResource> getFacilityOtherProducts() {
		return facilityOtherProducts;
	}
	public void setFacilityOtherProducts(List<FacilityOtherProductsRequestResponseResource> facilityOtherProducts) {
		this.facilityOtherProducts = facilityOtherProducts;
	}
	public List<Object> getGuarantors() {
		return guarantors;
	}
	public void setGuarantors(List<Object> guarantors) {
		this.guarantors = guarantors;
	}
	public List<Object> getAdditionalDocuments() {
		return additionalDocuments;
	}
	public void setAdditionalDocuments(List<Object> additionalDocuments) {
		this.additionalDocuments = additionalDocuments;
	}
	public List<Object> getDisbursementDetails() {
		return disbursementDetails;
	}
	public void setDisbursementDetails(List<Object> disbursementDetails) {
		this.disbursementDetails = disbursementDetails;
	}
    
    

}
