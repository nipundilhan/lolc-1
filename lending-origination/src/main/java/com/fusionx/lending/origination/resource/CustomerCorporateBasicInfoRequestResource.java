package com.fusionx.lending.origination.resource;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerCorporateBasicInfoRequestResource extends PersonCorporateInfoRequestResource{

	@JsonProperty("cusGoverningAuthorityCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{cusGoverningAuthorityCommonListId.pattern}")
	private String cusGoverningAuthorityCommonListId;
	private String cusGoverningAuthorityDesc;
	
	@JsonProperty("cusRegisteredDate")
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{cusRegisteredDate.pattern}")
	private String cusRegisteredDate;
	
	@JsonProperty("cusOperationStartDate")
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{cusOperationStartDate.pattern}")
	private String cusOperationStartDate;

	public String getCusGoverningAuthorityCommonListId() {
		return cusGoverningAuthorityCommonListId;
	}

	public void setCusGoverningAuthorityCommonListId(String cusGoverningAuthorityCommonListId) {
		this.cusGoverningAuthorityCommonListId = cusGoverningAuthorityCommonListId;
	}

	public String getCusGoverningAuthorityDesc() {
		return cusGoverningAuthorityDesc;
	}

	public void setCusGoverningAuthorityDesc(String cusGoverningAuthorityDesc) {
		this.cusGoverningAuthorityDesc = cusGoverningAuthorityDesc;
	}

	public String getCusRegisteredDate() {
		return cusRegisteredDate;
	}

	public void setCusRegisteredDate(String cusRegisteredDate) {
		this.cusRegisteredDate = cusRegisteredDate;
	}

	public String getCusOperationStartDate() {
		return cusOperationStartDate;
	}

	public void setCusOperationStartDate(String cusOperationStartDate) {
		this.cusOperationStartDate = cusOperationStartDate;
	}
}
