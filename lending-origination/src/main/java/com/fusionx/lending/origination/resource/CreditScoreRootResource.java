package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreditScoreRootResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String templateId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String businessPersonTypeId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String businessTypeId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String industryTypeId;
	
	@Valid
	private List<CreditScoreFieldValueResource> creditScoreFieldValueResourceList;
	

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<CreditScoreFieldValueResource> getCreditScoreFieldValueResourceList() {
		return creditScoreFieldValueResourceList;
	}

	public void setCreditScoreFieldValueResourceList(
			List<CreditScoreFieldValueResource> creditScoreFieldValueResourceList) {
		this.creditScoreFieldValueResourceList = creditScoreFieldValueResourceList;
	}

	public String getBusinessPersonTypeId() {
		return businessPersonTypeId;
	}

	public void setBusinessPersonTypeId(String businessPersonTypeId) {
		this.businessPersonTypeId = businessPersonTypeId;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}




	
	
}
