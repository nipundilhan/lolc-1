package com.fusionx.lending.origination.resource;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.enums.WorkflowStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovePendingApplicantBlacklistResource {

	private Long id;
	
	private Long customerId;
	
	private String firstName;
	
	private String middleName;
    
	private String lastName;
	
	private String cusReferenceCode;
	
	private String requestedUser;
	
	private Date requestedDate;
	
	private WorkflowStatus workflowStatus;
	
	private String cusIdentificationNo;
	
	private List<IdentificationDetail> identificationDetails;

	
	
	public ApprovePendingApplicantBlacklistResource(Long id, Long customerId, String firstName, String middleName,
			String lastName, String cusReferenceCode, String requestedUser, Date requestedDate,
			WorkflowStatus workflowStatus, String cusIdentificationNo) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.cusReferenceCode = cusReferenceCode;
		this.requestedUser = requestedUser;
		this.requestedDate = requestedDate;
		this.workflowStatus = workflowStatus;
		this.cusIdentificationNo = cusIdentificationNo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCusReferenceCode() {
		return cusReferenceCode;
	}

	public void setCusReferenceCode(String cusReferenceCode) {
		this.cusReferenceCode = cusReferenceCode;
	}

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public WorkflowStatus getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(WorkflowStatus workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public List<IdentificationDetail> getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(List<IdentificationDetail> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}

	public String getCusIdentificationNo() {
		return cusIdentificationNo;
	}

	public void setCusIdentificationNo(String cusIdentificationNo) {
		this.cusIdentificationNo = cusIdentificationNo;
	}

	

	
}
