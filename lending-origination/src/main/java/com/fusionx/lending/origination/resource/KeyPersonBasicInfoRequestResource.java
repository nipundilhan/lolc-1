package com.fusionx.lending.origination.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KeyPersonBasicInfoRequestResource extends PersonRequestResource{

	@JsonProperty("ckpStatus")
	@NotBlank(message = "{ckpStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{ckpStatus.pattern}")
	private String ckpStatus;
	
	@JsonProperty("ckpPositionCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{ckpPositionCommonListId.pattern}")
	private String ckpPositionCommonListId;
	private String ckpPositionDesc;
	
	@JsonProperty("ckpKeyPersonTypeId")
	@Pattern(regexp = "^$|[0-9]+", message = "{ckpKeyPersonTypeId.pattern}")
	private String ckpKeyPersonTypeId;
	private String ckpKeyPersonTypeDesc;
	
	@JsonProperty("perIndividualInf")
	private String perIndividualInf;
	
	@JsonProperty("perIndividualInfo")
	@NotNull(message = "{perIndividualInfo.not-null}")
	@Valid
	private PersonIndividualInfoRequestResource perIndividualInfo;

	public KeyPersonBasicInfoRequestResource() {
		super();
	}

	public String getCkpStatus() {
		return ckpStatus;
	}

	public void setCkpStatus(String ckpStatus) {
		this.ckpStatus = ckpStatus;
	}

	public String getCkpPositionCommonListId() {
		return ckpPositionCommonListId;
	}

	public void setCkpPositionCommonListId(String ckpPositionCommonListId) {
		this.ckpPositionCommonListId = ckpPositionCommonListId;
	}

	public String getCkpPositionDesc() {
		return ckpPositionDesc;
	}

	public void setCkpPositionDesc(String ckpPositionDesc) {
		this.ckpPositionDesc = ckpPositionDesc;
	}
	
	public String getCkpKeyPersonTypeId() {
		return ckpKeyPersonTypeId;
	}

	public void setCkpKeyPersonTypeId(String ckpKeyPersonTypeId) {
		this.ckpKeyPersonTypeId = ckpKeyPersonTypeId;
	}

	public String getCkpKeyPersonTypeDesc() {
		return ckpKeyPersonTypeDesc;
	}

	public void setCkpKeyPersonTypeDesc(String ckpKeyPersonTypeDesc) {
		this.ckpKeyPersonTypeDesc = ckpKeyPersonTypeDesc;
	}

	public String getPerIndividualInf() {
		return perIndividualInf;
	}

	public void setPerIndividualInf(String perIndividualInf) {
		this.perIndividualInf = perIndividualInf;
	}

	public PersonIndividualInfoRequestResource getPerIndividualInfo() {
		return perIndividualInfo;
	}

	public void setPerIndividualInfo(PersonIndividualInfoRequestResource perIndividualInfo) {
		this.perIndividualInfo = perIndividualInfo;
	}
}
