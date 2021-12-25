package com.fusionx.lending.origination.resource;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.enums.WorkflowStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovePendingGuarantorBlacklistResource {
	
	private Long id;
	
	private Long guarantorId;
	
	private String firstName;
	
	private String middleName;
    
	private String lastName;
	
	private String referenceNo;
	
	private String requestedUser;
	
	private Date requestedDate;
	
	private WorkflowStatus workflowStatus;
	
	private String guarIdentificationNo;
	
	private List<IdentificationDetail> identificationDetails;

	public ApprovePendingGuarantorBlacklistResource(Long id, Long guarantorId, String firstName, String middleName,
			String lastName, String referenceNo, String requestedUser, Date requestedDate,
			WorkflowStatus workflowStatus, String guarIdentificationNo) {
		super();
		this.id = id;
		this.guarantorId = guarantorId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.referenceNo = referenceNo;
		this.requestedUser = requestedUser;
		this.requestedDate = requestedDate;
		this.workflowStatus = workflowStatus;
		this.guarIdentificationNo = guarIdentificationNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(Long guarantorId) {
		this.guarantorId = guarantorId;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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

	public String getGuarIdentificationNo() {
		return guarIdentificationNo;
	}

	public void setGuarIdentificationNo(String guarIdentificationNo) {
		this.guarIdentificationNo = guarIdentificationNo;
	}

	public List<IdentificationDetail> getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(List<IdentificationDetail> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}
	
	

}
