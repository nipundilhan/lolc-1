package com.fusionx.lending.product.resources;

public class WorkflowRulesResource {
	
	private String referenceCode;

    private String approvalRequired;

    private String approvalAllowed;

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	public String getApprovalAllowed() {
		return approvalAllowed;
	}

	public void setApprovalAllowed(String approvalAllowed) {
		this.approvalAllowed = approvalAllowed;
	}
    
    
}
