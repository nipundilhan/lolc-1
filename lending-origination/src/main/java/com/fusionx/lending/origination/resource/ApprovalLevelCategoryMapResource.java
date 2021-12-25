package com.fusionx.lending.origination.resource;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.ApprovalLevel;

public class ApprovalLevelCategoryMapResource {


	private String approvalCategoryId;
	private String approvalCategory;
	private String approvalLevelId;
	private String approvalLevel;
	
	private String status;

	public String getApprovalCategoryId() {
		return approvalCategoryId;
	}

	public void setApprovalCategoryId(String approvalCategoryId) {
		this.approvalCategoryId = approvalCategoryId;
	}

	public String getApprovalCategory() {
		return approvalCategory;
	}

	public void setApprovalCategory(String approvalCategory) {
		this.approvalCategory = approvalCategory;
	}

	public String getApprovalLevelId() {
		return approvalLevelId;
	}

	public void setApprovalLevelId(String approvalLevelId) {
		this.approvalLevelId = approvalLevelId;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
