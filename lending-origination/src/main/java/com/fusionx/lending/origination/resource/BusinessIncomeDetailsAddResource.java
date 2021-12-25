package com.fusionx.lending.origination.resource;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Business Income Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessIncomeDetailsAddResource{
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeSourceDetailsId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Valid
	private List<DocumentAddResource> businessIncomeDocumentList;

	@Valid
	private BusinessAdditionalDetailsAddResource businessAdditionalDetails;

	public String getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}

	public void setIncomeSourceDetailsId(String incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}
	
	public List<DocumentAddResource> getBusinessIncomeDocumentList() {
		return businessIncomeDocumentList;
	}

	public void setBusinessIncomeDocumentList(List<DocumentAddResource> businessIncomeDocumentList) {
		this.businessIncomeDocumentList = businessIncomeDocumentList;
	}

	public BusinessAdditionalDetailsAddResource getBusinessAdditionalDetails() {
		return businessAdditionalDetails;
	}

	public void setBusinessAdditionalDetails(BusinessAdditionalDetailsAddResource businessAdditionalDetails) {
		this.businessAdditionalDetails = businessAdditionalDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
