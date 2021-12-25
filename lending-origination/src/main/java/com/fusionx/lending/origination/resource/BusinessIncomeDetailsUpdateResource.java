package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 	Business Income Details Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessIncomeDetailsUpdateResource{
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<DocumentUpdateResource> businessIncomeDocumentList;
	
	@Valid
	private BusinessAdditionalDetailsUpdateResource businessAdditionalDetails;
	
	public List<DocumentUpdateResource> getBusinessIncomeDocumentList() {
		return businessIncomeDocumentList;
	}

	public void setBusinessIncomeDocumentList(List<DocumentUpdateResource> businessIncomeDocumentList) {
		this.businessIncomeDocumentList = businessIncomeDocumentList;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BusinessAdditionalDetailsUpdateResource getBusinessAdditionalDetails() {
		return businessAdditionalDetails;
	}

	public void setBusinessAdditionalDetails(BusinessAdditionalDetailsUpdateResource businessAdditionalDetails) {
		this.businessAdditionalDetails = businessAdditionalDetails;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
