package com.fusionx.lending.origination.resource;

import java.util.List;

public class ApprovalDataResource {

	private String leadId;
	private String groupCreditExposure;
	private String individualCreditExposure;
	private String DSCR;
	private String creditScore;
	private String overallCreditRisk;
	private String approvalCategoryId;
	private String approvalCategory;
	private String note;
	private String commentGropId;
	private String commentGropName;
	private String comment;
	
	
	private List<ApproveUserResource> approveLevelList;


	public String getGroupCreditExposure() {
		return groupCreditExposure;
	}


	public void setGroupCreditExposure(String groupCreditExposure) {
		this.groupCreditExposure = groupCreditExposure;
	}


	public String getIndividualCreditExposure() {
		return individualCreditExposure;
	}


	public void setIndividualCreditExposure(String individualCreditExposure) {
		this.individualCreditExposure = individualCreditExposure;
	}


	public String getDSCR() {
		return DSCR;
	}


	public void setDSCR(String dSCR) {
		DSCR = dSCR;
	}


	public String getCreditScore() {
		return creditScore;
	}


	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}


	public String getOverallCreditRisk() {
		return overallCreditRisk;
	}


	public void setOverallCreditRisk(String overallCreditRisk) {
		this.overallCreditRisk = overallCreditRisk;
	}


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


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getCommentGropId() {
		return commentGropId;
	}


	public void setCommentGropId(String commentGropId) {
		this.commentGropId = commentGropId;
	}


	public String getCommentGropName() {
		return commentGropName;
	}


	public void setCommentGropName(String commentGropName) {
		this.commentGropName = commentGropName;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<ApproveUserResource> getApproveLevelList() {
		return approveLevelList;
	}


	public void setApproveLevelList(List<ApproveUserResource> approveLevelList) {
		this.approveLevelList = approveLevelList;
	}


	public String getLeadId() {
		return leadId;
	}


	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
					
	
}
