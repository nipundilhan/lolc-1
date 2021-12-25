package com.fusionx.lending.origination.resource;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LeadInfoStagingWithAdditionalDetailsResource {
	
	
	
	private Long leadId;
	private Long leadStagingId;
	private Long custId;//.ID,
	private String stagingStatus;//	,a.STAGING_STATUS
	private String customerType;//,b.CUSTOMER_TYPE
	//private String firstName;//,b.FIRST_NAME
	//private String middleName;//,b.MIDDLE_NAME
	//private String lastName;//,b.LAST_NAME
	private String fullName;//,b.FULL_NAME
	//private String initials;//,b.INITIALS
	private String customerReferenceCode;//,b.CUS_REFERENCE_CODE
	private String identificationType;
	private String identificationNo;
	private String brNumber;//,b.BR_NUMBER
	private String companyName;//,b.COMPANY_NAME
	//private String title;//,b.TITLE
	//private String gender;//,b.GENDER
	//private String customerMainType;//,b.CUSTOMER_MAIN_TYPE
	private String createdUser;//,a.CREATED_USER
	
	private Date createdDate;//a.CREATED_DATE,
	private String modifiedUser;//a.MODIFIED_USER,
	private Date modifiedDate;//a.MODIFIED_DATE,
	private Long version;//a.VERSION,
	private String comments;//a.COMMENTS,
	private String tenantId;//a.TENANT_ID,
	private Date syncTs;//a.SYNC_TS,
	private String status;//a.STATUS,
	

	public LeadInfoStagingWithAdditionalDetailsResource( Long leadId,
														 String stagingStatus,
														 String customerType,
														 //String firstName,
														 //String middleName,
														 //String lastName,
														 String fullName,
														 //String initials,
														 String customerReferenceCode,
														 String identificationType,
														 String identificationNo,
														 String brNumber,
														 String companyName,
														 //String title,
														 //String gender,
														 //String customerMainType,
														 String createdUser,
														 Long leadStagingId,
														 Date createdDate,
														 String modifiedUser,
														 Date modifiedDate,
														 Long version,
														 String comments,
														 String tenantId,
														 Date syncTs,
														 String status,
														 Long custId
														) {
		super();
		this.leadId = leadId;
		this.stagingStatus = stagingStatus;
		this.customerType = customerType;
		//this.firstName = firstName;
		//this.middleName = middleName;
		//this.lastName = lastName;
		this.fullName = fullName;
		//this.initials = initials;
		this.customerReferenceCode = customerReferenceCode;
		this.identificationType = identificationType;
		this.identificationNo = identificationNo;
		this.brNumber = brNumber;
		this.companyName = companyName;
		//this.title = title;
		//this.gender = gender;
		//this.customerMainType = customerMainType;
		this.createdUser = createdUser;
		this.leadStagingId= leadStagingId;
		this.createdDate= createdDate;
		this.modifiedUser= modifiedUser;
		this.modifiedDate= modifiedDate;
		this.version= version;
		this.comments= comments;
		this.tenantId= tenantId;
		this.syncTs= syncTs;
		this.status= status;
		this.custId= custId;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getStagingStatus() {
		return stagingStatus;
	}

	public void setStagingStatus(String stagingStatus) {
		this.stagingStatus = stagingStatus;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCustomerReferenceCode() {
		return customerReferenceCode;
	}

	public void setCustomerReferenceCode(String customerReferenceCode) {
		this.customerReferenceCode = customerReferenceCode;
	}

	public String getBrNumber() {
		return brNumber;
	}

	public void setBrNumber(String brNumber) {
		this.brNumber = brNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Long getLeadStagingId() {
		return leadStagingId;
	}

	public void setLeadStagingId(Long leadStagingId) {
		this.leadStagingId = leadStagingId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Date getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	


	
	
	
}
