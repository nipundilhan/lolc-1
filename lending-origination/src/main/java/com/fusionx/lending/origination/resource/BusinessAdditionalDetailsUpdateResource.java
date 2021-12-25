package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Business Additional Details Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessAdditionalDetailsUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String businessTypeId;
    
	@NotBlank(message = "{common.not-null}")
    private String businessTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessSubTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String businessSubTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String ownershipId;
	
	@NotBlank(message = "{common.not-null}")
	private String ownershipName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 300, message = "{business-name.size}")
	private String businessName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessSizeId;	
	
	@NotBlank(message = "{common.not-null}")
	private String businessSizeName;
	
	@NotBlank(message = "{common.not-null}")
	private String noOfYearsInBusiness;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String busiOpertaionStartDate;
	
	@Size(max = 50, message = "{br-no.size}")
	private String businessRegiNo;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String businessRegiDate;
	
	@Size(max = 1500, message = "{description.size}")
	private String description;
	
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String profitMargin;
	
	@Size(max = 1500, message = "{description.size}")
	private String comment;
	
	@Pattern(regexp = "PRIMARY|SECONDARY", message = "{common-source.pattern}")
	private String sourceType;
	
	private Long noOfBranches;
	
	@Size(max = 3000, message = "{detail-desc.size}")
	private String skillsOfKeyPerson;
	
	@Size(max = 3000, message = "{detail-desc.size}")
	private String previousBusiHistory;
	
	@Size(max = 3000, message = "{detail-desc.size}")
	private String busiContinuityPlan;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
		
	@Valid
	private List<DocumentUpdateResource> businessDocumentList;
	
	@Valid
	private List<BusinessRiskDetailResource> businessRiskdetailList;
	
	@Valid
	private List<BusinessAssetDetailResource> businessAssetdetailList;
	
	@Valid
	private List<BusinessEmploymentDetailResource> businessEmploymentdetailList;
	
	@Valid
	private List<BusinessClearanceDetailResource> businessClearancedetailList;


	public String getBusinessTypeId() {
		return businessTypeId;
	}


	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}


	public String getBusinessTypeName() {
		return businessTypeName;
	}


	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}


	public String getBusinessSubTypeId() {
		return businessSubTypeId;
	}


	public void setBusinessSubTypeId(String businessSubTypeId) {
		this.businessSubTypeId = businessSubTypeId;
	}


	public String getBusinessSubTypeName() {
		return businessSubTypeName;
	}


	public void setBusinessSubTypeName(String businessSubTypeName) {
		this.businessSubTypeName = businessSubTypeName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getProfitMargin() {
		return profitMargin;
	}


	public void setProfitMargin(String profitMargin) {
		this.profitMargin = profitMargin;
	}


	public String getBusiOpertaionStartDate() {
		return busiOpertaionStartDate;
	}


	public void setBusiOpertaionStartDate(String busiOpertaionStartDate) {
		this.busiOpertaionStartDate = busiOpertaionStartDate;
	}


	public String getBusinessRegiDate() {
		return businessRegiDate;
	}


	public void setBusinessRegiDate(String businessRegiDate) {
		this.businessRegiDate = businessRegiDate;
	}


	public String getOwnershipId() {
		return ownershipId;
	}


	public void setOwnershipId(String ownershipId) {
		this.ownershipId = ownershipId;
	}

	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public String getBusinessSizeId() {
		return businessSizeId;
	}


	public void setBusinessSizeId(String businessSizeId) {
		this.businessSizeId = businessSizeId;
	}


	public String getNoOfYearsInBusiness() {
		return noOfYearsInBusiness;
	}


	public void setNoOfYearsInBusiness(String noOfYearsInBusiness) {
		this.noOfYearsInBusiness = noOfYearsInBusiness;
	}


	public String getBusinessRegiNo() {
		return businessRegiNo;
	}


	public void setBusinessRegiNo(String businessRegiNo) {
		this.businessRegiNo = businessRegiNo;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Long getNoOfBranches() {
		return noOfBranches;
	}


	public void setNoOfBranches(Long noOfBranches) {
		this.noOfBranches = noOfBranches;
	}


	public String getSkillsOfKeyPerson() {
		return skillsOfKeyPerson;
	}


	public void setSkillsOfKeyPerson(String skillsOfKeyPerson) {
		this.skillsOfKeyPerson = skillsOfKeyPerson;
	}


	public String getPreviousBusiHistory() {
		return previousBusiHistory;
	}


	public void setPreviousBusiHistory(String previousBusiHistory) {
		this.previousBusiHistory = previousBusiHistory;
	}


	public String getBusiContinuityPlan() {
		return busiContinuityPlan;
	}


	public void setBusiContinuityPlan(String busiContinuityPlan) {
		this.busiContinuityPlan = busiContinuityPlan;
	}

	public @Valid List<DocumentUpdateResource> getBusinessDocumentList() {
		return businessDocumentList;
	}


	public void setBusinessDocumentList(@Valid List<DocumentUpdateResource> businessDocumentList) {
		this.businessDocumentList = businessDocumentList;
	}


	public List<BusinessRiskDetailResource> getBusinessRiskdetailList() {
		return businessRiskdetailList;
	}


	public void setBusinessRiskdetailList(List<BusinessRiskDetailResource> businessRiskdetailList) {
		this.businessRiskdetailList = businessRiskdetailList;
	}


	public List<BusinessAssetDetailResource> getBusinessAssetdetailList() {
		return businessAssetdetailList;
	}


	public void setBusinessAssetdetailList(List<BusinessAssetDetailResource> businessAssetdetailList) {
		this.businessAssetdetailList = businessAssetdetailList;
	}


	public List<BusinessEmploymentDetailResource> getBusinessEmploymentdetailList() {
		return businessEmploymentdetailList;
	}


	public void setBusinessEmploymentdetailList(List<BusinessEmploymentDetailResource> businessEmploymentdetailList) {
		this.businessEmploymentdetailList = businessEmploymentdetailList;
	}


	public List<BusinessClearanceDetailResource> getBusinessClearancedetailList() {
		return businessClearancedetailList;
	}


	public void setBusinessClearancedetailList(List<BusinessClearanceDetailResource> businessClearancedetailList) {
		this.businessClearancedetailList = businessClearancedetailList;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}


	public String getBusinessSizeName() {
		return businessSizeName;
	}


	public void setBusinessSizeName(String businessSizeName) {
		this.businessSizeName = businessSizeName;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
