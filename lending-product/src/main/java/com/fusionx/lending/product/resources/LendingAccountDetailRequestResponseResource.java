package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.domain.AuditData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendingAccountDetailRequestResponseResource {

    private String accountNumber;
    private String loanAmount;
    private String collectionMethod;
    private String interestRate;
    private String termNumber;
    private String calculationMethod;
    private String repaymentCriteria;
    private String noOfPerPayment;
    private String amountPaidInAdvanced;
    private String residualValue;
    private String rewards;
    private String remarks;
    private String loanAmountWithTax;
    private String totalReceivable;
    private String downPaymentAmount;
    private String leaseFactor;
    private String chargeFactor;
    private String totalFactor;
    private String irr;
    private String dueDate;
    private String penalInterestRate;
    private String gracePeriod;
    private String expiryDate;
    private String currencyCode;
    private String currencyCodeNumeric;
    private String tenantId;
    private String status;
    private AuditData auditData;
    private List<ProductRequestResponseResource> product;
    private List<SubProductRequestResponseResource> subProduct;
    private List<BankBranchRequestResponseResource> branch;
    private String meId;
    private String currencyId;
    private String tcId;
    private List<LeadInfoRequestResponseResource> approvedLead;
    private Object recoveryAccount;
    private String documentTemplateId;
	public List<ProductRequestResponseResource> getProduct() {
		return product;
	}
	public void setProduct(List<ProductRequestResponseResource> product) {
		this.product = product;
	}
	public List<BankBranchRequestResponseResource> getBranch() {
		return branch;
	}
	public void setBranch(List<BankBranchRequestResponseResource> branch) {
		this.branch = branch;
	}
	public List<LeadInfoRequestResponseResource> getApprovedLead() {
		return approvedLead;
	}
	public void setApprovedLead(List<LeadInfoRequestResponseResource> approvedLead) {
		this.approvedLead = approvedLead;
	}
	public List<SubProductRequestResponseResource> getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(List<SubProductRequestResponseResource> subProduct) {
		this.subProduct = subProduct;
	}
	public Object getRecoveryAccount() {
		return recoveryAccount;
	}
	public void setRecoveryAccount(Object recoveryAccount) {
		this.recoveryAccount = recoveryAccount;
	}
	
}
